package com.estate.converter;

import com.estate.dto.InvoiceDetailDTO;
import com.estate.repository.entity.InvoiceDetailEntity;
import com.estate.repository.entity.InvoiceEntity;
import com.estate.repository.entity.UtilityMeterEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Component
public class InvoiceDetailConverter {
    @Autowired
    private ModelMapper modelMapper;

    public InvoiceDetailDTO toDTO(InvoiceEntity entity, UtilityMeterEntity utilityMeter) {
        InvoiceDetailDTO dto = modelMapper.map(entity, InvoiceDetailDTO.class);

        String formattedDueDate = entity.getDueDate()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        dto.setDueDate(formattedDueDate);

        String formattedCreatedDate = entity.getCreatedDate()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
        dto.setCreatedDate(formattedCreatedDate);

        BigDecimal totalServiceFeeAmount = entity.getDetails().stream()
                .filter(d -> !d.getDescription().toLowerCase().contains("thuê"))
                .map(InvoiceDetailEntity::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        dto.setTotalServiceFeeAmount(totalServiceFeeAmount);

        dto.setContract(entity.getContract());
        dto.setCustomer(entity.getCustomer());
        dto.setDetails(entity.getDetails());

        dto.setUtilityMeter(utilityMeter);

        return dto;
    }
}
