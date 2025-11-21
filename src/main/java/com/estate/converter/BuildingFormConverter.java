package com.estate.converter;

import com.estate.dto.BuildingFormDTO;
import com.estate.repository.entity.BuildingEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BuildingFormConverter {
    @Autowired
    private ModelMapper modelMapper;

    public BuildingFormDTO toDTO(BuildingEntity entity) {
        BuildingFormDTO dto = modelMapper.map(entity, BuildingFormDTO.class);

        if (entity.getDirection() != null) {
            dto.setDirection(entity.getDirection().getLabel());
        }

        if (entity.getLevel() != null) {
            dto.setLevel(entity.getLevel().getLabel());
        }

        if (entity.getDistrict() != null) {
            dto.setDistrictId(entity.getDistrict().getId());
        }

        if (entity.getRentAreas() != null && !entity.getRentAreas().isEmpty()) {
            String rentAreaValues = entity.getRentAreas().stream()
                    .map(a -> a.getValue().toString())
                    .collect(Collectors.joining(","));
            dto.setRentAreaValues(rentAreaValues);
        }

        if (entity.getStaffs_buildings() != null && !entity.getStaffs_buildings().isEmpty()) {
            dto.setStaffIds(
                    entity.getStaffs_buildings().stream()
                            .map(s -> s.getId())
                            .collect(Collectors.toList())
            );
        }
        return dto;
    }
}
