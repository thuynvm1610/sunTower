package com.estate.config;

import com.estate.dto.BuildingFormDTO;
import com.estate.dto.ContractFormDTO;
import com.estate.repository.entity.BuildingEntity;
import com.estate.repository.entity.ContractEntity;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.getConfiguration()
                .setSkipNullEnabled(true)
                .setFieldMatchingEnabled(true)
                .setMatchingStrategy(MatchingStrategies.STRICT);

        // --- Converter LocalDateTime -> LocalDate ---
        mapper.addConverter(new AbstractConverter<LocalDateTime, LocalDate>() {
            @Override
            protected LocalDate convert(LocalDateTime source) {
                return source == null ? null : source.toLocalDate();
            }
        });

        // --- Converter LocalDate -> LocalDateTime ---
        mapper.addConverter(new AbstractConverter<LocalDate, LocalDateTime>() {
            @Override
            protected LocalDateTime convert(LocalDate source) {
                return source == null ? null : source.atStartOfDay();
            }
        });

        // Mapping Building
        mapper.typeMap(BuildingFormDTO.class, BuildingEntity.class)
                .addMappings(m -> {
                    m.skip(BuildingEntity::setDistrict);
                    m.skip(BuildingEntity::setStaffs_buildings);
                    m.skip(BuildingEntity::setRentAreas);
                });

        // Mapping Contract
        mapper.typeMap(ContractFormDTO.class, ContractEntity.class)
                .addMappings(m -> {
                    m.skip(ContractEntity::setBuilding);
                    m.skip(ContractEntity::setCustomer);
                    m.skip(ContractEntity::setStaff);
                });

        return mapper;
    }
}

