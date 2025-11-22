package com.estate.service;

import com.estate.dto.BuildingFilterDTO;
import com.estate.dto.BuildingListDTO;
import com.estate.dto.CustomerListDTO;
import com.estate.dto.PotentialCustomersDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {
    long countAll();
    List<PotentialCustomersDTO>getTopCustomers();
    Page<CustomerListDTO> getCustomers(int page, int size);
    Page<CustomerListDTO> search(String fullName, int page, int size);
}
