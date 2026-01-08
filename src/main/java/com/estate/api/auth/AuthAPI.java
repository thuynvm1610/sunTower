package com.estate.api.auth;

import com.estate.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthAPI {
    @Autowired
    AuthService authService;

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {

        authService.forgotPassword(email);

        return ResponseEntity.ok(
                Map.of("message", "Nếu email hợp lệ, liên kết đã được gửi.")
        );
    }
}
