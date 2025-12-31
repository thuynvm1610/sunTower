package com.estate.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer/profile")
public class CustomerProfileController {
    @GetMapping("")
    public String listBuildings() {
        return "customer/profile";
    }
}
