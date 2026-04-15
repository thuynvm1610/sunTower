package com.estate.service.impl;

import com.estate.dto.chat.*;
import com.estate.enums.ChatRoomStatus;
import com.estate.enums.ChatSenderType;
import com.estate.exception.BusinessException;
import com.estate.repository.*;
import com.estate.repository.entity.*;
import com.estate.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final BuildingRepository buildingRepository;
    private final StaffRepository staffRepository;
    private final CustomerRepository customerRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public List<ChatStaffOptionDTO> getStaffOptionsByBuilding(Long buildingId, Long customerId) {
        if (customerId == null) {
            throw new BusinessException("Không thể xác định khách hàng");
        }
        if (!buildingRepository.existsById(buildingId)) {
            throw new BusinessException("Không tìm thấy tòa nhà");
        }
        return staffRepository.findChatStaffOptionsByBuildingId(buildingId);
    }

    @Override
    public ChatRoomOpenResponseDTO openRoom(Long buildingId, Long staffId, Long customerId) {
        BuildingEntity building = buildingRepository.findById(buildingId)
                .orElseThrow(() -> new BusinessException("Không tìm thấy tòa nhà"));
        StaffEntity staff = staffRepository.findById(staffId)
                .orElseThrow(() -> new BusinessException("Không tìm thấy nhân viên"));
        CustomerEntity customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new BusinessException("Không tìm thấy khách hàng"));

        if (!buildingRepository.isStaffManagesBuilding(staffId, buildingId)) {
            throw new BusinessException("Nhân viên này không quản lý tòa nhà đã chọn");
        }

        ChatRoomEntity room = chatRoomRepository.findByBuildingIdAndCustomerIdAndStaffId(buildingId, customerId, staffId)
                .orElseGet(() -> {
                    ChatRoomEntity newRoom = new ChatRoomEntity();
                    newRoom.setBuilding(building);
                    newRoom.setCustomer(customer);
                    newRoom.setStaff(staff);
                    newRoom.setStatus(ChatRoomStatus.OPEN);
                    return newRoom;
                });

        room.setStatus(ChatRoomStatus.OPEN);
        room.setClosedAt(null);
        room = chatRoomRepository.save(room);

        return new ChatRoomOpenResponseDTO(toSummary(room), getRoomMessages(room.getId(), customerId, "CUSTOMER"));
    }

    @Override
    public List<ChatRoomSummaryDTO> getStaffInbox(Long staffId) {
        return chatRoomRepository.findByStaffIdAndLastMessageAtIsNotNullOrderByLastMessageAtDesc(staffId)
                .stream()
                .map(room -> {
                    ChatRoomSummaryDTO dto = toSummary(room);
                    dto.setUnreadCount(chatMessageRepository.countUnreadByRoomIdAndSenderTypeNot(room.getId(), ChatSenderType.STAFF));
                    dto.setLastMessage(chatMessageRepository.findTopByRoomIdOrderByCreatedAtDesc(room.getId())
                            .map(ChatMessageEntity::getContent)
                            .orElse(null));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ChatMessageDTO> getRoomMessages(Long roomId, Long userId, String userType) {
        ChatRoomEntity room = findAndAuthorize(roomId, userId, userType);
        markAsRead(roomId, userId, userType);
        return chatMessageRepository.findByRoomIdOrderByCreatedAtAsc(roomId)
                .stream()
                .map(message -> toMessageDto(message, userId, userType, room))
                .collect(Collectors.toList());
    }

    @Override
    public ChatMessageDTO sendMessage(Long roomId, Long senderId, String senderType, String content) {
        if (content == null || content.isBlank()) {
            throw new BusinessException("Nội dung tin nhắn không được để trống");
        }

        ChatRoomEntity room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new BusinessException("Không tìm thấy cuộc hội thoại"));

        authorize(room, senderId, senderType);

        ChatSenderType senderEnum = ChatSenderType.valueOf(senderType.toUpperCase());
        ChatMessageEntity message = new ChatMessageEntity();
        message.setRoom(room);
        message.setSenderType(senderEnum);
        message.setSenderId(senderId);
        message.setContent(content.trim());
        message = chatMessageRepository.save(message);

        room.setStatus(ChatRoomStatus.OPEN);
        room.setLastMessageAt(message.getCreatedAt());
        chatRoomRepository.save(room);

        ChatMessageDTO dto = toMessageDto(message, senderId, senderType, room);
        messagingTemplate.convertAndSend("/topic/chat/room/" + roomId, dto);

        ChatRoomSummaryDTO summary = toSummary(room);
        summary.setLastMessage(message.getContent());
        summary.setUnreadCount(chatMessageRepository.countUnreadByRoomIdAndSenderTypeNot(roomId, senderEnum));

        if (senderEnum == ChatSenderType.CUSTOMER) {
            sendNotificationToStaff(room, summary, message.getContent());
        } else {
            sendNotificationToCustomer(room, summary, message.getContent());
        }

        return dto;
    }

    @Override
    public ChatRoomSummaryDTO closeRoom(Long roomId, Long userId, String userType) {
        ChatRoomEntity room = findAndAuthorize(roomId, userId, userType);
        room.setStatus(ChatRoomStatus.CLOSED);
        room.setClosedAt(LocalDateTime.now());
        chatRoomRepository.save(room);
        return toSummary(room);
    }

    @Override
    public void markAsRead(Long roomId, Long userId, String userType) {
        ChatRoomEntity room = findAndAuthorize(roomId, userId, userType);
        ChatSenderType senderType = "STAFF".equalsIgnoreCase(userType)
                ? ChatSenderType.STAFF
                : ChatSenderType.CUSTOMER;
        chatMessageRepository.markReadByRoomId(room.getId(), senderType, LocalDateTime.now());
    }

    private ChatRoomEntity findAndAuthorize(Long roomId, Long userId, String userType) {
        ChatRoomEntity room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new BusinessException("Không tìm thấy cuộc hội thoại"));
        authorize(room, userId, userType);
        return room;
    }

    private void authorize(ChatRoomEntity room, Long userId, String userType) {
        if ("STAFF".equalsIgnoreCase(userType)) {
            if (!room.getStaff().getId().equals(userId)) {
                throw new BusinessException("Bạn không có quyền truy cập hội thoại này");
            }
            return;
        }
        if ("CUSTOMER".equalsIgnoreCase(userType)) {
            if (!room.getCustomer().getId().equals(userId)) {
                throw new BusinessException("Bạn không có quyền truy cập hội thoại này");
            }
            return;
        }
        throw new BusinessException("Loại tài khoản không hợp lệ");
    }

    private ChatRoomSummaryDTO toSummary(ChatRoomEntity room) {
        return new ChatRoomSummaryDTO(
                room.getId(),
                room.getBuilding().getId(),
                room.getBuilding().getName(),
                room.getCustomer().getId(),
                room.getCustomer().getFullName(),
                room.getCustomer().getPhone(),
                room.getStaff().getId(),
                room.getStaff().getFullName(),
                room.getStaff().getPhone(),
                room.getStatus() != null ? room.getStatus().name() : ChatRoomStatus.OPEN.name(),
                null,
                room.getLastMessageAt(),
                0L
        );
    }

    private ChatMessageDTO toMessageDto(ChatMessageEntity message, Long userId, String userType, ChatRoomEntity room) {
        boolean mine = "STAFF".equalsIgnoreCase(userType)
                ? room.getStaff().getId().equals(userId)
                : room.getCustomer().getId().equals(userId);
        String senderName = "STAFF".equals(message.getSenderType().name())
                ? room.getStaff().getFullName()
                : room.getCustomer().getFullName();
        return new ChatMessageDTO(
                message.getId(),
                room.getId(),
                message.getSenderType().name(),
                message.getSenderId(),
                senderName,
                message.getContent(),
                message.getCreatedAt(),
                mine
        );
    }

    private void sendNotificationToStaff(ChatRoomEntity room, ChatRoomSummaryDTO summary, String content) {
        messagingTemplate.convertAndSendToUser(
                resolveDestinationName(room.getStaff().getUsername(), "STAFF", room.getStaff().getId()),
                "/queue/chat/notifications",
                new ChatNotificationDTO("MESSAGE", summary, content)
        );
    }

    private void sendNotificationToCustomer(ChatRoomEntity room, ChatRoomSummaryDTO summary, String content) {
        messagingTemplate.convertAndSendToUser(
                resolveDestinationName(room.getCustomer().getUsername(), "CUSTOMER", room.getCustomer().getId()),
                "/queue/chat/notifications",
                new ChatNotificationDTO("MESSAGE", summary, content)
        );
    }

    private String resolveDestinationName(String username, String userType, Long userId) {
        if (username != null && !username.isBlank()) {
            return username;
        }
        return userType + ":" + userId;
    }
}
