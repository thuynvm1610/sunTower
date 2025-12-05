package com.estate.dto;

import com.estate.repository.entity.ContractEntity;
import com.estate.repository.entity.CustomerEntity;
import com.estate.repository.entity.InvoiceDetailEntity;
import com.estate.repository.entity.UtilityMeterEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDetailDTO {
    private Long id;
    private Integer month;
    private Integer year;
    private BigDecimal totalAmount;
    private BigDecimal totalServiceFeeAmount;
    private String status; // PENDING, PAID, OVERDUE
    private String dueDate;
    private ContractEntity contract;
    private CustomerEntity customer;
    private List<InvoiceDetailEntity> details;
    private UtilityMeterEntity utilityMeter;
}
