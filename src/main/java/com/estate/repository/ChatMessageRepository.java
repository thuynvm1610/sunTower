package com.estate.repository;

import com.estate.enums.ChatSenderType;
import com.estate.repository.entity.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {
    List<ChatMessageEntity> findByRoomIdOrderByCreatedAtAsc(Long roomId);

    Optional<ChatMessageEntity> findTopByRoomIdOrderByCreatedAtDesc(Long roomId);

    @Query("""
            SELECT COUNT(m)
            FROM ChatMessageEntity m
            WHERE m.room.id = :roomId
              AND m.readAt IS NULL
              AND m.senderType <> :senderType
            """)
    long countUnreadByRoomIdAndSenderTypeNot(
            @Param("roomId") Long roomId,
            @Param("senderType") ChatSenderType senderType
    );

    @Modifying
    @Query("""
            UPDATE ChatMessageEntity m
            SET m.readAt = :readAt
            WHERE m.room.id = :roomId
              AND m.readAt IS NULL
              AND m.senderType <> :senderType
            """)
    void markReadByRoomId(
            @Param("roomId") Long roomId,
            @Param("senderType") ChatSenderType senderType,
            @Param("readAt") LocalDateTime readAt
    );
}
