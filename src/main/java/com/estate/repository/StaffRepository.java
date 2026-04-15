package com.estate.repository;

import com.estate.repository.entity.StaffEntity;
import com.estate.dto.chat.ChatStaffOptionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StaffRepository extends JpaRepository<StaffEntity, Long> {
    Long countByRole(String role);

    List<StaffEntity> findByRole(String role);

    @Query("""
            SELECT s.fullName FROM StaffEntity s
            JOIN s.buildings b
            WHERE b.id = :buildingId
            """)
    List<String> findStaffNamesByBuildingId(@Param("buildingId") Long buildingId);

    Page<StaffEntity> findByRole(Pageable pageable, String role);

    @Query("""
            SELECT s FROM StaffEntity s
            WHERE (:fullName = '' OR s.fullName LIKE CONCAT('%', :fullName, '%'))
                AND (:role = '' OR s.role = :role)
            """)
    Page<StaffEntity> search(
            @Param("fullName") String fullName,
            @Param("role") String role,
            Pageable pageable);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByPhone(String phone);

    boolean existsByPhoneAndIdNot(String phone, Long id);

    boolean existsByUsernameAndIdNot(String username, Long id);

    @Query("""
            SELECT count(b)
            FROM StaffEntity s
            JOIN s.buildings b
            WHERE s.id = :staffId
            """)
    Long countBuildingsByStaffId(@Param("staffId") Long staffId);

    @Query("""
            SELECT count(c)
            FROM StaffEntity s
            JOIN s.customers c
            WHERE s.id = :customerId
            """)
    Long countCustomersByStaffId(@Param("customerId") Long customerId);

    @Query("""
            SELECT count(s) = 0
            FROM StaffEntity s
            JOIN s.buildings b
            WHERE s.id = :staffId and b.id = :buildingId
            """)
    boolean notExistsByStaffIdAndBuildingId(
            @Param("staffId") Long staffId,
            @Param("buildingId") Long buildingId);


    @Query("""
            SELECT count(s) = 0
            FROM StaffEntity s
            JOIN s.customers c
            WHERE s.id = :staffId and c.id = :customerId
            """)
    boolean notExistsByStaffIdAndCustomerId(
            @Param("staffId") Long staffId,
            @Param("customerId") Long customerId);

    Optional<StaffEntity> findByEmail(String email);

    @Modifying
    @Query("""
            UPDATE StaffEntity s
            SET s.username = :username
            WHERE s.id = :staffId
            """)
    void usernameUpdate(
            @Param("username") String username,
            @Param("staffId") Long staffId);

    @Modifying
    @Query("""
            UPDATE StaffEntity s
            SET s.email = :email
            WHERE s.id = :staffId
            """)
    void emailUpdate(
            @Param("email") String email,
            @Param("staffId") Long staffId);

    @Modifying
    @Query("""
            UPDATE StaffEntity s
            SET s.phone = :phone
            WHERE s.id = :staffId
            """)
    void phoneNumberUpdate(
            @Param("phone") String phone,
            @Param("staffId") Long staffId);

    @Modifying
    @Query("""
            UPDATE StaffEntity s
            SET s.password = :password
            WHERE s.id = :staffId
            """)
    void passwordUpdate(
            @Param("password") String password,
            @Param("staffId") Long staffId);

    Optional<StaffEntity> findByUsername(String username);

    // Lấy danh sách buildingId đang được phân công cho staff
    @Query("""
            SELECT b.id
            FROM StaffEntity s
            JOIN s.buildings b
            WHERE s.id = :staffId
            """)
    List<Long> findAssignedBuildingIds(@Param("staffId") Long staffId);

    // Lấy danh sách customerId đang được phân công cho staff
    @Query("""
            SELECT c.id
            FROM StaffEntity s
            JOIN s.customers c
            WHERE s.id = :staffId
            """)
    List<Long> findAssignedCustomerIds(@Param("staffId") Long staffId);

    @Query("""
            SELECT new com.estate.dto.chat.ChatStaffOptionDTO(s.id, s.fullName, s.phone, s.image)
            FROM StaffEntity s
            JOIN s.buildings b
            WHERE b.id = :buildingId
              AND s.role = 'STAFF'
            ORDER BY s.fullName ASC
            """)
    List<ChatStaffOptionDTO> findChatStaffOptionsByBuildingId(@Param("buildingId") Long buildingId);
}
