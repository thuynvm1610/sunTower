package com.estate.controller.customer;

import com.estate.security.CustomUserDetails;
import com.estate.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer/profile")
@RequiredArgsConstructor
public class CustomerProfileController {
    private final CustomerService customerService;

    @GetMapping("")
    public String profile(
            Model model,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long userId = user.getUserId();

        model.addAttribute("customer", customerService.findById(userId));

        return "customer/profile";
    }
}
