package com.estate.api.admin;

import com.estate.dto.EmailChangeDTO;
import com.estate.dto.PasswordChangeDTO;
import com.estate.dto.PhoneNumberChangeDTO;
import com.estate.dto.UsernameChangeDTO;
import com.estate.security.CustomUserDetails;
import com.estate.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/profile")
public class AdminProfileAPI {
    @Autowired
    StaffService staffService;

    @PutMapping("/username")
    public ResponseEntity<?> usernameUpdate(
            @RequestBody UsernameChangeDTO dto,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long staffId = user.getCustomerId();
        staffService.usernameUpdate(dto, staffId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/email")
    public ResponseEntity<?> emailUpdate(
            @RequestBody EmailChangeDTO dto,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long staffId = user.getCustomerId();
        staffService.emailUpdate(dto, staffId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/phoneNumber")
    public ResponseEntity<?> phoneNumberUpdate(
            @RequestBody PhoneNumberChangeDTO dto,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long staffId = user.getCustomerId();
        staffService.phoneNumberUpdate(dto, staffId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/password")
    public ResponseEntity<?> passwordUpdate(
            @RequestBody PasswordChangeDTO dto,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long staffId = user.getCustomerId();
        staffService.passwordUpdate(dto, staffId);
        return ResponseEntity.ok().build();
    }
}
