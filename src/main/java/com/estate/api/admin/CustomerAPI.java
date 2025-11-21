package com.estate.api.admin;

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

        return customerService.getBuildings(page - 1, size);
    }
}
