package com.estate.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PropertyType {
    OFFICE("Văn phòng"),
    SHOPHOUSE("Nhà phố thương mại"),
    APARTMENT("Căn hộ"),
    WAREHOUSE("Kho xưởng");

    private final String label;
}
