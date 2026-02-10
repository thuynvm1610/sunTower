package com.estate.controller.staff;

import com.estate.dto.ContractFeeDTO;
import com.estate.security.CustomUserDetails;
import com.estate.service.ContractService;
import com.estate.service.CustomerService;
import com.estate.service.InvoiceService;
import com.estate.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/staff")
public class StaffInvoiceController {
    @Autowired
    StaffService staffService;

    @Autowired
    CustomerService customerService;

    @Autowired
    ContractService contractService;

    @Autowired
    InvoiceService invoiceService;

    @GetMapping("/invoices")
    public String invoice(
            Model model,
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam(required = false, defaultValue = "") String status
        ) {
        model.addAttribute("customers", customerService.getCustomersNameByStaff(user.getCustomerId()));

        model.addAttribute("staffName", staffService.getStaffName(user.getCustomerId()));

        model.addAttribute("staffAvatar", staffService.getStaffAvatar(user.getCustomerId()));

        model.addAttribute("status", status);

        Map<Long, List<Long>> contracts = contractService.getActiveContracts();
        model.addAttribute("contracts", contracts);

        Map<Long, ContractFeeDTO> contractFees = contractService.getContractsFees();
        model.addAttribute("contractFees", contractFees);

        Map<Long, Integer> rentAreas = invoiceService.getRentAreaByContract();
        model.addAttribute("rentAreas", rentAreas);

        return "staff/invoice-list";
    }
}
