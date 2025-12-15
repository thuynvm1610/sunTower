package com.estate.service.impl;

import com.estate.converter.InvoiceDetailConverter;
import com.estate.converter.InvoiceListDTOConverter;
import com.estate.dto.BuildingListDTO;
import com.estate.dto.InvoiceDetailDTO;
import com.estate.dto.InvoiceFilterDTO;
import com.estate.dto.InvoiceListDTO;
import com.estate.repository.InvoiceRepository;
import com.estate.repository.entity.BuildingEntity;
import com.estate.repository.entity.InvoiceEntity;
import com.estate.repository.entity.UtilityMeterEntity;
import com.estate.service.InvoiceService;
import com.estate.service.UtilityMeterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {
    @Autowired
    InvoiceRepository invoiceRepository;

    @Autowired
    UtilityMeterService utilityMeterService;

    @Autowired
    InvoiceDetailConverter invoiceDetailConverter;

    @Autowired
    InvoiceListDTOConverter invoiceListDTOConverter;

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

    @Override
    public InvoiceDetailDTO getDetailInvoice(Long customerId) {
        Long unpaidInvoices = this.getTotalUnpaidInvoices(customerId);
        if (unpaidInvoices == 0) {
            return null;
        }

        InvoiceEntity invoice = invoiceRepository.getFirstByCustomerIdAndStatus(customerId, "PENDING");

        UtilityMeterEntity utilityMeter = utilityMeterService.findByContractIdAndMonthAndYear(
                invoice.getContract().getId(), invoice.getMonth(), invoice.getYear());

        return invoiceDetailConverter.toDTO(invoice, utilityMeter);
    }

    @Override
    public InvoiceEntity findById(Long invoiceId) {
        return invoiceRepository.findById(invoiceId).orElse(null);
    }

    @Override
    public List<InvoiceDetailDTO> getDetailInvoices(Long customerId) {
        Long unpaidInvoices = this.getTotalUnpaidInvoices(customerId);
        if (unpaidInvoices == 0) {
            return null;
        }

        List<InvoiceEntity> invoices = invoiceRepository.findAllByCustomerIdAndStatus(customerId, "PENDING");

        List<InvoiceDetailDTO> res = new ArrayList<>();
        for (InvoiceEntity i : invoices) {
            UtilityMeterEntity utilityMeter = utilityMeterService.findByContractIdAndMonthAndYear(
                    i.getContract().getId(), i.getMonth(), i.getYear());
            res.add(invoiceDetailConverter.toDTO(i, utilityMeter));
        }

        return res;
    }

    @Override
    public BigDecimal getTotalAmountPayable(Long customerId) {
        return invoiceRepository.findAllByCustomerIdAndStatus(customerId, "PENDING")
                .stream()
                .map(InvoiceEntity::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Long getTotalPaidInvoice(Long customerId) {
        return invoiceRepository.countByCustomerIdAndStatus(customerId, "PAID");
    }

    @Override
    public Page<InvoiceDetailDTO> getInvoices(int page, int size, Integer month, Integer year, Long customerId) {
        Page<InvoiceEntity> invoicePage = invoiceRepository.search(
                month,
                year,
                customerId,
                "PAID",
                PageRequest.of(page, size)
        );

        // Tạo list chứa DTO
        List<InvoiceDetailDTO> dtoList = new ArrayList<>();

        // Duyệt qua từng InvoiceEntity
        for (InvoiceEntity i : invoicePage) {
            UtilityMeterEntity utilityMeter = utilityMeterService.findByContractIdAndMonthAndYear(
                    i.getContract().getId(), i.getMonth(), i.getYear());
            InvoiceDetailDTO dto = invoiceDetailConverter.toDTO(i, utilityMeter);

            dtoList.add(dto);
        }

        // Tạo PageImpl giữ nguyên thông tin phân trang gốc
        Page<InvoiceDetailDTO> result = new PageImpl<>(
                dtoList,
                invoicePage.getPageable(),
                invoicePage.getTotalElements()
        );

        return result;
    }

    @Override
    public Page<InvoiceListDTO> getInvoices(int page, int size) {
        Page<InvoiceEntity> invoicePage = invoiceRepository.findAll(PageRequest.of(page, size));

        // Tạo list chứa DTO
        List<InvoiceListDTO> dtoList = new ArrayList<>();

        // Duyệt qua từng InvoiceEntity
        for (InvoiceEntity i : invoicePage) {
            // Convert entity sang DTO
            InvoiceListDTO dto = invoiceListDTOConverter.toDTO(i);

            dtoList.add(dto);
        }

        // Tạo PageImpl giữ nguyên thông tin phân trang gốc
        Page<InvoiceListDTO> result = new PageImpl<>(
                dtoList,
                invoicePage.getPageable(),
                invoicePage.getTotalElements()
        );

        return result;
    }

    @Override
    public Page<InvoiceListDTO> search(InvoiceFilterDTO filter, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<InvoiceEntity> invoicePage = invoiceRepository.searchInvoices(filter, pageable);

        // Tạo list chứa DTO
        List<InvoiceListDTO> dtoList = new ArrayList<>();

        // Duyệt qua từng InvoiceEntity
        for (InvoiceEntity i : invoicePage) {
            InvoiceListDTO dto = invoiceListDTOConverter.toDTO(i);

            dtoList.add(dto);
        }

        // Tạo PageImpl giữ nguyên thông tin phân trang gốc
        Page<InvoiceListDTO> result = new PageImpl<>(
                dtoList,
                invoicePage.getPageable(),
                invoicePage.getTotalElements()
        );

        return result;
    }

}
