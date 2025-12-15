package com.estate.repository.custom;

import com.estate.dto.InvoiceFilterDTO;
import com.estate.repository.entity.InvoiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InvoiceRepositoryCustom {
    Page<InvoiceEntity> searchInvoices(InvoiceFilterDTO filter, Pageable pageable);
}
