package com.estate.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

public interface InvoiceService {
    String findTotalAmountByCustomerId(Long id);
    Long getTotalUnpaidInvoices(Long customerId);
}
