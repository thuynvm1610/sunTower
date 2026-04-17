package com.estate.api.admin;

import com.estate.dto.BuildingFilterDTO;
import com.estate.dto.BuildingFormDTO;
import com.estate.dto.BuildingListDTO;
import com.estate.exception.InputValidationException;
import com.estate.service.BuildingService;
import com.estate.service.ImageStorageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/building")
@RequiredArgsConstructor
public class AdminBuildingAPI {
    private final BuildingService buildingService;
    private final ImageStorageService imageStorageService;

    // Định dạng và dung lượng cho phép
    private static final List<String> ALLOWED_TYPES = List.of("image/jpeg", "image/png", "image/webp");
    private static final List<String> ALLOWED_EXTS = List.of(".jpg", ".jpeg", ".png", ".webp");
    private static final long MAX_SIZE_BYTES = 5 * 1024 * 1024; // 5 MB

    @GetMapping("/list/page")
    public Page<BuildingListDTO> getBuildingsPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return buildingService.getBuildings(page - 1, size);
    }

    @GetMapping("/search/page")
    public Page<BuildingListDTO> getBuildingsSearchPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            BuildingFilterDTO filter
    ) {
        return buildingService.search(filter, page - 1, size);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBuilding(
            @Valid @RequestBody BuildingFormDTO dto,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            throw new InputValidationException(getFirstError(result));
        }
        buildingService.save(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editBuilding(
            @Valid @RequestBody BuildingFormDTO dto,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            throw new InputValidationException(getFirstError(result));
        }
        buildingService.save(dto);
        return ResponseEntity.ok("Sửa bất động sản thành công");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBuilding(@PathVariable Long id) {
        buildingService.delete(id);
        return ResponseEntity.ok().build();
    }

    // Upload ảnh bất động sản
    @PostMapping("/upload-image")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        // validate (giữ nguyên logic cũ)
        if (file.isEmpty())
            return ResponseEntity.badRequest().body(Map.of("message", "Vui lòng chọn file ảnh."));
        if (file.getSize() > MAX_SIZE_BYTES)
            return ResponseEntity.badRequest().body(Map.of("message", "File quá lớn. Tối đa 5 MB."));
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_TYPES.contains(contentType))
            return ResponseEntity.badRequest().body(Map.of("message", "Định dạng không hợp lệ."));
        String originalName = file.getOriginalFilename() != null ? file.getOriginalFilename().toLowerCase() : "";
        if (ALLOWED_EXTS.stream().noneMatch(originalName::endsWith))
            return ResponseEntity.badRequest().body(Map.of("message", "Định dạng file không hợp lệ."));

        try {
            String result = imageStorageService.store(file, "building");
            return ResponseEntity.ok(Map.of("filename", result, "message", "Upload thành công"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("message", "Lỗi lưu file: " + e.getMessage()));
        }
    }

    // Helper
    private String getFirstError(BindingResult result) {
        if (!result.getFieldErrors().isEmpty()) {
            return result.getFieldErrors().getFirst().getDefaultMessage();
        }
        return result.getAllErrors().getFirst().getDefaultMessage();
    }
}