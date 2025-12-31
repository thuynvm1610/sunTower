package com.estate.service.impl;

import com.estate.converter.*;
import com.estate.dto.*;
import com.estate.exception.BusinessException;
import com.estate.repository.ContractRepository;
import com.estate.repository.CustomerRepository;
import com.estate.repository.StaffRepository;
import com.estate.repository.entity.ContractEntity;
import com.estate.repository.entity.CustomerEntity;
import com.estate.repository.entity.StaffEntity;
import com.estate.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerListConverter customerListConverter;

    @Autowired
    CustomerFormConverter customerFormConverter;

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    ContractRepository contractRepository;

    @Autowired
    CustomerDetailConverter customerDetailConverter;

    @Autowired
    ContractDetailConverter contractDetailConverter;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public long countAll() {
        return customerRepository.count();
    }

    @Override
    public List<PotentialCustomersDTO> getTopCustomers() {
        List<Object[]> rawData = customerRepository.countContractsByCustomer((Pageable) PageRequest.of(0, 5));

        return rawData.stream().map(r -> {
            Long customerId = (Long) r[0];
            String fullName = (String) r[1];
            Long contractCount = (Long) r[2];

            return new PotentialCustomersDTO(
                    customerId,
                    fullName,
                    contractCount
            );
        }).collect(Collectors.toList());
    }

    @Override
    public Page<CustomerListDTO> getCustomers(int page, int size) {
        Page<CustomerEntity> customerPage = customerRepository.findAll(PageRequest.of(page, size));

        // Tạo list chứa DTO
        List<CustomerListDTO> dtoList = new ArrayList<>();

        // Duyệt qua từng CustomerEntity
        for (CustomerEntity c : customerPage) {
            // Convert entity sang DTO
            CustomerListDTO dto = customerListConverter.toDto(c);
            dtoList.add(dto);
        }

        // Tạo PageImpl giữ nguyên thông tin phân trang gốc
        Page<CustomerListDTO> result = new PageImpl<>(
                dtoList,
                customerPage.getPageable(),
                customerPage.getTotalElements()
        );

        return result;
    }

    @Override
    public Page<CustomerListDTO> search(String fullName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CustomerEntity> customerPage = customerRepository.findByFullNameContainingIgnoreCase(fullName, pageable);

        // Tạo list chứa DTO
        List<CustomerListDTO> dtoList = new ArrayList<>();

        // Duyệt qua từng CustomerEntity
        for (CustomerEntity c : customerPage) {
            // Convert entity sang DTO
            CustomerListDTO dto = customerListConverter.toDto(c);
            dtoList.add(dto);
        }

        // Tạo PageImpl giữ nguyên thông tin phân trang gốc
        Page<CustomerListDTO> result = new PageImpl<>(
                dtoList,
                customerPage.getPageable(),
                customerPage.getTotalElements()
        );

        return result;
    }

    @Override
    public void save(CustomerFormDTO dto) {
        CustomerEntity entity;

        if (customerRepository.existsByUsername(dto.getUsername()) || staffRepository.existsByUsername(dto.getUsername())) {
            throw new BusinessException("Tên đăng nhập đã tồn tại");
        }

        if (customerRepository.existsByEmail(dto.getEmail()) || staffRepository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Email đã tồn tại");
        }

        if (customerRepository.existsByPhone(dto.getPhone()) || staffRepository.existsByPhone(dto.getPhone())) {
            throw new BusinessException("Số điện thoại đã tồn tại");
        }

        if (dto.getId() != null) {
            // Update
            entity = customerRepository.findById(dto.getId())
                    .orElseThrow(() -> new BusinessException("Không tìm thấy khách hàng để sửa"));
        } else {
            // Thêm mới
            entity = customerFormConverter.toEntity(dto);
        }

        // Mã hóa dữ liệu
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));

        // Lưu danh sách nhân viên quản lý
        List<StaffEntity> staffs = staffRepository.findAllById(dto.getStaffIds());
        entity.setStaffs_customers(staffs);

        // Lưu khách hàng
        customerRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new BusinessException("Không tìm thấy khách hàng để xóa");
        }
        long count = contractRepository.countByCustomerId(id);
        if (count > 0) {
            throw new BusinessException("Không thể xóa! Khách hàng đang có hợp đồng liên quan.");
        }
        customerRepository.deleteById(id);
    }

    @Override
    public CustomerDetailDTO viewById(Long id) {
        CustomerEntity customerEntity = customerRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Không tìm thấy khách hàng"));
        return customerDetailConverter.toDTO(customerEntity);
    }

    @Override
    public Map<String, Long> getCustomersName() {
        List<CustomerEntity> customerEntities = customerRepository.findAll();
        Map<String, Long> result = new HashMap<>();
        for (CustomerEntity c : customerEntities) {
            result.put(c.getFullName(), c.getId());
        }
        return result;
    }

    @Override
    public List<ContractDetailDTO> getCustomerContracts(Long customerId) {
        List<ContractEntity> contractEntities = contractRepository.findByCustomerId(customerId);
        List<ContractDetailDTO> result = new ArrayList<>();
        int cnt = 1;
        for (ContractEntity c : contractEntities) {
            if (cnt == 3) break;
            else {
                result.add(contractDetailConverter.toDto(c));
                cnt++;
            }
        }

        return result;
    }

    @Override
    public CustomerEntity findById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new BusinessException("Không tìm thấy khách hàng"));
    }

    @Override
    public void usernameUpdate(UsernameChangeDTO dto, Long customerId) {
        CustomerEntity customer = this.findById(customerId);

        boolean isCorrect = passwordEncoder.matches(
                dto.getPassword(),
                customer.getPassword()
        );

        if (!isCorrect) {
            throw new BusinessException("Mật khẩu sai");
        }

        customerRepository.usernameUpdate(dto.getNewUsername(), customerId);
    }

    @Override
    public void emailUpdate(EmailChangeDTO dto, Long customerId) {
        CustomerEntity customer = this.findById(customerId);

        boolean isCorrect = passwordEncoder.matches(
                dto.getPassword(),
                customer.getPassword()
        );

        if (!isCorrect) {
            throw new BusinessException("Mật khẩu sai");
        }

        if (customerRepository.existsByEmailAndIdNot(dto.getNewEmail(), customerId) ||
            staffRepository.existsByEmail(dto.getNewEmail())
        ) {
            throw new BusinessException("Email này đã được sử dụng");
        }

        customerRepository.emailUpdate(dto.getNewEmail(), customerId);
    }

}
