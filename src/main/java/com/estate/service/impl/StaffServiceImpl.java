package com.estate.service.impl;

import com.estate.converter.StaffDetailConverter;
import com.estate.converter.StaffFormConverter;
import com.estate.converter.StaffListConverter;
import com.estate.dto.*;
import com.estate.exception.BusinessException;
import com.estate.repository.CustomerRepository;
import com.estate.repository.StaffRepository;
import com.estate.repository.entity.CustomerEntity;
import com.estate.repository.entity.StaffEntity;
import com.estate.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private StaffListConverter staffListConverter;

    @Autowired
    private StaffFormConverter staffFormConverter;

    @Autowired
    private StaffDetailConverter staffDetailConverter;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Long countAllStaffs() {
        return staffRepository.countByRole("STAFF");
    }

    @Override
    public List<StaffEntity> getStaffsName() {
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

        String fullName = filter.get("fullName");
        String role = filter.get("role");

        Page<StaffEntity> staffPage = staffRepository.search(fullName, role, pageable);

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

        if (staffRepository.existsByUsername(dto.getUsername()) || customerRepository.existsByUsername(dto.getUsername())) {
            throw new BusinessException("Tên đăng nhập đã tồn tại");
        }

        if (staffRepository.existsByEmail(dto.getEmail()) || customerRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Email đã tồn tại");
        }

        if (staffRepository.existsByPhone(dto.getPhone()) || customerRepository.existsByPhone(dto.getPhone())) {
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

        // Mã hóa dữ liệu
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));

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

    @Override
    public StaffDetailDTO viewById(Long id) {
        StaffEntity staffEntity = staffRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Không tìm thấy nhân viên"));
        StaffDetailDTO staffDetailDTO = staffDetailConverter.toDTO(staffEntity);
        return staffDetailDTO;
    }

    @Override
    public Long getBuildingCnt(Long staffId) {
        return staffRepository.countBuildingsByStaffId(staffId);
    }

    @Override
    public Long getCustomertCnt(Long staffId) {
        return staffRepository.countCustomersByStaffId(staffId);
    }

    @Override
    public String getStaffName(Long staffId) {
        return staffRepository.findById(staffId).get().getFullName();
    }

    @Override
    public String getStaffAvatar(Long staffId) {
        return staffRepository.findById(staffId).get().getImage();
    }

    @Override
    public void usernameUpdate(UsernameChangeDTO dto, Long staffId) {
        StaffEntity staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new BusinessException("Không tìm thấy nhân viên"));

        if (staffRepository.existsByUsernameAndIdNot(dto.getNewUsername(), staffId)
        || customerRepository.existsByUsername(dto.getNewUsername())) {
            throw new BusinessException("Tên đăng nhập đã được sử dụng!");
        }

        boolean isCorrect = passwordEncoder.matches(
                dto.getPassword(),
                staff.getPassword()
        );

        if (!isCorrect) {
            throw new BusinessException("Mật khẩu sai");
        }

        staffRepository.usernameUpdate(dto.getNewUsername(), staffId);
    }

    @Override
    public void emailUpdate(EmailChangeDTO dto, Long staffId) {
        StaffEntity staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new BusinessException("Không tìm thấy nhân viên"));

        boolean isCorrect = passwordEncoder.matches(
                dto.getPassword(),
                staff.getPassword()
        );

        if (!isCorrect) {
            throw new BusinessException("Mật khẩu sai");
        }

        if (customerRepository.existsByEmail(dto.getNewEmail()) ||
                staffRepository.existsByEmailAndIdNot(dto.getNewEmail(), staffId)
        ) {
            throw new BusinessException("Email này đã được sử dụng");
        }

        staffRepository.emailUpdate(dto.getNewEmail(), staffId);
    }

    @Override
    public void phoneNumberUpdate(PhoneNumberChangeDTO dto, Long staffId) {
        StaffEntity staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new BusinessException("Không tìm thấy nhân viên"));

        boolean isCorrect = passwordEncoder.matches(
                dto.getPassword(),
                staff.getPassword()
        );

        if (!isCorrect) {
            throw new BusinessException("Mật khẩu sai");
        }

        if (customerRepository.existsByPhone(dto.getNewPhoneNumber()) ||
                staffRepository.existsByPhoneAndIdNot(dto.getNewPhoneNumber(), staffId)
        ) {
            throw new BusinessException("Số điện thoại này đã được sử dụng");
        }

        staffRepository.phoneNumberUpdate(dto.getNewPhoneNumber(), staffId);
    }

    @Override
    public void passwordUpdate(PasswordChangeDTO dto, Long staffId) {
        StaffEntity staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new BusinessException("Không tìm thấy nhân viên"));

        boolean isCorrect = passwordEncoder.matches(
                dto.getCurrentPassword(),
                staff.getPassword()
        );

        if (!isCorrect) {
            throw new BusinessException("Mật khẩu hiện tại không đúng");
        }

        if (!dto.getConfirmPassword().equals(dto.getNewPassword())) {
            throw new BusinessException("Mật khẩu xác nhận sai");
        }

        String encodedPassword = passwordEncoder.encode(dto.getNewPassword());
        staffRepository.passwordUpdate(encodedPassword, staffId);
    }

    @Override
    public StaffEntity findById(Long staffId) {
        return staffRepository.findById(staffId)
                .orElseThrow(() -> new BusinessException("Không tìm thấy nhân viên"));
    }
}
