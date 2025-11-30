package com.estate.converter;

import com.estate.dto.BuildingFormDTO;
import com.estate.enums.Direction;
import com.estate.enums.Level;
import com.estate.exception.BusinessException;
import com.estate.repository.DistrictRepository;
import com.estate.repository.StaffRepository;
import com.estate.repository.entity.BuildingEntity;
import com.estate.repository.entity.DistrictEntity;
import com.estate.repository.entity.RentAreaEntity;
import com.estate.repository.entity.StaffEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BuildingFormConverter {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private StaffRepository staffRepository;

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

    public void toEntity(BuildingEntity entity, BuildingFormDTO dto) {

        modelMapper.map(dto, entity);

        if (dto.getDirection() != null && !dto.getDirection().isEmpty()) {
            entity.setDirection(Direction.valueOf(dto.getDirection()));
        }

        if (dto.getLevel() != null && !dto.getLevel().isEmpty()) {
            entity.setLevel(Level.valueOf(dto.getLevel()));
        }

        DistrictEntity district = districtRepository.findById(dto.getDistrictId())
                .orElseThrow(() -> new BusinessException("Không tìm thấy quận"));
        entity.setDistrict(district);

        List<StaffEntity> staffs = staffRepository.findAllById(dto.getStaffIds());
        entity.setStaffs_buildings(staffs);

        entity.getRentAreas().clear();

        if (dto.getRentAreaValues() != null && !dto.getRentAreaValues().isEmpty()) {
            List<RentAreaEntity> newAreas = Arrays.stream(dto.getRentAreaValues().split(","))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .map(v -> new RentAreaEntity(v, entity))
                    .toList();

            entity.getRentAreas().addAll(newAreas);
        }
    }
}
