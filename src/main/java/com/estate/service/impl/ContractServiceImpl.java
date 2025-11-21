package com.estate.service.impl;

import com.estate.dto.StaffPerformanceDTO;
import com.estate.repository.ContractRepository;
import com.estate.repository.entity.ContractEntity;
import com.estate.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ContractServiceImpl implements ContractService {
    @Autowired
    private ContractRepository contractRepository;

    @Override
    public Long countAll() {
        return contractRepository.count();
    }

    @Override
    public List<StaffPerformanceDTO> getTopStaffs() {
        List<Object[]> rawData = contractRepository.countContractsByStaff((Pageable) PageRequest.of(0, 5));

        long totalContracts = rawData.stream()
                .mapToLong(r -> (Long) r[2])
                .sum();

        return rawData.stream().map(r -> {
            Long staffId = (Long) r[0];
            String fullName = (String) r[1];
            Long contractCount = (Long) r[2];

            double percent = totalContracts == 0
                    ? 0
                    : (contractCount * 100.0) / totalContracts;

            return new StaffPerformanceDTO(
                    staffId,
                    fullName,
                    contractCount,
                    Math.round(percent * 100) / 100.0
            );
        }).collect(Collectors.toList());
    }

    @Override
    public List<BigDecimal> getMonthlyRevenue(int year) {

        LocalDateTime startOfYear = LocalDateTime.of(year, 1, 1, 0, 0);
        LocalDateTime endOfYear   = LocalDateTime.of(year, 12, 31, 23, 59);

        List<ContractEntity> contracts = contractRepository
                .findByStartDateLessThanEqualAndEndDateGreaterThanEqual(endOfYear, startOfYear);

        List<BigDecimal> revenue = new ArrayList<>(Collections.nCopies(12, BigDecimal.ZERO));

        int currentYear  = LocalDate.now().getYear();
        int currentMonth = LocalDate.now().getMonthValue();

        for (ContractEntity c : contracts) {

            LocalDateTime start = c.getStartDate();
            LocalDateTime end   = c.getEndDate();

            BigDecimal monthlyPrice = c.getRentPrice();

            // Xác định tháng bắt đầu trong năm
            int startMonth = Math.max(1,
                    start.getYear() < year ? 1 : start.getMonthValue());

            // Xác định tháng kết thúc trong năm
            int endMonth = Math.min(12,
                    end.getYear() > year ? 12 : end.getMonthValue());

            // Loại bỏ tháng chưa diễn ra nếu là năm hiện tại
            if (year == currentYear) {
                endMonth = Math.min(endMonth, currentMonth - 1);
            }

            // Bỏ qua hợp đồng nếu không còn tháng hợp lệ (Ví dụ: Tháng hiện tại là 1 thì endMonth = 0)
            if (startMonth > endMonth) continue;

            // Cộng tiền vào danh sách
            for (int m = startMonth; m <= endMonth; m++) {
                revenue.set(
                        m - 1,
                        revenue.get(m - 1).add(monthlyPrice)
                );
            }
        }

        return revenue;
    }

    @Override
    public List<BigDecimal> getYearlyRevenue(int yearBeforeLast, int lastYear, int currentYear) {
        List<BigDecimal> finalRevenue = new ArrayList<>(Collections.nCopies(3, BigDecimal.ZERO));

        List<BigDecimal> yearBeforeLastRevenueByMonth = getMonthlyRevenue(yearBeforeLast);
        List<BigDecimal> lastYearRevenueByMonth = getMonthlyRevenue(lastYear);
        List<BigDecimal> currentYearRevenueByMonth = getMonthlyRevenue(currentYear);

        BigDecimal yearBeforeLastRevenue = BigDecimal.ZERO;
        BigDecimal lastYearRevenue = BigDecimal.ZERO;
        BigDecimal currentYearRevenue = BigDecimal.ZERO;

        for (int j = 0; j < 12; j++) {
            yearBeforeLastRevenue = yearBeforeLastRevenue.add(yearBeforeLastRevenueByMonth.get(j));
            lastYearRevenue = lastYearRevenue.add(lastYearRevenueByMonth.get(j));
            currentYearRevenue = currentYearRevenue.add(currentYearRevenueByMonth.get(j));
        }

        finalRevenue.set(0, yearBeforeLastRevenue);
        finalRevenue.set(1, lastYearRevenue);
        finalRevenue.set(2, currentYearRevenue);

        return finalRevenue;
    }

    @Override
    public Map<String, Long> getContractCountByBuilding() {
        List<Object[]> result = contractRepository.countContractsByBuilding((Pageable)PageRequest.of(0, 5));
        Map<String, Long> map = new LinkedHashMap<>();
        for (Object[] row : result) {
            map.put((String) row[0], (Long) row[1]);
        }
        return map;
    }

    @Override
    public Map<Long, Long> getContractCountByYear() {
        List<Long[]> result = contractRepository.countContractsByYear();
        Map<Long, Long> map = new LinkedHashMap<>();
        for (Long[] row : result) {
            map.put(row[0], row[1]);
        }
        return map;
    }
}
