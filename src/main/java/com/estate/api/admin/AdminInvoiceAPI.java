package com.estate.api.admin;

import com.estate.dto.InvoiceListDTO;
import com.estate.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/invoice")
public class AdminInvoiceAPI {
    @Autowired
    private InvoiceService invoiceService;
    @GetMapping("/list/page")
    public Page<InvoiceListDTO> getInvoicesPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size) {

        return invoiceService.getInvoices(page - 1, size);
    }
}
