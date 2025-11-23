package com.estate.api.admin;

import com.estate.dto.CustomerFormDTO;
import com.estate.dto.StaffFormDTO;
import com.estate.dto.StaffListDTO;
import com.estate.exception.InputValidationException;
import com.estate.service.StaffService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/staff")
public class StaffAPI {
    @Autowired
    StaffService staffService;

    @GetMapping("/list/page")
    public Page<StaffListDTO> getCustomersPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String role
    ) {
        return staffService.getStaffs(page - 1, size, role);
    }

    @GetMapping("/search/page")
    public Page<StaffListDTO> getStaffsSearchPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam Map<String, String> filter
    ) {
        Page<StaffListDTO> result = staffService.search(filter, page - 1, size);
        return result;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addStaff(@Valid @RequestBody StaffFormDTO dto,
                                         BindingResult result) {
        if (result.hasErrors()) {
            String message;

            if (!result.getFieldErrors().isEmpty()) {
                message = result.getFieldErrors().get(0).getDefaultMessage();
            } else {
                message = result.getAllErrors().get(0).getDefaultMessage();
            }

            throw new InputValidationException(message);
        }

        staffService.save(dto);
        return ResponseEntity.ok("Thêm nhân viên thành công");
    }
}
