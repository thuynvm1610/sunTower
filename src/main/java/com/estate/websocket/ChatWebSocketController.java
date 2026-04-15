package com.estate.websocket;

import com.estate.dto.chat.ChatTypingRequestDTO;
import com.estate.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/typing")
    public void typing(ChatTypingRequestDTO request,
                       Principal principal) {
        if (request == null || request.getRoomId() == null) {
            return;
        }
        boolean typing = request.getTyping() == null || request.getTyping();
        String senderType = null;
        Long senderId = null;
        if (principal instanceof Authentication authentication
                && authentication.getPrincipal() instanceof CustomUserDetails user) {
            senderType = user.getUserType();
            senderId = user.getUserId();
        }
        messagingTemplate.convertAndSend("/topic/chat/typing/" + request.getRoomId(), new TypingPayload(
                request.getRoomId(),
                senderType,
                senderId,
                typing
        ));
    }

    public record TypingPayload(Long roomId, String senderType, Long senderId, boolean typing) {}
}
