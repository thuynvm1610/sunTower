package com.estate.api.admin;

import com.estate.dto.BuildingFilterDTO;
import com.estate.dto.BuildingListDTO;
import com.estate.dto.CustomerListDTO;
import com.estate.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/customer")
public class CustomerAPI {
    @Autowired
    CustomerService customerService;

    @GetMapping("/list/page")
    public Page<CustomerListDTO> getBuildingsPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {
        return customerService.getCustomers(page - 1, size);
    }

    @GetMapping("/search/page")
    public Page<CustomerListDTO> getBuildingsSearchPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String fullName
    ) {
        Page<CustomerListDTO> result = customerService.search(fullName, page - 1, size);
        return result;
    }
}
