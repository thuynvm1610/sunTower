package com.estate.controller.customer;

import com.estate.security.CustomUserDetails;
import com.estate.service.ContractService;
import com.estate.service.CustomerService;
import com.estate.service.InvoiceService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/customer/home")
@RequiredArgsConstructor
public class CustomerHomeController {
    private final ContractService contractService;
    private final InvoiceService invoiceService;
    private final CustomerService customerService;

    @GetMapping("")
    public String homePage(
            Model model,
            HttpServletRequest request,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long userId = user.getUserId();

        model.addAttribute("today", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));

        model.addAttribute("clientIp", request.getRemoteAddr());

        model.addAttribute("totalContracts", contractService.getContractCountByCustomer(userId));

        model.addAttribute("totalPayment", invoiceService.findTotalAmountByCustomerId(userId));
        model.addAttribute("detailInvoice", invoiceService.getDetailInvoice(userId));
        model.addAttribute("totalUnpaidInvoices", invoiceService.getTotalUnpaidInvoicesByCustomer(userId));

        model.addAttribute("contracts", customerService.getCustomerContracts(userId));

        return "customer/home";
    }
}
