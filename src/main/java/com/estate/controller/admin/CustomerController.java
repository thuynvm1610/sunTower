package com.estate.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/customer")
public class CustomerController {
    @GetMapping("/list")
    public String listBuildings(Model model) {
        model.addAttribute("page", "customer");
        return "admin/customer-list";
    }
}
