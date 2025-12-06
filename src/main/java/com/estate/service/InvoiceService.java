package com.estate.service;

import com.estate.dto.InvoiceDetailDTO;

public interface InvoiceService {
    String findTotalAmountByCustomerId(Long id);
    Long getTotalUnpaidInvoices(Long customerId);
    InvoiceDetailDTO getDetailInvoice(Long customerId);
}
