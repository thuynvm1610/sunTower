package com.estate.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomCreateRequestDTO {
    private Long buildingId;
    private Long staffId;
}
