package com.estate.controller.admin;

import com.estate.dto.PotentialCustomersDTO;
import com.estate.dto.StaffPerformanceDTO;
import com.estate.service.BuildingService;
import com.estate.service.ContractService;
import com.estate.service.CustomerService;
import com.estate.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class DashboardController {
    @Autowired
    private BuildingService buildingService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private ContractService contractService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        model.addAttribute("pageTitle", "Trang quản trị hệ thống SunTower");
        model.addAttribute("totalBuildings", buildingService.countAll());
        model.addAttribute("totalCustomers", customerService.countAll());
        model.addAttribute("totalStaffs", staffService.countAllStaffs());
        model.addAttribute("totalContracts", contractService.countAll());

        model.addAttribute("recentBuildings", buildingService.findRecent());

        Map<String, Long> buildingByDistrict = buildingService.getBuildingCountByDistrict();
        model.addAttribute("districtNames", buildingByDistrict.keySet());
        model.addAttribute("districtCounts", buildingByDistrict.values());

        int currentYear = LocalDate.now().getYear();
        List<BigDecimal> monthlyRevenue = contractService.getMonthlyRevenue(currentYear);
        model.addAttribute("monthlyRevenue", monthlyRevenue);
        model.addAttribute("currentYear", currentYear);

        List<BigDecimal> yearlyRevenue = contractService.getYearlyRevenue(currentYear-2, currentYear-1, currentYear);
        model.addAttribute("yearlyRevenue", yearlyRevenue);
        model.addAttribute("yearBeforeLast", currentYear-2);
        model.addAttribute("lastYear", currentYear-1);
        model.addAttribute("currentYear", currentYear);


        List<StaffPerformanceDTO> topStaffs = contractService.getTopStaffs();
        model.addAttribute("topStaffs", topStaffs);

        Map<String, Long> contractByBuilding = contractService.getContractCountByBuilding();
        model.addAttribute("buildingNames", contractByBuilding.keySet());
        model.addAttribute("buildingContractCounts", contractByBuilding.values());

        Map<Long, Long> contractByYear = contractService.getContractCountByYear();
        model.addAttribute("contractYearLabels", contractByYear.keySet());
        model.addAttribute("contractYearCounts", contractByYear.values());

        List<PotentialCustomersDTO> potentialCustomers = customerService.getTopCustomers();
        model.addAttribute("potentialCustomers", potentialCustomers);

        model.addAttribute("page", "dashboard");
        return "admin/dashboard";
    }

}
