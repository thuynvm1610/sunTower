package com.estate.api.admin;

import com.estate.dto.InvoiceFilterDTO;
import com.estate.dto.InvoiceListDTO;
import com.estate.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/search/page")
    public Page<InvoiceListDTO> getInvoicesSearchPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            InvoiceFilterDTO filter
    ) {
        return invoiceService.search(filter, page - 1, size);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteInvoice(@PathVariable Long id) {
        invoiceService.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping ("/confirm/{id}")
    public ResponseEntity<?> invoicePay(@PathVariable Long id) {
        invoiceService.invoiceConfirm(id);
        return ResponseEntity.ok("Xác nhận thanh toán thành công!");
    }
}
