package com.estate.service;

import com.estate.dto.chat.ChatMessageDTO;
import com.estate.dto.chat.ChatRoomOpenResponseDTO;
import com.estate.dto.chat.ChatRoomSummaryDTO;
import com.estate.dto.chat.ChatStaffOptionDTO;

import java.util.List;

public interface ChatService {
    List<ChatStaffOptionDTO> getStaffOptionsByBuilding(Long buildingId, Long customerId);

    ChatRoomOpenResponseDTO openRoom(Long buildingId, Long staffId, Long customerId);

    ChatRoomOpenResponseDTO resumeRoom(Long roomId, Long customerId);

    List<ChatRoomSummaryDTO> getStaffInbox(Long staffId);

    List<ChatRoomSummaryDTO> getCustomerInbox(Long customerId);

    List<ChatMessageDTO> getRoomMessages(Long roomId, Long userId, String userType);

    ChatMessageDTO sendMessage(Long roomId, Long senderId, String senderType, String content);

    ChatRoomSummaryDTO closeRoom(Long roomId, Long userId, String userType);

    void markAsRead(Long roomId, Long userId, String userType);

    ChatRoomSummaryDTO getRoomSummary(Long roomId, Long userId, String userType);
}
