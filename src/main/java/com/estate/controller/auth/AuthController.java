package com.estate.controller.auth;

import com.estate.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    @Autowired
    AuthService authService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login-success")
    public String loginSuccess(Authentication authentication) {

        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"))
                ? "redirect:/customer/home"
                : "redirect:/admin/dashboard";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }

    @GetMapping("/auth/reset-password")
    public String resetPasswordPage(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/auth/reset-password")
    public String resetPassword(
            @RequestParam String token,
            @RequestParam String newPassword,
            RedirectAttributes redirectAttributes) {

        authService.resetPassword(token, newPassword);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Mật khẩu đã được cập nhật thành công"
        );

        return "redirect:/login";
    }
}
