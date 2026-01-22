package com.estate.controller.staff;

import com.estate.security.CustomUserDetails;
import com.estate.service.BuildingService;
import com.estate.service.CustomerService;
import com.estate.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Map;

@Controller
@RequestMapping("/staff")
public class StaffContractController {
    @Autowired
    StaffService staffService;

    @Autowired
    CustomerService customerService;

    @Autowired
    BuildingService buildingService;

    @GetMapping("/contracts")
    public String contract(
            Model model,
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam Map<String, String> params
    ) {
        model.addAttribute("customers", customerService.getCustomersName());
        model.addAttribute("buildings", buildingService.getBuildingsName());

        model.addAttribute("staffName", staffService.getStaffName(user.getCustomerId()));

        model.addAttribute("staffAvatar", staffService.getStaffAvatar(user.getCustomerId()));

        if (params.get("status") != null) {
            model.addAttribute("status", params.get("status"));
            LocalDate endDate = LocalDate
                    .now()
                    .plusMonths(1)
                    .withDayOfMonth(1);
            model.addAttribute("endDate", endDate);
        }

        return "staff/contract-list";
    }
}
