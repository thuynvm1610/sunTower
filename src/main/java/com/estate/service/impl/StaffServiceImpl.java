package com.estate.service.impl;

import com.estate.converter.StaffFormConverter;
import com.estate.converter.StaffListConverter;
import com.estate.dto.CustomerListDTO;
import com.estate.dto.StaffFormDTO;
import com.estate.dto.StaffListDTO;
import com.estate.exception.BusinessException;
import com.estate.repository.StaffRepository;
import com.estate.repository.entity.CustomerEntity;
import com.estate.repository.entity.StaffEntity;
import com.estate.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private StaffListConverter staffListConverter;

    @Autowired
    private StaffFormConverter staffFormConverter;

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

    @Override
    public Page<StaffListDTO> search(Map<String, String> filter, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<StaffEntity> staffPage;

        String fullName = filter.get("fullName");
        String role = filter.get("role");

        if (role == null || role.isEmpty()) {
            staffPage = staffRepository.findByFullNameContainingIgnoreCase(fullName, pageable);
        }
        else if (fullName == null || fullName.isEmpty()) {
            staffPage = staffRepository.findByRole(pageable, role);
        } else {
            staffPage = staffRepository.findByFullNameContainingIgnoreCaseAndRole(fullName, role, pageable);
        }

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

    @Override
    public void save(StaffFormDTO dto) {
        StaffEntity entity;

        if (staffRepository.existsByUsername(dto.getUsername())) {
            throw new BusinessException("Username đã tồn tại");
        }

        if (staffRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Email đã tồn tại");
        }

        if (staffRepository.existsByPhone(dto.getPhone())) {
            throw new BusinessException("Số điện thoại đã tồn tại");
        }

        if (dto.getId() != null) {
            // Update
            entity = staffRepository.findById(dto.getId())
                    .orElseThrow(() -> new BusinessException("Không tìm thấy nhân viên để sửa"));
        } else {
            // Thêm mới
            entity = staffFormConverter.toEntity(dto);
        }

        // Lưu nhân viên
        StaffEntity saved = staffRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        if (!staffRepository.existsById(id)) {
            throw new BusinessException("Không tìm thấy nhân viên để xóa");
        }
        long buildingCnt = staffRepository.countBuildingsByStaffId(id);
        if (buildingCnt > 0) {
            throw new BusinessException("Không thể xóa! Nhân viên này đang chịu trách nhiệm quản lý tòa nhà.");
        }
        long customerCnt = staffRepository.countCustomersByStaffId(id);
        if (customerCnt > 0) {
            throw new BusinessException("Không thể xóa! Nhân viên này đang chịu trách nhiệm chăm sóc khách hàng.");
        }
        staffRepository.deleteById(id);
    }
}
