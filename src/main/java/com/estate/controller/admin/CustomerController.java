package com.estate.controller.admin;

import com.estate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/customer")
public class CustomerController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String listBuildings(Model model) {
        model.addAttribute("page", "customer");
        return "admin/customer-list";
    }

    @GetMapping("/search")
    public String searchCustomers(
            @RequestParam(required = false) String fullName,
            Model model
    ) {
        model.addAttribute("fullName", fullName);
        model.addAttribute("page", "customer");
        return "admin/customer-search";
    }

    @GetMapping("/add")
    public String addCustomerForm(Model model) {
        model.addAttribute("staffs", userService.getStaffName());

        model.addAttribute("page", "customer");

        return "admin/customer-add";
    }
}
