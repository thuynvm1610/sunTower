package com.estate.controller.customer;

import com.estate.repository.CustomerRepository;
import com.estate.repository.entity.CustomerEntity;
import com.estate.security.CustomUserDetails;
import com.estate.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer/profile")
public class CustomerProfileController {
    @Autowired
    CustomerService customerService;

    @GetMapping("")
    public String listBuildings(
            Model model,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long customerId = user.getCustomerId();
        CustomerEntity customer = customerService.findById(customerId);
        model.addAttribute("customer", customer);

        return "customer/profile";
    }
}
