package com.estate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BuildingDetailDTO {
    private Long id;
    private String name;
    private String district;
    private String ward;
    private String street;
    private Integer numberOfFloor;
    private Integer numberOfBasement;
    private Integer floorArea;
    private String direction;
    private String level;
    private BigDecimal rentPrice;
    private BigDecimal serviceFee;
    private BigDecimal carFee;
    private BigDecimal motorbikeFee;
    private BigDecimal waterFee;
    private BigDecimal electricityFee;
    private BigDecimal deposit;
    private String linkOfBuilding;
    private String image;
    private String rentAreaValues;
    private List<String> staffs = new ArrayList<>();
}
