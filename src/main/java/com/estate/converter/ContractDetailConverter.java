package com.estate.converter;


import com.estate.dto.BuildingListDTO;
import com.estate.dto.ContractDetailDTO;
import com.estate.repository.entity.ContractEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContractDetailConverter {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BuildingListConverter buildingListConverter;

    public ContractDetailDTO toDto(ContractEntity entity) {
        ContractDetailDTO dto = modelMapper.map(entity, ContractDetailDTO.class);
        BuildingListDTO buildingDto = buildingListConverter.toDto(entity.getBuilding(), "");
        dto.setBuilding(buildingDto);
        dto.setStaff(entity.getStaff());
        dto.setCustomer(entity.getCustomer());
        return dto;
    }
}
