package com.estate.controller.staff;

import com.estate.security.CustomUserDetails;
import com.estate.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/staff/profile")
@RequiredArgsConstructor
public class StaffProfileController {
    private final StaffService staffService;

    @GetMapping("")
    public String profile(
            Model model,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long userId = user.getUserId();

        model.addAttribute("staff", staffService.findById(userId));

        model.addAttribute("staffName", staffService.getStaffName(user.getUserId()));
        model.addAttribute("staffAvatar", staffService.getStaffAvatar(user.getUserId()));

        return "staff/profile";
    }
}
