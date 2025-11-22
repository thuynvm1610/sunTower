package com.estate.repository;

import com.estate.repository.custom.BuildingRepositoryCustom;
import com.estate.repository.entity.BuildingEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BuildingRepository extends JpaRepository<BuildingEntity, Long>, BuildingRepositoryCustom {
    @Query("SELECT d.name, COUNT(b.id) " +
            "FROM BuildingEntity b JOIN b.district d " +
            "GROUP BY d.name " +
            "ORDER BY COUNT(b.id) DESC")
    List<Object[]> countBuildingsByDistrict();

    @Query("SELECT b FROM BuildingEntity b ORDER BY b.createdDate DESC")
    List<BuildingEntity> findRecentBuildings(Pageable pageable);

    @Query("SELECT b.ward " +
            "FROM BuildingEntity b " +
            "GROUP BY b.ward")
    List<String> getWardName();

    @Query("SELECT b.street " +
            "FROM BuildingEntity b " +
            "GROUP BY b.street")
    List<String> getStreetName();

    @Query("SELECT b.direction " +
            "FROM BuildingEntity b " +
            "GROUP BY b.direction")
    List<String> getDirectionName();

    @Query("SELECT b.level " +
            "FROM BuildingEntity b " +
            "GROUP BY b.level")
    List<String> getLevelName();
}
