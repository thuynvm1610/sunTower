package com.estate.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class CustomerValueDTO {
    private Long customerId;
    private String fullName;
    private BigDecimal rentValue;
    private BigDecimal saleValue;
    private BigDecimal total;

    public CustomerValueDTO(Long customerId, String fullName, BigDecimal rentValue, BigDecimal saleValue) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.rentValue = rentValue != null ? rentValue : BigDecimal.ZERO;
        this.saleValue = saleValue != null ? saleValue : BigDecimal.ZERO;
        this.total = this.rentValue.add(this.saleValue);
    }
}