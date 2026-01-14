package com.estate.converter;

import com.estate.dto.OverdueInvoiceListDTO;
import com.estate.repository.entity.CustomerEntity;
import com.estate.repository.entity.InvoiceEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OverdueInvoiceListConverter {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CustomerListConverter customerListConverter;

    public OverdueInvoiceListDTO toDTO(InvoiceEntity entity) {
        OverdueInvoiceListDTO dto = new OverdueInvoiceListDTO();
        modelMapper.map(entity, dto);

        if (entity.getCustomer() != null) {
            dto.setCustomer(customerListConverter.toDto(entity.getCustomer()));
        }

        return dto;
    }
}
