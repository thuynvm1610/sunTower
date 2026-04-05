package com.estate.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ReportDTO {

    // ── KPI ───────────────────────────────────────────────────────────────────
    private BigDecimal totalRentRevenueCurrentYear;   // Tổng doanh thu thuê năm hiện tại
    private BigDecimal totalSaleRevenueAllTime;       // Tổng giá trị mua bán đã ký (all-time)
    private Long totalForRentBuildings;               // Số building FOR_RENT
    private Long occupiedForRentBuildings;            // Số building FOR_RENT đang có HĐ ACTIVE
    private Long expiringContractsCount;              // HĐ sắp hết hạn ≤30 ngày

    // ── Doanh thu theo tháng (năm hiện tại) ──────────────────────────────────
    private List<BigDecimal> monthlyRentRevenue;      // 12 phần tử — chỉ doanh thu thuê
    private List<BigDecimal> monthlySaleRevenue;      // 12 phần tử — doanh thu mua bán (ghi nhận theo tháng ký)

    // ── Doanh thu theo tháng (năm trước — dùng tính tăng trưởng) ────────────
    private List<BigDecimal> monthlyRentLastYear;     // 12 phần tử — thuê năm trước
    private List<BigDecimal> monthlySaleLastYear;     // 12 phần tử — mua bán năm trước

    // ── Doanh thu gộp 3 năm ──────────────────────────────────────────────────
    private List<BigDecimal> yearlyRentRevenue;       // [yearBeforeLast, lastYear, currentYear]
    private List<BigDecimal> yearlySaleRevenue;       // [yearBeforeLast, lastYear, currentYear]
    private int yearBeforeLast;
    private int lastYear;
    private int currentYear;

    // ── Phân bổ BĐS ──────────────────────────────────────────────────────────
    private Map<String, Long> buildingByPropertyType; // OFFICE→N, APARTMENT→N, ...
    private Long forRentCount;
    private Long forSaleCount;
    private Long forRentActiveCount;   // FOR_RENT đang có HĐ ACTIVE
    private Long forSaleActiveCount;   // FOR_SALE đã bán

    // ── Top 5 building doanh thu thuê cao nhất (năm hiện tại) ────────────────
    private List<BuildingRevenueDTO> topBuildingsByRentRevenue;

    // ── HĐ sắp hết hạn ───────────────────────────────────────────────────────
    private List<ExpiringContractDTO> expiringContracts;

    // ── Top 5 nhân viên theo tổng giá trị ────────────────────────────────────
    private List<StaffValueDTO> topStaffsByValue;

    // ── Top 5 khách hàng theo tổng giá trị ───────────────────────────────────
    private List<CustomerValueDTO> topCustomersByValue;
}