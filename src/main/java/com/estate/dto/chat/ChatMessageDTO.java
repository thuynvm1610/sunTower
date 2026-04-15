package com.estate.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDTO {
    private Long id;
    private Long roomId;
    private String senderType;
    private Long senderId;
    private String senderName;
    private String content;
    private LocalDateTime createdAt;
    private boolean mine;
}
