package com.estate.api.customer;

import com.estate.dto.EmailChangeDTO;
import com.estate.dto.PasswordChangeDTO;
import com.estate.dto.PhoneNumberChangeDTO;
import com.estate.dto.UsernameChangeDTO;
import com.estate.security.CustomUserDetails;
import com.estate.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer/profile")
@RequiredArgsConstructor
public class CustomerProfileAPI {
    private final CustomerService customerService;

    @PutMapping("/username")
    public ResponseEntity<?> usernameUpdate(
            @RequestBody UsernameChangeDTO dto,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long customerId = user.getUserId();
        customerService.usernameUpdate(dto, customerId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/email")
    public ResponseEntity<?> emailUpdate(
            @RequestBody EmailChangeDTO dto,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long customerId = user.getUserId();
        customerService.emailUpdate(dto, customerId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/phoneNumber")
    public ResponseEntity<?> phoneNumberUpdate(
            @RequestBody PhoneNumberChangeDTO dto,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long customerId = user.getUserId();
        customerService.phoneNumberUpdate(dto, customerId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/password")
    public ResponseEntity<?> passwordUpdate(
            @RequestBody PasswordChangeDTO dto,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long customerId = user.getUserId();
        customerService.passwordUpdate(dto, customerId);
        return ResponseEntity.ok().build();
    }
}
