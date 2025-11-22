package com.estate.converter;

import com.estate.dto.BuildingFormDTO;
import com.estate.dto.CustomerFormDTO;
import com.estate.repository.entity.BuildingEntity;
import com.estate.repository.entity.CustomerEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CustomerFormConverter {
    @Autowired
    private ModelMapper modelMapper;

    public CustomerEntity toEntity(CustomerFormDTO dto) {
        CustomerEntity entity = modelMapper.map(dto, CustomerEntity.class);
        return entity;
    }
}
