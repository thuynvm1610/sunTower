package com.estate.api.staff;

import com.estate.dto.CustomerDetailDTO;
import com.estate.dto.InvoiceDetailDTO;
import com.estate.dto.InvoiceFilterDTO;
import com.estate.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/staff/invoices")
public class StaffInvoiceAPI {
    @Autowired
    InvoiceService invoiceService;

    @GetMapping("/search")
    public Page<InvoiceDetailDTO> getInvoicesSearchPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            InvoiceFilterDTO filter
    ) {
        return invoiceService.searchByStaff(filter, page - 1, size);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteInvoice(@PathVariable Long id) {
        invoiceService.delete(id);
        return ResponseEntity.ok().build();
    }
}
