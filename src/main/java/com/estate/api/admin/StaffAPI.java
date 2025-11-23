package com.estate.api.admin;

import com.estate.dto.CustomerListDTO;
import com.estate.dto.StaffListDTO;
import com.estate.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
