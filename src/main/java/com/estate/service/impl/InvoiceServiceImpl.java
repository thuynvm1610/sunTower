package com.estate.service.impl;

import com.estate.repository.InvoiceRepository;
import com.estate.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    InvoiceRepository invoiceRepository;

    @Override
    public String findTotalAmountByCustomerId(Long id) {
        BigDecimal amount = invoiceRepository.findTotalAmountByCustomerId(id);

        if (amount == null) return "0";

        long value = amount.longValue();

        if (value < 1_000_000_000) {
            // Nhỏ hơn 1 tỷ → giữ nguyên dạng 1.234.567
            return String.format("%,d", value).replace(",", ".");
        } else {
            // Lớn hơn hoặc bằng 1 tỷ → chia cho 1 tỷ
            double ty = value / 1_000_000_000.0;

            // Giữ 1 hoặc 2 số thập phân khi cần
            if (ty % 1 == 0) {
                return String.format("%.0f tỷ", ty);   // Ví dụ: 3 tỷ
            } else {
                return String.format("%.1f tỷ", ty);   // Ví dụ: 1.2 tỷ
            }
        }
    }

    @Override
    public Long getTotalUnpaidInvoices(Long customerId) {
        return invoiceRepository.countByCustomerIdAndStatus(customerId, "PENDING");
    }
}
