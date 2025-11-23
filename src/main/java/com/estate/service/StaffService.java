package com.estate.service;

import com.estate.dto.CustomerFormDTO;
import com.estate.dto.CustomerListDTO;
import com.estate.dto.StaffFormDTO;
import com.estate.dto.StaffListDTO;
import com.estate.repository.entity.StaffEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface StaffService {
    Long countAllStaffs();
    List<StaffEntity> getStaffName();
    Page<StaffListDTO> getStaffs(int page, int size, String role);
    Page<StaffListDTO> search(Map<String, String> filter, int page, int size);
    void save(StaffFormDTO dto);
}
