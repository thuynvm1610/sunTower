package com.estate.repository.custom;

import com.estate.dto.BuildingFilterDTO;
import com.estate.repository.entity.BuildingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BuildingRepositoryCustom {
    Page<BuildingEntity> searchBuildings(BuildingFilterDTO filter, Pageable pageable);
}
