package com.estate.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransactionType {
    FOR_RENT("Cho thuê"),
    FOR_SALE("Mua bán");

    private final String label;
}
