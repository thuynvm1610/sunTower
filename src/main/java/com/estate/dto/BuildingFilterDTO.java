package com.estate.dto;

import com.estate.enums.Direction;
import com.estate.enums.Level;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BuildingFilterDTO {
    private String name;
    private Long districtId;
    private String ward;
    private String street;
    private Integer numberOfFloorFrom;
    private Integer numberOfFloorTo;
    private Integer numberOfBasementFrom;
    private Integer numberOfBasementTo;
    private Integer floorAreaFrom;
    private Integer floorAreaTo;
    private Integer rentAreaFrom;
    private Integer rentAreaTo;
    private Direction direction;
    private Level level;
    private BigDecimal rentPriceFrom;
    private BigDecimal rentPriceTo;
    private BigDecimal serviceFeeFrom;
    private BigDecimal serviceFeeTo;
    private BigDecimal carFeeFrom;
    private BigDecimal carFeeTo;
    private BigDecimal motorbikeFeeFrom;
    private BigDecimal motorbikeFeeTo;
    private BigDecimal waterFeeFrom;
    private BigDecimal waterFeeTo;
    private BigDecimal electricityFeeFrom;
    private BigDecimal electricityFeeTo;
    private Long staffId;
}
