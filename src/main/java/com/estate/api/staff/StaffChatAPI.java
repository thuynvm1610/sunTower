package com.estate.api.staff;

import com.estate.dto.chat.ChatMessageDTO;
import com.estate.dto.chat.ChatRoomSummaryDTO;
import com.estate.dto.chat.ChatSendMessageRequestDTO;
import com.estate.security.CustomUserDetails;
import com.estate.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staff/chat")
@RequiredArgsConstructor
public class StaffChatAPI {
    private final ChatService chatService;

    @GetMapping("/rooms")
    public ResponseEntity<List<ChatRoomSummaryDTO>> getInbox(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ResponseEntity.ok(chatService.getStaffInbox(user.getUserId()));
    }

    @GetMapping("/rooms/{roomId}/messages")
    public ResponseEntity<List<ChatMessageDTO>> getMessages(
            @PathVariable Long roomId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ResponseEntity.ok(chatService.getRoomMessages(roomId, user.getUserId(), "STAFF"));
    }

    @PostMapping("/rooms/{roomId}/messages")
    public ResponseEntity<ChatMessageDTO> sendMessage(
            @PathVariable Long roomId,
            @RequestBody ChatSendMessageRequestDTO request,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ResponseEntity.ok(chatService.sendMessage(roomId, user.getUserId(), "STAFF", request.getContent()));
    }

    @PostMapping("/rooms/{roomId}/close")
    public ResponseEntity<ChatRoomSummaryDTO> closeRoom(
            @PathVariable Long roomId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return ResponseEntity.ok(chatService.closeRoom(roomId, user.getUserId(), "STAFF"));
    }

    @PostMapping("/rooms/{roomId}/read")
    public ResponseEntity<Void> markRead(
            @PathVariable Long roomId,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        chatService.markAsRead(roomId, user.getUserId(), "STAFF");
        return ResponseEntity.ok().build();
    }
}
