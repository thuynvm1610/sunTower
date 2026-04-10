package com.estate.api.staff;

import com.estate.dto.EmailChangeDTO;
import com.estate.dto.PasswordChangeDTO;
import com.estate.dto.PhoneNumberChangeDTO;
import com.estate.dto.UsernameChangeDTO;
import com.estate.repository.OAuthIdentityRepository;
import com.estate.repository.entity.OAuthIdentityEntity;
import com.estate.repository.entity.StaffEntity;
import com.estate.security.CustomUserDetails;
import com.estate.service.StaffService;
import com.estate.service.ProfileOtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/staff/profile")
@RequiredArgsConstructor
public class StaffProfileAPI {
    private final StaffService staffService;
    private final ProfileOtpService profileOtpService;
    private final OAuthIdentityRepository oauthIdentityRepository;

    @Value("${staff.image.upload-dir}")
    private String uploadDir;

    private static final List<String> ALLOWED_TYPES = List.of("image/jpeg", "image/png", "image/webp");
    private static final List<String> ALLOWED_EXTS  = List.of(".jpg", ".jpeg", ".png", ".webp");
    private static final long MAX_SIZE_BYTES = 5 * 1024 * 1024; // 5 MB

    @PutMapping("/username")
    public ResponseEntity<?> usernameUpdate(
            @RequestBody UsernameChangeDTO dto,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long staffId = user.getUserId();
        staffService.usernameUpdate(dto, staffId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/email")
    public ResponseEntity<?> emailUpdate(
            @RequestBody EmailChangeDTO dto,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long staffId = user.getUserId();
        staffService.emailUpdate(dto, staffId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/phoneNumber")
    public ResponseEntity<?> phoneNumberUpdate(
            @RequestBody PhoneNumberChangeDTO dto,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long staffId = user.getUserId();
        staffService.phoneNumberUpdate(dto, staffId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/password")
    public ResponseEntity<?> passwordUpdate(
            @RequestBody PasswordChangeDTO dto,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long staffId = user.getUserId();
        staffService.passwordUpdate(dto, staffId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/otp/{purpose}")
    public ResponseEntity<?> sendOtp(@PathVariable String purpose,
                                     @AuthenticationPrincipal CustomUserDetails user) {
        Long staffId = user.getUserId();
        String email = resolveOtpEmail(staffId);
        profileOtpService.sendOtp(email, purpose);
        return ResponseEntity.ok(Map.of("message", "Mã OTP đã được gửi đến email liên kết."));
    }

    // Upload ảnh đại diện
    @PostMapping("/upload-avatar")
    public ResponseEntity<?> uploadAvatar(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Vui lòng chọn file ảnh."));
        }

        if (file.getSize() > MAX_SIZE_BYTES) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "File quá lớn. Dung lượng tối đa cho phép là 5 MB."));
        }

        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_TYPES.contains(contentType)) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Định dạng không hợp lệ. Chỉ chấp nhận JPG, PNG, WEBP."));
        }

        String originalName = file.getOriginalFilename() != null
                ? file.getOriginalFilename().toLowerCase() : "";
        boolean validExt = ALLOWED_EXTS.stream().anyMatch(originalName::endsWith);
        if (!validExt) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Định dạng file không hợp lệ. Chỉ chấp nhận .jpg, .png, .webp."));
        }

        String ext         = originalName.substring(originalName.lastIndexOf('.'));
        String newFilename = UUID.randomUUID().toString().replace("-", "") + ext;

        try {
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath();
            Files.createDirectories(uploadPath);
            Files.copy(file.getInputStream(),
                    uploadPath.resolve(newFilename),
                    StandardCopyOption.REPLACE_EXISTING);

            return ResponseEntity.ok(Map.of(
                    "filename", newFilename,
                    "message",  "Upload ảnh thành công"
            ));
        } catch (IOException e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("message", "Lỗi lưu file: " + e.getMessage()));
        }
    }


    // Cập nhật field image trong DB
    @PutMapping("/avatar")
    public ResponseEntity<?> avatarUpdate(
            @RequestBody Map<String, String> body,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        String filename = body.get("filename");
        if (filename == null || filename.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "filename không được để trống."));
        }

        Long staffId = user.getUserId();
        staffService.avatarUpdate(filename, staffId);
        return ResponseEntity.ok(Map.of("message", "Cập nhật ảnh đại diện thành công."));
    }

    // HELPER
    private String resolveOtpEmail(Long staffId) {
        return oauthIdentityRepository
                .findByProviderAndUserTypeAndUserId("google", "STAFF", staffId)
                .map(OAuthIdentityEntity::getEmail)
                .orElseGet(() -> {
                    StaffEntity staff = staffService.findById(staffId);
                    return staff.getEmail();
                });
    }
}
