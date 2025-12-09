package com.estate.converter;

import com.estate.dto.InvoiceDetailDetailDTO;
import com.estate.repository.entity.InvoiceDetailEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvoiceDetailDetailConverter {
    @Autowired
    private ModelMapper modelMapper;

    public InvoiceDetailDetailDTO toDTO (InvoiceDetailEntity entity) {
        InvoiceDetailDetailDTO dto = modelMapper.map(entity, InvoiceDetailDetailDTO.class);
        return dto;
    }
}
