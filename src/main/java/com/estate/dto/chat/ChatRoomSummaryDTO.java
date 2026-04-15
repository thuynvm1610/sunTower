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
public class ChatRoomSummaryDTO {
    private Long roomId;
    private Long buildingId;
    private String buildingName;
    private Long customerId;
    private String customerName;
    private String customerPhone;
    private Long staffId;
    private String staffName;
    private String staffPhone;
    private String status;
    private String lastMessage;
    private LocalDateTime lastMessageAt;
    private long unreadCount;
}
