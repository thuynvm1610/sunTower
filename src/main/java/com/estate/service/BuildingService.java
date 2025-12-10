package com.estate.service;

import com.estate.dto.BuildingDetailDTO;
import com.estate.dto.BuildingFilterDTO;
import com.estate.dto.BuildingFormDTO;
import com.estate.dto.BuildingListDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface BuildingService {
    long countAll();
    List<BuildingListDTO> findRecent();
    Map<String, Long> getBuildingCountByDistrict();
    Page<BuildingListDTO> getBuildings(int page, int size);
    Page<BuildingListDTO> search(BuildingFilterDTO filter, int page, int size);
    List<String> getWardName();
    List<String> getStreetName();
    List<String> getDirectionName();
    List<String> getLevelName();
    void save(BuildingFormDTO dto);
    BuildingFormDTO findById(Long id);
    void delete(Long id);
    BuildingDetailDTO viewById(Long id);
    Map<String, Long> getBuildingsName();
    List<BuildingDetailDTO> searchByCustomer(BuildingFilterDTO filter);
}
