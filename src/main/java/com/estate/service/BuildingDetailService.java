package com.estate.service;

import com.estate.dto.*;
import java.util.List;

public interface BuildingDetailService {

    List<SupplierDTO>       getSuppliers      (Long buildingId);
    List<PlanningMapDTO>    getPlanningMaps   (Long buildingId);
    List<LegalAuthorityDTO> getLegalAuthorities(Long buildingId);
    List<NearbyAmenityDTO>  getNearbyAmenities (Long buildingId);
}