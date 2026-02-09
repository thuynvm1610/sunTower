package com.estate.controller.staff;

import com.estate.security.CustomUserDetails;
import com.estate.service.ContractService;
import com.estate.service.InvoiceService;
import com.estate.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/staff")
public class StaffDashboardController {
    @Autowired
    StaffService staffService;

    @Autowired
    ContractService contractService;

    @Autowired
    InvoiceService invoiceService;

    @GetMapping("/dashboard")
    public String staffDashboard(
            Model model,
            @AuthenticationPrincipal CustomUserDetails user
        ) {
        Long staffID = user.getCustomerId();

        model.addAttribute("buildingCnt", staffService.getBuildingCnt(staffID));
        model.addAttribute("contractCnt", contractService.getContractCnt(staffID));
        model.addAttribute("customerCnt", staffService.getCustomertCnt(staffID));
        model.addAttribute("unpaidInvoiceCnt", invoiceService.getTotalUnpaidInvoices(staffID));

        model.addAttribute("overdueInvoices", invoiceService.getOverdueInvoices(staffID));

        model.addAttribute("expiringInvoices", invoiceService.getExpiringInvoices(staffID));

        model.addAttribute("expiringContracts", contractService.getExpiringContracts(staffID));

        model.addAttribute("staffName", staffService.getStaffName(user.getCustomerId()));

        model.addAttribute("staffAvatar", staffService.getStaffAvatar(user.getCustomerId()));

        return "/staff/dashboard";
    }
}
