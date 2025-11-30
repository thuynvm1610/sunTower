package com.estate.dto;

import com.estate.repository.entity.CustomerEntity;
import com.estate.repository.entity.StaffEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractDetailDTO {
    private Long id;
    private BigDecimal rentPrice;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private CustomerEntity customer;
    private BuildingListDTO building;
    private StaffEntity staff;
}
