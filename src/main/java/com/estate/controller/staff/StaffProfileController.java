package com.estate.controller.staff;

import com.estate.repository.entity.StaffEntity;
import com.estate.security.CustomUserDetails;
import com.estate.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/staff/profile")
public class StaffProfileController {
    @Autowired
    StaffService staffService;

    @GetMapping("")
    public String listBuildings(
            Model model,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long staffId = user.getCustomerId();
        StaffEntity staff = staffService.findById(staffId);
        model.addAttribute("staff", staff);

        model.addAttribute("staffName", staffService.getStaffName(user.getCustomerId()));

        model.addAttribute("staffAvatar", staffService.getStaffAvatar(user.getCustomerId()));

        return "staff/profile";
    }
}
