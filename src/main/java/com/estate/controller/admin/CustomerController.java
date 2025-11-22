package com.estate.controller.admin;

import com.estate.dto.BuildingDetailDTO;
import com.estate.dto.CustomerDetailDTO;
import com.estate.service.CustomerService;
import com.estate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/customer")
public class CustomerController {
    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

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

    @GetMapping("/{id}")
    public String detailCustomer(
            @PathVariable("id") Long id,
            Model model
    ) {
        CustomerDetailDTO customer = customerService.viewById(id);
        model.addAttribute("customer", customer);

        model.addAttribute("page", "customer");

        return "admin/customer-detail";
    }
}
