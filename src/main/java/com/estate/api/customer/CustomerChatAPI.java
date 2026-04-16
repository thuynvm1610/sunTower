package com.estate.api.customer;

import com.estate.dto.chat.*;
import com.estate.security.CustomUserDetails;
import com.estate.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer/chat")
@RequiredArgsConstructor
public class CustomerChatAPI {
    private final ChatService chatService;

    @GetMapping("/buildings/{buildingId}/staffs")
    public ResponseEntity<List<ChatStaffOptionDTO>> getStaffOptions(
            @PathVariable Long buildingId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ResponseEntity.ok(chatService.getStaffOptionsByBuilding(buildingId, user.getUserId()));
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<ChatRoomSummaryDTO>> getInbox(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ResponseEntity.ok(chatService.getCustomerInbox(user.getUserId()));
    }

    @PostMapping("/rooms/open")
    public ResponseEntity<ChatRoomOpenResponseDTO> openRoom(
            @RequestBody ChatRoomCreateRequestDTO request,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ResponseEntity.ok(chatService.openRoom(request.getBuildingId(), request.getStaffId(), user.getUserId()));
    }

    @PostMapping("/rooms/{roomId}/resume")
    public ResponseEntity<ChatRoomOpenResponseDTO> resumeRoom(
            @PathVariable Long roomId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ResponseEntity.ok(chatService.resumeRoom(roomId, user.getUserId()));
    }

    @GetMapping("/rooms/{roomId}/messages")
    public ResponseEntity<List<ChatMessageDTO>> getMessages(
            @PathVariable Long roomId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ResponseEntity.ok(chatService.getRoomMessages(roomId, user.getUserId(), "CUSTOMER"));
    }

    @GetMapping("/rooms/{roomId}")
    public ResponseEntity<ChatRoomSummaryDTO> getRoomSummary(
            @PathVariable Long roomId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ResponseEntity.ok(chatService.getRoomSummary(roomId, user.getUserId(), "CUSTOMER"));
    }

    @PostMapping("/rooms/{roomId}/messages")
    public ResponseEntity<ChatMessageDTO> sendMessage(
            @PathVariable Long roomId,
            @RequestBody ChatSendMessageRequestDTO request,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ResponseEntity.ok(chatService.sendMessage(roomId, user.getUserId(), "CUSTOMER", request.getContent()));
    }

    @PostMapping("/rooms/{roomId}/close")
    public ResponseEntity<ChatRoomSummaryDTO> closeRoom(
            @PathVariable Long roomId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ResponseEntity.ok(chatService.closeRoom(roomId, user.getUserId(), "CUSTOMER"));
    }

    @PostMapping("/rooms/{roomId}/read")
    public ResponseEntity<Void> markRead(
            @PathVariable Long roomId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        chatService.markAsRead(roomId, user.getUserId(), "CUSTOMER");
        return ResponseEntity.ok().build();
    }
}
