package com.estate.repository;

import com.estate.repository.entity.StaffEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StaffRepository extends JpaRepository<StaffEntity, Long> {
    Long countByRole(String role);

    List<StaffEntity> findByRole(String role);

    @Query("SELECT s.fullName FROM StaffEntity s " +
            "JOIN s.buildings b " +
            "WHERE b.id = :buildingId")
    List<String> findStaffNamesByBuildingId(@Param("buildingId") Long buildingId);

    Page<StaffEntity> findByRole(Pageable pageable, String role);

    Page<StaffEntity> findByFullNameContainingIgnoreCase(String fullName, Pageable pageable);

    Page<StaffEntity> findByFullNameContainingIgnoreCaseAndRole(
            String fullName,
            String role,
            Pageable pageable
    );

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    @Query("select count(b) from StaffEntity s join s.buildings b where s.id = :staffId")
    Long countBuildingsByStaffId(@Param("staffId") Long staffId);

    @Query("select count(c) from StaffEntity s join s.customers c where s.id = :customerId")
    Long countCustomersByStaffId(@Param("customerId") Long customerId);

    @Query("""
                select count(s) > 0
                from StaffEntity s
                join s.buildings b
                where s.id = :staffId and b.id = :buildingId
            """)
    boolean existsByStaffIdAndBuildingId(@Param("staffId") Long staffId,
                                         @Param("buildingId") Long buildingId);


    @Query("""
                select count(s) > 0
                from StaffEntity s
                join s.customers c
                where s.id = :staffId and c.id = :customerId
            """)
    boolean existsByStaffIdAndCustomerId(@Param("staffId") Long staffId,
                                         @Param("customerId") Long customerId);

    StaffEntity findByUsername(String username);
}
