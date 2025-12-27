package com.estate.service;

import com.estate.dto.InvoiceDetailDTO;
import com.estate.dto.InvoiceFilterDTO;
import com.estate.dto.InvoiceFormDTO;
import com.estate.dto.InvoiceListDTO;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface InvoiceService {
    String findTotalAmountByCustomerId(Long id);
    Long getTotalUnpaidInvoices(Long customerId);
    InvoiceDetailDTO getDetailInvoice(Long customerId);
    InvoiceFormDTO findById(Long invoiceId);
    List<InvoiceDetailDTO> getDetailInvoices(Long customerId);
    BigDecimal getTotalAmountPayable(Long customerId);
    Long getTotalPaidInvoice(Long customerId);
    Page<InvoiceDetailDTO> getInvoices(int page, int size, Integer  month, Integer  year, Long customerId);
    Page<InvoiceListDTO> getInvoices(int page, int size);
    Page<InvoiceListDTO> search(InvoiceFilterDTO filter, int page, int size);
    void delete(Long id);
    InvoiceDetailDTO viewById(Long invoiceId);
    void invoiceConfirm(Long id);
    void save(InvoiceFormDTO dto);
    Integer getRentArea(Long id);
}
