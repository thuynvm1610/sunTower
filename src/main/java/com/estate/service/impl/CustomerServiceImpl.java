package com.estate.service.impl;

import com.estate.converter.CustomerListConverter;
import com.estate.dto.BuildingListDTO;
import com.estate.dto.CustomerListDTO;
import com.estate.dto.PotentialCustomersDTO;
import com.estate.repository.CustomerRepository;
import com.estate.repository.entity.BuildingEntity;
import com.estate.repository.entity.CustomerEntity;
import com.estate.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerListConverter customerListConverter;

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
}
