package com.estate.config;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        mapper.addConverter(new AbstractConverter<LocalDateTime, LocalDate>() {
            @Override
            protected LocalDate convert(LocalDateTime source) {
                return source == null ? null : source.toLocalDate();
            }
        });

        return mapper;
    }
}