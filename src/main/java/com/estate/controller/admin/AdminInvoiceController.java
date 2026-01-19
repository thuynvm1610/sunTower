package com.estate.controller.admin;

import com.estate.dto.ContractFeeDTO;
import com.estate.dto.InvoiceDetailDTO;
import com.estate.dto.InvoiceFilterDTO;
import com.estate.dto.InvoiceFormDTO;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/invoice")
public class AdminInvoiceController {
    @Autowired
    CustomerService customerService;

    @Autowired
    InvoiceService invoiceService;

    @Autowired
    ContractService contractService;

    @Autowired
    StaffService staffService;

    @GetMapping("/list")
    public String listInvoices(
            Model model,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        model.addAttribute("customers", customerService.getCustomersName());

        model.addAttribute("page", "invoice");

        model.addAttribute("staffName", staffService.getStaffName(user.getCustomerId()));

        model.addAttribute("staffAvatar", staffService.getStaffAvatar(user.getCustomerId()));

        return "admin/invoice-list";
    }

    @GetMapping("/search")
    public String searchInvoices(
            InvoiceFilterDTO filter,
            Model model,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        model.addAttribute("filter", filter);

        model.addAttribute("customers", customerService.getCustomersName());

        model.addAttribute("page", "invoice");

        model.addAttribute("staffName", staffService.getStaffName(user.getCustomerId()));

        model.addAttribute("staffAvatar", staffService.getStaffAvatar(user.getCustomerId()));

        return "admin/invoice-search";
    }

    @GetMapping("/add")
    public String addInvoiceForm(
            Model model,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        model.addAttribute("customers", customerService.getCustomersName());

        Map<Long, List<Long>> contracts = contractService.getActiveContracts();
        model.addAttribute("contracts", contracts);

        Map<Long, ContractFeeDTO> contractFees = contractService.getContractsFees();
        model.addAttribute("contractFees", contractFees);

        Map<Long, Integer> rentAreas = invoiceService.getRentAreaByContract();
        model.addAttribute("rentAreas", rentAreas);

        model.addAttribute("page", "invoice");

        model.addAttribute("staffName", staffService.getStaffName(user.getCustomerId()));

        model.addAttribute("staffAvatar", staffService.getStaffAvatar(user.getCustomerId()));

        return "admin/invoice-add";
    }

    @GetMapping("/{id}")
    public String detailInvoice(
            @PathVariable("id") Long id,
            Model model,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        InvoiceDetailDTO invoice = invoiceService.viewById(id);
        model.addAttribute("invoice", invoice);

        model.addAttribute("page", "invoice");

        model.addAttribute("staffName", staffService.getStaffName(user.getCustomerId()));

        model.addAttribute("staffAvatar", staffService.getStaffAvatar(user.getCustomerId()));

        return "admin/invoice-detail";
    }

    @GetMapping("/edit/{id}")
    public String editInvoice(
            @PathVariable("id") Long id,
            Model model,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        InvoiceFormDTO invoice = invoiceService.findById(id);
        model.addAttribute("invoice", invoice);

        Integer rentArea = invoiceService.getRentArea(id);
        model.addAttribute("rentArea", rentArea);

        model.addAttribute("customers", customerService.getCustomersName());

        model.addAttribute("page", "invoice");

        model.addAttribute("staffName", staffService.getStaffName(user.getCustomerId()));

        model.addAttribute("staffAvatar", staffService.getStaffAvatar(user.getCustomerId()));

        return "admin/invoice-edit";
    }
}
