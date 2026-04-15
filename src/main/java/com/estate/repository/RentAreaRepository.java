package com.estate.repository;

import com.estate.repository.entity.RentAreaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RentAreaRepository extends JpaRepository<RentAreaEntity, Long> {
    @Query("""
            SELECT r.building.id AS buildingId, r.value AS value
            FROM RentAreaEntity r
            """)
    List<Object[]> getAllRentAreasRaw();
}
