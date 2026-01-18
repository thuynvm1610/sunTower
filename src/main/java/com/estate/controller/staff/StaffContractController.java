package com.estate.controller.staff;

import com.estate.enums.Direction;
import com.estate.enums.Level;
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
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        model.addAttribute("customers", customerService.getCustomersName());
        model.addAttribute("buildings", buildingService.getBuildingsName());

        String username = staffService.getStaffName(user.getCustomerId());
        model.addAttribute("username", username);

        return "staff/contract-list";
    }
}
