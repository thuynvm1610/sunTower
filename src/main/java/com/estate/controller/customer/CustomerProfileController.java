package com.estate.controller.customer;

import com.estate.repository.OAuthIdentityRepository;
import com.estate.repository.entity.OAuthIdentityEntity;
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
    private final OAuthIdentityRepository oauthIdentityRepository;

    @GetMapping("")
    public String profile(
            Model model,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long userId = user.getUserId();

        model.addAttribute("customer", customerService.findById(userId));
        model.addAttribute("linkedGoogleEmail", resolveLinkedGoogleEmail(user.getUserType(), userId));

        return "customer/profile";
    }

    private String resolveLinkedGoogleEmail(String userType, Long userId) {
        return oauthIdentityRepository
                .findByProviderAndUserTypeAndUserId("google", userType, userId)
                .map(OAuthIdentityEntity::getEmail)
                .orElse(null);
    }
}
