package com.estate.controller.admin;

import com.estate.dto.CustomerDetailDTO;
import com.estate.security.CustomUserDetails;
import com.estate.service.CustomerService;
import com.estate.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/customer")
public class AdminCustomerController {
    @Autowired
    private StaffService staffService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/list")
    public String listBuildings(
            Model model,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        model.addAttribute("page", "customer");

        model.addAttribute("staffName", staffService.getStaffName(user.getCustomerId()));

        model.addAttribute("staffAvatar", staffService.getStaffAvatar(user.getCustomerId()));

        return "admin/customer-list";
    }

    @GetMapping("/search")
    public String searchCustomers(
            @RequestParam(required = false) String fullName,
            Model model,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        model.addAttribute("fullName", fullName);
        model.addAttribute("page", "customer");

        model.addAttribute("staffName", staffService.getStaffName(user.getCustomerId()));

        model.addAttribute("staffAvatar", staffService.getStaffAvatar(user.getCustomerId()));

        return "admin/customer-search";
    }

    @GetMapping("/add")
    public String addCustomerForm(
            Model model,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        model.addAttribute("staffs", staffService.getStaffsName());

        model.addAttribute("page", "customer");

        model.addAttribute("staffName", staffService.getStaffName(user.getCustomerId()));

        model.addAttribute("staffAvatar", staffService.getStaffAvatar(user.getCustomerId()));

        return "admin/customer-add";
    }

    @GetMapping("/{id}")
    public String detailCustomer(
            @PathVariable("id") Long id,
            Model model,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        CustomerDetailDTO customer = customerService.viewById(id);
        model.addAttribute("customer", customer);

        model.addAttribute("page", "customer");

        model.addAttribute("staffName", staffService.getStaffName(user.getCustomerId()));

        model.addAttribute("staffAvatar", staffService.getStaffAvatar(user.getCustomerId()));

        return "admin/customer-detail";
    }
}
