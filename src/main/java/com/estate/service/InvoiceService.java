package com.estate.service;

import com.estate.dto.InvoiceDetailDTO;
import com.estate.repository.entity.InvoiceEntity;

import java.math.BigDecimal;
import java.util.List;

public interface InvoiceService {
    String findTotalAmountByCustomerId(Long id);
    Long getTotalUnpaidInvoices(Long customerId);
    InvoiceDetailDTO getDetailInvoice(Long customerId);
    InvoiceEntity findById(Long invoiceId);
    List<InvoiceDetailDTO> getDetailInvoices(Long customerId);
    BigDecimal getTotalAmountPayable(Long customerId);
}
