package com.estate.controller.admin;

import com.estate.repository.entity.StaffEntity;
import com.estate.security.CustomUserDetails;
import com.estate.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/profile")
@RequiredArgsConstructor
public class AdminProfileController {
    private final StaffService staffService;

    @GetMapping("")
    public String profile(
            Model model,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long userId = user.getUserId();

        StaffEntity staff = staffService.findById(userId);

        model.addAttribute("staff", staff);
        model.addAttribute("staffName", staffService.getStaffName(userId));
        model.addAttribute("staffAvatar", staffService.getStaffAvatar(userId));

        return "admin/profile";
    }
}
