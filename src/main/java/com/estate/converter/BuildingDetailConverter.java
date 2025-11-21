package com.estate.converter;

import com.estate.dto.BuildingDetailDTO;
import com.estate.repository.entity.BuildingEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BuildingDetailConverter {
    @Autowired
    private ModelMapper modelMapper;

    public BuildingDetailDTO toDTO(BuildingEntity entity) {
        BuildingDetailDTO dto = modelMapper.map(entity, BuildingDetailDTO.class);

        if (entity.getDirection() != null) {
            dto.setDirection(entity.getDirection().getLabel());
        }

        if (entity.getLevel() != null) {
            dto.setLevel(entity.getLevel().getLabel());
        }

        if (entity.getDistrict() != null) {
            dto.setDistrict(entity.getDistrict().getName());
        }

        if (entity.getRentAreas() != null && !entity.getRentAreas().isEmpty()) {
            String rentAreaValues = entity.getRentAreas().stream()
                    .map(a -> a.getValue().toString())
                    .collect(Collectors.joining(","));
            dto.setRentAreaValues(rentAreaValues);
        }

        if (entity.getStaffs_buildings() != null && !entity.getStaffs_buildings().isEmpty()) {
            dto.setStaffs(
                    entity.getStaffs_buildings().stream()
                            .map(s -> s.getFullName())
                            .collect(Collectors.toList())
            );
        }
        return dto;
    }
}
