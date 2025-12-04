package com.estate.controller.customer;

import com.estate.security.CustomUserDetails;
import com.estate.service.ContractService;
import com.estate.service.InvoiceService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/customer")
public class CustomerHomeController {

    @Autowired
    ContractService contractService;

    @Autowired
    InvoiceService invoiceService;

    @GetMapping("/home")
    public String home(
            Model model,
            HttpServletRequest request,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        model.addAttribute(
                "today",
                LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
                )
        );

        model.addAttribute("clientIp", request.getRemoteAddr());

        Long customerId = user.getCustomerId();

        Long totalContracts = contractService.getContractCountByCustomer(customerId);
        model.addAttribute("totalContracts", totalContracts);

        String totalPayment = invoiceService.findTotalAmountByCustomerId(customerId);
        model.addAttribute("totalPayment", totalPayment);

        return "customer/home";
    }
}
