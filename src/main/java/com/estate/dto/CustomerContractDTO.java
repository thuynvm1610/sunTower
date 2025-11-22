package com.estate.dto;

import com.estate.repository.entity.BuildingEntity;
import com.estate.repository.entity.CustomerEntity;
import com.estate.repository.entity.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerContractDTO {
    private Long id;
    private String building;
    private String staff;
    private BigDecimal rentPrice;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
}
