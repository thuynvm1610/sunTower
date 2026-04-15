package com.estate.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomOpenResponseDTO {
    private ChatRoomSummaryDTO room;
    private List<ChatMessageDTO> messages;
}
