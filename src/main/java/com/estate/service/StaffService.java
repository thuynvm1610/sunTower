package com.estate.service;

import com.estate.dto.CustomerListDTO;
import com.estate.dto.StaffListDTO;
import com.estate.repository.entity.StaffEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StaffService {
    Long countAllStaffs();
    List<StaffEntity> getStaffName();
    Page<StaffListDTO> getStaffs(int page, int size, String role);
}
