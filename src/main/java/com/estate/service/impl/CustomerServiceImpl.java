package com.estate.service.impl;

import com.estate.dto.PotentialCustomersDTO;
import com.estate.dto.StaffPerformanceDTO;
import com.estate.repository.BuildingRepository;
import com.estate.repository.CustomerRepository;
import com.estate.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

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
}
