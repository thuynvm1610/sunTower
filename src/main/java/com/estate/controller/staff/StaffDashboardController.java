package com.estate.controller.staff;

import com.estate.security.CustomUserDetails;
import com.estate.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/staff")
public class StaffDashboardController {
    @Autowired
    StaffService staffService;

    @GetMapping("/dashboard")
    public String staffDasboard(
            Model model,
            @AuthenticationPrincipal CustomUserDetails user
        ) {
        Long staffID = user.getCustomerId();

        model.addAttribute("buildingCnt", staffService.getBuildingCnt(staffID));

        return "/staff/dashboard";
    }
}
