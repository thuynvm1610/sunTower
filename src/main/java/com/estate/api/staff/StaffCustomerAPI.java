package com.estate.api.staff;

import com.estate.dto.ContractDetailDTO;
import com.estate.dto.ContractFilterDTO;
import com.estate.dto.CustomerDetailDTO;
import com.estate.dto.CustomerListDTO;
import com.estate.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/staff/customers")
public class StaffCustomerAPI {
    @Autowired
    CustomerService customerService;

    public StaffCustomerAPI(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/search")
    public Page<CustomerDetailDTO> getContractsSearchPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(required = false) String fullName
    ) {
        return customerService.searchByStaff(fullName, page - 1, size);
    }
}
