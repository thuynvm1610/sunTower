package com.estate.service.impl;

import com.estate.converter.StaffListConverter;
import com.estate.dto.CustomerListDTO;
import com.estate.dto.StaffListDTO;
import com.estate.repository.StaffRepository;
import com.estate.repository.entity.CustomerEntity;
import com.estate.repository.entity.StaffEntity;
import com.estate.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private StaffListConverter staffListConverter;

    @Override
    public Long countAllStaffs() {
        return staffRepository.countByRole("STAFF");
    }

    @Override
    public List<StaffEntity> getStaffName() {
        return staffRepository.findByRole("STAFF");
    }

    @Override
    public Page<StaffListDTO> getStaffs(int page, int size, String role) {
        Page<StaffEntity> staffPage = staffRepository.findByRole(PageRequest.of(page, size), role);

        // Tạo list chứa DTO
        List<StaffListDTO> dtoList = new ArrayList<>();

        // Duyệt qua từng StaffEntity
        for (StaffEntity s : staffPage) {
            // Convert entity sang DTO
            StaffListDTO dto = staffListConverter.toDto(s);
            dtoList.add(dto);
        }

        // Tạo PageImpl giữ nguyên thông tin phân trang gốc
        Page<StaffListDTO> result = new PageImpl<>(
                dtoList,
                staffPage.getPageable(),
                staffPage.getTotalElements()
        );

        return result;
    }
}
