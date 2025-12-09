package com.estate.converter;

import com.estate.dto.UtilityMeterDetailDTO;
import com.estate.repository.entity.UtilityMeterEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UtilityMeterDetailConverter {
    @Autowired
    private ModelMapper modelMapper;

    public UtilityMeterDetailDTO toDTO (UtilityMeterEntity entity) {
        UtilityMeterDetailDTO dto = modelMapper.map(entity, UtilityMeterDetailDTO.class);
        return dto;
    }
}
