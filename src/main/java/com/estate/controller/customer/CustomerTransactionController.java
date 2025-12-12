package com.estate.controller.customer;

import com.estate.security.CustomUserDetails;
import com.estate.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer/transaction")
public class CustomerTransactionController {
    @Autowired
    InvoiceService invoiceService;

    @GetMapping("history")
    public String transactionHistory(
            Model model,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long customerId = user.getCustomerId();

        Long totalTransaction = invoiceService.getTotalPaidInvoice(customerId);
        model.addAttribute("totalTransaction", totalTransaction);

        String totalPaidAmount = invoiceService.findTotalAmountByCustomerId(customerId);
        model.addAttribute("totalPaidAmount", totalPaidAmount);

        return "customer/transaction-history";
    }
}
