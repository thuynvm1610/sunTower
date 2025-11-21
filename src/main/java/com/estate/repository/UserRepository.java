package com.estate.repository;

import com.estate.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Long countByRole(String role);

    List<UserEntity> findByRole(String role);

    @Query("SELECT u.fullName FROM UserEntity u " +
            "JOIN u.buildings b " +
            "WHERE b.id = :buildingId")
    List<String> findStaffNamesByBuildingId(@Param("buildingId") Long buildingId);
}
