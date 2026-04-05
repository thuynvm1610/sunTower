package com.estate.service.impl;

import com.estate.dto.*;
import com.estate.repository.entity.ContractEntity;
import com.estate.repository.entity.SaleContractEntity;
import com.estate.enums.TransactionType;
import com.estate.repository.*;
import com.estate.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired private ContractRepository contractRepository;
    @Autowired private SaleContractRepository saleContractRepository;
    @Autowired private BuildingRepository buildingRepository;

    @Override
    public ReportDTO getReport(int year) {
        ReportDTO dto = new ReportDTO();

        int currentMonth = LocalDate.now().getMonthValue();
        int currentYear  = LocalDate.now().getYear();

        dto.setCurrentYear(year);
        dto.setLastYear(year - 1);
        dto.setYearBeforeLast(year - 2);

        // ── 1. Monthly rent revenue (12 tháng năm hiện tại) ──────────────────
        List<BigDecimal> monthlyRent = calcMonthlyRentRevenue(year, currentYear, currentMonth);
        dto.setMonthlyRentRevenue(monthlyRent);

        // ── 2. Monthly sale revenue (theo tháng ký sale_contract) ────────────
        List<BigDecimal> monthlySale = calcMonthlySaleRevenue(year);
        dto.setMonthlySaleRevenue(monthlySale);

        // ── 2b. Dữ liệu năm trước để tính tăng trưởng ────────────────────────
        List<BigDecimal> monthlyRentLastYear = calcMonthlyRentRevenue(year - 1, currentYear, currentMonth);
        List<BigDecimal> monthlySaleLastYear = calcMonthlySaleRevenue(year - 1);
        dto.setMonthlyRentLastYear(monthlyRentLastYear);
        dto.setMonthlySaleLastYear(monthlySaleLastYear);

        // ── 3. KPI: tổng doanh thu thuê năm hiện tại ─────────────────────────
        BigDecimal totalRent = monthlyRent.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        dto.setTotalRentRevenueCurrentYear(totalRent);

        // ── 4. KPI: tổng giá trị mua bán all-time ────────────────────────────
        BigDecimal totalSale = saleContractRepository.findAll().stream()
                .map(SaleContractEntity::getSalePrice)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        dto.setTotalSaleRevenueAllTime(totalSale);

        // ── 5. KPI: tỷ lệ lấp đầy FOR_RENT ──────────────────────────────────
        long totalForRent = buildingRepository.countByTransactionType(TransactionType.FOR_RENT);
        long occupied     = contractRepository.countDistinctBuildingWithActiveContract();
        dto.setTotalForRentBuildings(totalForRent);
        dto.setOccupiedForRentBuildings(occupied);

        // ── 6. KPI: HĐ sắp hết hạn ≤30 ngày ─────────────────────────────────
        LocalDateTime now     = LocalDateTime.now();
        LocalDateTime in30    = now.plusDays(30);
        List<ContractEntity> expiring = contractRepository
                .findByStatusAndEndDateBetween("ACTIVE", now, in30);
        dto.setExpiringContractsCount((long) expiring.size());

        // ── 7. Yearly revenue (3 năm) ─────────────────────────────────────────
        dto.setYearlyRentRevenue(calcYearlyRent(year - 2, year - 1, year, currentYear, currentMonth));
        dto.setYearlySaleRevenue(calcYearlySale(year - 2, year - 1, year));

        // ── 8. Phân bổ BĐS theo loại hình ────────────────────────────────────
        Map<String, Long> byType = buildingRepository.countGroupByPropertyType()
                .stream()
                .collect(Collectors.toMap(
                        arr -> arr[0].toString(),
                        arr -> (Long) arr[1],
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
        dto.setBuildingByPropertyType(byType);

        long forRentCount  = buildingRepository.countByTransactionType(TransactionType.FOR_RENT);
        long forSaleCount  = buildingRepository.countByTransactionType(TransactionType.FOR_SALE);
        long forSaleActive = saleContractRepository.count();
        dto.setForRentCount(forRentCount);
        dto.setForSaleCount(forSaleCount);
        dto.setForRentActiveCount(occupied);
        dto.setForSaleActiveCount(forSaleActive);

        // ── 9. Top 5 building doanh thu thuê (năm hiện tại) ──────────────────
        dto.setTopBuildingsByRentRevenue(calcTopBuildingRevenue(year, currentYear, currentMonth));

        // ── 10. HĐ sắp hết hạn — table ───────────────────────────────────────
        List<ExpiringContractDTO> expiringList = expiring.stream()
                .sorted(Comparator.comparing(ContractEntity::getEndDate))
                .map(c -> new ExpiringContractDTO(
                        c.getId(),
                        c.getBuilding() != null ? c.getBuilding().getName() : "—",
                        c.getCustomer() != null ? c.getCustomer().getFullName() : "—",
                        c.getEndDate(),
                        ChronoUnit.DAYS.between(now, c.getEndDate())
                ))
                .collect(Collectors.toList());
        dto.setExpiringContracts(expiringList);

        // ── 11. Top 5 nhân viên theo tổng giá trị ────────────────────────────
        dto.setTopStaffsByValue(calcTopStaff());

        // ── 12. Top 5 khách hàng theo tổng giá trị ───────────────────────────
        dto.setTopCustomersByValue(calcTopCustomer());

        return dto;
    }

    // ── HELPERS ───────────────────────────────────────────────────────────────

    /**
     * Doanh thu thuê theo tháng trong năm target.
     * Logic: với mỗi contract active trong năm, cộng rentPrice×rentArea vào từng tháng nó active.
     * Nếu là năm hiện tại, chỉ tính đến tháng trước (tháng chưa kết thúc không tính).
     */
    private List<BigDecimal> calcMonthlyRentRevenue(int targetYear, int currentYear, int currentMonth) {
        LocalDateTime startOfYear = LocalDateTime.of(targetYear, 1, 1, 0, 0);
        LocalDateTime endOfYear   = LocalDateTime.of(targetYear, 12, 31, 23, 59);

        List<ContractEntity> contracts = contractRepository
                .findByStartDateLessThanEqualAndEndDateGreaterThanEqual(endOfYear, startOfYear);

        List<BigDecimal> revenue = new ArrayList<>(Collections.nCopies(12, BigDecimal.ZERO));

        for (ContractEntity c : contracts) {
            BigDecimal monthlyValue = c.getRentPrice().multiply(BigDecimal.valueOf(c.getRentArea()));

            int startMonth = c.getStartDate().getYear() < targetYear ? 1 : c.getStartDate().getMonthValue();
            int endMonth   = c.getEndDate().getYear()   > targetYear ? 12 : c.getEndDate().getMonthValue();

            startMonth = Math.max(1, startMonth);
            endMonth   = Math.min(12, endMonth);

            if (targetYear == currentYear) {
                endMonth = Math.min(endMonth, currentMonth - 1);
            }
            if (startMonth > endMonth) continue;

            for (int m = startMonth; m <= endMonth; m++) {
                revenue.set(m - 1, revenue.get(m - 1).add(monthlyValue));
            }
        }
        return revenue;
    }

    /**
     * Doanh thu mua bán ghi nhận vào tháng created_date của sale_contract.
     */
    private List<BigDecimal> calcMonthlySaleRevenue(int targetYear) {
        List<BigDecimal> revenue = new ArrayList<>(Collections.nCopies(12, BigDecimal.ZERO));

        saleContractRepository.findAll().forEach(sc -> {
            if (sc.getCreatedDate() == null || sc.getSalePrice() == null) return;
            if (sc.getCreatedDate().getYear() != targetYear) return;
            int month = sc.getCreatedDate().getMonthValue();
            revenue.set(month - 1, revenue.get(month - 1).add(sc.getSalePrice()));
        });
        return revenue;
    }

    private List<BigDecimal> calcYearlyRent(int y1, int y2, int y3, int currentYear, int currentMonth) {
        List<BigDecimal> result = new ArrayList<>();
        for (int y : new int[]{y1, y2, y3}) {
            result.add(calcMonthlyRentRevenue(y, currentYear, currentMonth)
                    .stream().reduce(BigDecimal.ZERO, BigDecimal::add));
        }
        return result;
    }

    private List<BigDecimal> calcYearlySale(int y1, int y2, int y3) {
        Map<Integer, BigDecimal> byYear = new HashMap<>();
        saleContractRepository.findAll().forEach(sc -> {
            if (sc.getCreatedDate() == null || sc.getSalePrice() == null) return;
            int y = sc.getCreatedDate().getYear();
            byYear.merge(y, sc.getSalePrice(), BigDecimal::add);
        });
        List<BigDecimal> result = new ArrayList<>();
        for (int y : new int[]{y1, y2, y3}) {
            result.add(byYear.getOrDefault(y, BigDecimal.ZERO));
        }
        return result;
    }

    /**
     * Top 5 building theo tổng doanh thu thuê năm targetYear.
     * Tính: rentPrice × rentArea × số tháng active trong năm đó.
     */
    private List<BuildingRevenueDTO> calcTopBuildingRevenue(int targetYear, int currentYear, int currentMonth) {
        LocalDateTime startOfYear = LocalDateTime.of(targetYear, 1, 1, 0, 0);
        LocalDateTime endOfYear   = LocalDateTime.of(targetYear, 12, 31, 23, 59);

        List<ContractEntity> contracts = contractRepository
                .findByStartDateLessThanEqualAndEndDateGreaterThanEqual(endOfYear, startOfYear);

        Map<String, BigDecimal> revenueMap = new LinkedHashMap<>();

        for (ContractEntity c : contracts) {
            if (c.getBuilding() == null) continue;
            String name = c.getBuilding().getName();
            BigDecimal monthly = c.getRentPrice().multiply(BigDecimal.valueOf(c.getRentArea()));

            int startMonth = c.getStartDate().getYear() < targetYear ? 1 : c.getStartDate().getMonthValue();
            int endMonth   = c.getEndDate().getYear()   > targetYear ? 12 : c.getEndDate().getMonthValue();
            startMonth = Math.max(1, startMonth);
            endMonth   = Math.min(12, endMonth);
            if (targetYear == currentYear) endMonth = Math.min(endMonth, currentMonth - 1);
            if (startMonth > endMonth) continue;

            BigDecimal total = monthly.multiply(BigDecimal.valueOf(endMonth - startMonth + 1));
            revenueMap.merge(name, total, BigDecimal::add);
        }

        return revenueMap.entrySet().stream()
                .sorted(Map.Entry.<String, BigDecimal>comparingByValue().reversed())
                .limit(5)
                .map(e -> new BuildingRevenueDTO(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * Top 5 nhân viên theo tổng giá trị:
     * - Thuê: sum(rentPrice × rentArea × số tháng active năm hiện tại) của các HĐ họ phụ trách
     * - Mua bán: sum(sale_price) của các sale_contract họ phụ trách
     */
    private List<StaffValueDTO> calcTopStaff() {
        int currentYear  = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();

        // Doanh thu thuê theo staff_id
        LocalDateTime startOfYear = LocalDateTime.of(currentYear, 1, 1, 0, 0);
        LocalDateTime endOfYear   = LocalDateTime.of(currentYear, 12, 31, 23, 59);
        List<ContractEntity> contracts = contractRepository
                .findByStartDateLessThanEqualAndEndDateGreaterThanEqual(endOfYear, startOfYear);

        Map<Long, BigDecimal> rentByStaff = new HashMap<>();
        Map<Long, String>     staffNames  = new HashMap<>();

        for (ContractEntity c : contracts) {
            if (c.getStaff() == null) continue;
            Long staffId = c.getStaff().getId();
            staffNames.put(staffId, c.getStaff().getFullName());

            BigDecimal monthly = c.getRentPrice().multiply(BigDecimal.valueOf(c.getRentArea()));
            int startMonth = c.getStartDate().getYear() < currentYear ? 1 : c.getStartDate().getMonthValue();
            int endMonth   = c.getEndDate().getYear()   > currentYear ? 12 : c.getEndDate().getMonthValue();
            startMonth = Math.max(1, startMonth);
            endMonth   = Math.min(12, Math.min(endMonth, currentMonth - 1));
            if (startMonth > endMonth) continue;

            BigDecimal total = monthly.multiply(BigDecimal.valueOf(endMonth - startMonth + 1));
            rentByStaff.merge(staffId, total, BigDecimal::add);
        }

        // Doanh thu mua bán theo staff_id
        Map<Long, BigDecimal> saleByStaff = new HashMap<>();
        saleContractRepository.findAll().forEach(sc -> {
            if (sc.getStaff() == null || sc.getSalePrice() == null) return;
            Long staffId = sc.getStaff().getId();
            staffNames.put(staffId, sc.getStaff().getFullName());
            saleByStaff.merge(staffId, sc.getSalePrice(), BigDecimal::add);
        });

        // Gộp
        Set<Long> allStaffIds = new HashSet<>();
        allStaffIds.addAll(rentByStaff.keySet());
        allStaffIds.addAll(saleByStaff.keySet());

        return allStaffIds.stream()
                .map(id -> new StaffValueDTO(
                        id,
                        staffNames.getOrDefault(id, "NV-" + id),
                        rentByStaff.getOrDefault(id, BigDecimal.ZERO),
                        saleByStaff.getOrDefault(id, BigDecimal.ZERO)
                ))
                .sorted(Comparator.comparing(StaffValueDTO::getTotal).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }

    /**
     * Top 5 khách hàng theo tổng giá trị (thuê all-time + mua bán).
     */
    private List<CustomerValueDTO> calcTopCustomer() {
        Map<Long, BigDecimal> rentByCust = new HashMap<>();
        Map<Long, String>     custNames  = new HashMap<>();

        // Tất cả hợp đồng thuê (all-time, tính theo tổng giá trị hợp đồng)
        contractRepository.findAll().forEach(c -> {
            if (c.getCustomer() == null || c.getRentPrice() == null || c.getRentArea() == null) return;
            Long custId = c.getCustomer().getId();
            custNames.put(custId, c.getCustomer().getFullName());

            long months = ChronoUnit.MONTHS.between(
                    c.getStartDate().toLocalDate().withDayOfMonth(1),
                    c.getEndDate().toLocalDate().withDayOfMonth(1)
            );
            months = Math.max(1, months);
            BigDecimal total = c.getRentPrice()
                    .multiply(BigDecimal.valueOf(c.getRentArea()))
                    .multiply(BigDecimal.valueOf(months));
            rentByCust.merge(custId, total, BigDecimal::add);
        });

        Map<Long, BigDecimal> saleByCust = new HashMap<>();
        saleContractRepository.findAll().forEach(sc -> {
            if (sc.getCustomer() == null || sc.getSalePrice() == null) return;
            Long custId = sc.getCustomer().getId();
            custNames.put(custId, sc.getCustomer().getFullName());
            saleByCust.merge(custId, sc.getSalePrice(), BigDecimal::add);
        });

        Set<Long> allIds = new HashSet<>();
        allIds.addAll(rentByCust.keySet());
        allIds.addAll(saleByCust.keySet());

        return allIds.stream()
                .map(id -> new CustomerValueDTO(
                        id,
                        custNames.getOrDefault(id, "KH-" + id),
                        rentByCust.getOrDefault(id, BigDecimal.ZERO),
                        saleByCust.getOrDefault(id, BigDecimal.ZERO)
                ))
                .sorted(Comparator.comparing(CustomerValueDTO::getTotal).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }
}