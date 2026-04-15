package com.estate.enums;

import lombok.Getter;

@Getter
public enum ChatSenderType {
    CUSTOMER("CUSTOMER"),
    STAFF("STAFF");

    private final String value;

    ChatSenderType(String value) {
        this.value = value;
    }
}
