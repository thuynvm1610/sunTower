package com.estate.enums;

import lombok.Getter;

@Getter
public enum ChatRoomStatus {
    OPEN("OPEN"),
    CLOSED("CLOSED");

    private final String value;

    ChatRoomStatus(String value) {
        this.value = value;
    }
}
