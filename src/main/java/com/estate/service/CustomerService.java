package com.estate.service;

import com.estate.dto.*;
import com.estate.repository.entity.ContractEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface CustomerService {
    long countAll();
    List<PotentialCustomersDTO>getTopCustomers();
    Page<CustomerListDTO> getCustomers(int page, int size);
    Page<CustomerListDTO> search(String fullName, int page, int size);
    void save(CustomerFormDTO dto);
    void delete(Long id);
    CustomerDetailDTO viewById(Long id);
    Map<String, Long> getCustomersName();
    InvoiceDetailDTO getDetailInvoice(Long customerId, int currentMonth, int currentYear);
}
