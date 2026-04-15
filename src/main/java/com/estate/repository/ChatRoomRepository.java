package com.estate.repository;

import com.estate.repository.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {
    Optional<ChatRoomEntity> findByBuildingIdAndCustomerIdAndStaffId(Long buildingId, Long customerId, Long staffId);

    List<ChatRoomEntity> findByStaffIdAndLastMessageAtIsNotNullOrderByLastMessageAtDesc(Long staffId);

    List<ChatRoomEntity> findByCustomerIdAndLastMessageAtIsNotNullOrderByLastMessageAtDesc(Long customerId);
}
