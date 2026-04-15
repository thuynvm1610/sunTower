package com.estate.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatTypingRequestDTO {
    private Long roomId;
    private Boolean typing;
}
