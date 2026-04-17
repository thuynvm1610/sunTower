package com.estate.api.admin;

import com.estate.dto.EmailChangeDTO;
import com.estate.dto.PasswordChangeDTO;
import com.estate.dto.PhoneNumberChangeDTO;
import com.estate.dto.UsernameChangeDTO;
import com.estate.repository.OAuthIdentityRepository;
import com.estate.repository.entity.OAuthIdentityEntity;
import com.estate.repository.entity.StaffEntity;
import com.estate.security.CustomUserDetails;
import com.estate.service.ImageStorageService;
import com.estate.service.ProfileOtpService;
import com.estate.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/profile")
@RequiredArgsConstructor
public class AdminProfileAPI {
    private final StaffService staffService;
    private final ProfileOtpService profileOtpService;
    private final OAuthIdentityRepository oauthIdentityRepository;
    private final ImageStorageService imageStorageService;

    private static final List<String> ALLOWED_TYPES = List.of("image/jpeg", "image/png", "image/webp");
    private static final List<String> ALLOWED_EXTS = List.of(".jpg", ".jpeg", ".png", ".webp");
    private static final long MAX_SIZE_BYTES = 5 * 1024 * 1024;

    @PutMapping("/username")
    public ResponseEntity<?> usernameUpdate(@RequestBody UsernameChangeDTO dto,
                                            @AuthenticationPrincipal CustomUserDetails user) {
        staffService.usernameUpdate(dto, user.getUserId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/email")
    public ResponseEntity<?> emailUpdate(@RequestBody EmailChangeDTO dto,
                                         @AuthenticationPrincipal CustomUserDetails user) {
        staffService.emailUpdate(dto, user.getUserId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/phoneNumber")
    public ResponseEntity<?> phoneNumberUpdate(@RequestBody PhoneNumberChangeDTO dto,
                                               @AuthenticationPrincipal CustomUserDetails user) {
        staffService.phoneNumberUpdate(dto, user.getUserId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/password")
    public ResponseEntity<?> passwordUpdate(@RequestBody PasswordChangeDTO dto,
                                            @AuthenticationPrincipal CustomUserDetails user) {
        staffService.passwordUpdate(dto, user.getUserId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/otp/{purpose}")
    public ResponseEntity<?> sendOtp(@PathVariable String purpose,
                                     @AuthenticationPrincipal CustomUserDetails user) {
        profileOtpService.sendOtp(resolveOtpEmail(user.getUserId()), purpose);
        return ResponseEntity.ok(Map.of("message", "Mã OTP đã được gửi đến email liên kết."));
    }

    @PostMapping("/upload-avatar")
    public ResponseEntity<?> uploadAvatar(@RequestParam("file") MultipartFile file) {
        ResponseEntity<?> validationError = validateImageFile(file);
        if (validationError != null) return validationError;

        try {
            String result = imageStorageService.store(file, "staff");
            return ResponseEntity.ok(Map.of("filename", result, "message", "Upload thành công"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("message", "Lỗi lưu file: " + e.getMessage()));
        }
    }

    @PutMapping("/avatar")
    public ResponseEntity<?> avatarUpdate(@RequestBody Map<String, String> body,
                                          @AuthenticationPrincipal CustomUserDetails user) {
        String filename = body.get("filename");
        if (filename == null || filename.isBlank())
            return ResponseEntity.badRequest().body(Map.of("message", "filename không được để trống."));
        staffService.avatarUpdate(filename, user.getUserId());
        return ResponseEntity.ok(Map.of("message", "Cập nhật ảnh đại diện thành công."));
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private String resolveOtpEmail(Long staffId) {
        return oauthIdentityRepository
                .findByProviderAndUserTypeAndUserId("google", "STAFF", staffId)
                .map(OAuthIdentityEntity::getEmail)
                .orElseGet(() -> staffService.findById(staffId).getEmail());
    }

    private ResponseEntity<?> validateImageFile(MultipartFile file) {
        if (file.isEmpty())
            return ResponseEntity.badRequest().body(Map.of("message", "Vui lòng chọn file ảnh."));
        if (file.getSize() > MAX_SIZE_BYTES)
            return ResponseEntity.badRequest().body(Map.of("message", "File quá lớn. Tối đa 5 MB."));
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_TYPES.contains(contentType))
            return ResponseEntity.badRequest().body(Map.of("message", "Chỉ chấp nhận JPG, PNG, WEBP."));
        String originalName = file.getOriginalFilename() != null
                ? file.getOriginalFilename().toLowerCase() : "";
        if (ALLOWED_EXTS.stream().noneMatch(originalName::endsWith))
            return ResponseEntity.badRequest().body(Map.of("message", "Định dạng file không hợp lệ."));
        return null;
    }
}