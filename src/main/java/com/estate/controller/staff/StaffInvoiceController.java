package com.estate.controller.staff;

import com.estate.security.CustomUserDetails;
import com.estate.service.CustomerService;
import com.estate.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/staff")
public class StaffInvoiceController {
    @Autowired
    StaffService staffService;

    @Autowired
    CustomerService customerService;

    @GetMapping("/invoices")
    public String invoice(
            Model model,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        model.addAttribute("customers", customerService.getCustomersName());

        model.addAttribute("staffName", staffService.getStaffName(user.getCustomerId()));

        model.addAttribute("staffAvatar", staffService.getStaffAvatar(user.getCustomerId()));

        return "staff/invoice-list";
    }
}
