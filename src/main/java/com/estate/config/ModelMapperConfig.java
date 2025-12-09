package com.estate.config;

import com.estate.dto.BuildingFormDTO;
import com.estate.dto.ContractFormDTO;
import com.estate.dto.InvoiceDetailDTO;
import com.estate.repository.entity.BuildingEntity;
import com.estate.repository.entity.ContractEntity;
import com.estate.repository.entity.InvoiceEntity;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
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

        // Converter LocalDateTime -> LocalDate
        mapper.addConverter(new AbstractConverter<LocalDateTime, LocalDate>() {
            @Override
            protected LocalDate convert(LocalDateTime source) {
                return source == null ? null : source.toLocalDate();
            }
        });

        // Converter LocalDate -> LocalDateTime
        mapper.addConverter(new AbstractConverter<LocalDate, LocalDateTime>() {
            @Override
            protected LocalDateTime convert(LocalDate source) {
                return source == null ? null : source.atStartOfDay();
            }
        });


        /** -------------------- BUILDING -------------------- **/
        TypeMap<BuildingFormDTO, BuildingEntity> buildingMap =
                mapper.createTypeMap(BuildingFormDTO.class, BuildingEntity.class); // tạo trống

        buildingMap.addMappings(m -> {
            m.skip(BuildingEntity::setDistrict);
            m.skip(BuildingEntity::setStaffs_buildings);
            m.skip(BuildingEntity::setRentAreas);
        });

        buildingMap.implicitMappings(); // chạy implicit cuối cùng


        /** -------------------- CONTRACT -------------------- **/
        TypeMap<ContractFormDTO, ContractEntity> contractMap =
                mapper.createTypeMap(ContractFormDTO.class, ContractEntity.class);

        contractMap.addMappings(m -> {
            m.skip(ContractEntity::setBuilding);
            m.skip(ContractEntity::setCustomer);
            m.skip(ContractEntity::setStaff);
        });

        contractMap.implicitMappings();

        return mapper;
    }
}

