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

        // --- 1. Cấu hình cơ bản ---
        mapper.getConfiguration()
                .setSkipNullEnabled(true)              // Không overwrite field bằng null
                .setFieldMatchingEnabled(true)         // So khớp theo field
                .setMatchingStrategy(MatchingStrategies.STRICT); // STRICT để tránh map nhầm

        // --- 2. Converter LocalDateTime -> LocalDate ---
        mapper.addConverter(new AbstractConverter<LocalDateTime, LocalDate>() {
            @Override
            protected LocalDate convert(LocalDateTime source) {
                return source == null ? null : source.toLocalDate();
            }
        });

        // --- 3. Ignore các quan hệ phức tạp (ManyToOne, OneToMany, ManyToMany) ---

        // BuildingFormDTO -> BuildingEntity
        mapper.typeMap(BuildingFormDTO.class, BuildingEntity.class)
                .addMappings(m -> {
                    m.skip(BuildingEntity::setDistrict);          // ManyToOne
                    m.skip(BuildingEntity::setStaffs_buildings);   // ManyToMany
                    m.skip(BuildingEntity::setRentAreas);          // OneToMany
                });

        // ContractFormDTO -> ContractEntity
        mapper.typeMap(ContractFormDTO.class, ContractEntity.class)
                .addMappings(m -> {
                    m.skip(ContractEntity::setBuilding);
                    m.skip(ContractEntity::setCustomer);
                    m.skip(ContractEntity::setStaff);
                    m.skip(ContractEntity::setStartDate);
                    m.skip(ContractEntity::setEndDate);
                });

        return mapper;
    }
}
