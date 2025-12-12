package com.estate.controller.customer;

import com.estate.dto.InvoiceDetailDTO;
import com.estate.security.CustomUserDetails;
import com.estate.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/customer/invoice")
public class CustomerInvoiceController {
    @Autowired
    InvoiceService invoiceService;

    @GetMapping("list")
    public String invoiceList(
            Model model,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Long customerId = user.getCustomerId();

        Long totalUnpaidInvoices = invoiceService.getTotalUnpaidInvoices(customerId);
        model.addAttribute("totalUnpaidInvoices", totalUnpaidInvoices);

        List<InvoiceDetailDTO> detailInvoices = invoiceService.getDetailInvoices(customerId);
        model.addAttribute("detailInvoices", detailInvoices);

        BigDecimal totalAmountPayable = invoiceService.getTotalAmountPayable(customerId);
        model.addAttribute("totalAmountPayable", totalAmountPayable);

        return "customer/invoice";
    }
}
