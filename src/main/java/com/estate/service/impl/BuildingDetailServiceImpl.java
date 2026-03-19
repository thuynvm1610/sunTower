package com.estate.service.impl;

import com.estate.dto.*;
import com.estate.repository.*;
import com.estate.repository.entity.*;
import com.estate.service.BuildingDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BuildingDetailServiceImpl implements BuildingDetailService {

    private final SupplierRepository        supplierRepo;
    private final PlanningMapRepository     planningMapRepo;
    private final LegalAuthorityRepository  legalAuthorityRepo;
    private final NearbyAmenityRepository   nearbyAmenityRepo;

    // ── Label map cho authority_type ─────────────────────────────────────────
    private static final Map<String, String> AUTHORITY_LABELS = Map.of(
            "NOTARY",        "Công chứng",
            "LAND_REGISTRY", "Đăng ký đất đai",
            "LAW_FIRM",      "Văn phòng luật",
            "TAX_OFFICE",    "Cơ quan thuế"
    );

    // ── Label map cho amenity_type ────────────────────────────────────────────
    private static final Map<String, String> AMENITY_LABELS = Map.of(
            "SHOPPING",  "Mua sắm",
            "PARK",      "Công viên",
            "HOSPITAL",  "Bệnh viện",
            "SCHOOL",    "Trường học",
            "RESTAURANT","Nhà hàng",
            "BANK",      "Ngân hàng",
            "GYM",       "Thể dục",
            "TRANSPORT", "Giao thông",
            "OTHER",     "Khác"
    );

    // ─────────────────────────────────────────────────────────────────────────

    @Override
    public List<SupplierDTO> getSuppliers(Long buildingId) {
        return supplierRepo.findByBuildingId(buildingId)
                .stream()
                .map(this::toSupplierDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PlanningMapDTO> getPlanningMaps(Long buildingId) {
        return planningMapRepo.findByBuildingId(buildingId)
                .stream()
                .map(this::toPlanningMapDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<LegalAuthorityDTO> getLegalAuthorities(Long buildingId) {
        return legalAuthorityRepo.findByBuildingId(buildingId)
                .stream()
                .map(this::toLegalAuthorityDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<NearbyAmenityDTO> getNearbyAmenities(Long buildingId) {
        return nearbyAmenityRepo.findByBuildingId(buildingId)
                .stream()
                .map(this::toNearbyAmenityDTO)
                .collect(Collectors.toList());
    }

    // ── Mappers ───────────────────────────────────────────────────────────────

    private SupplierDTO toSupplierDTO(SupplierEntity e) {
        SupplierDTO dto = new SupplierDTO();
        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setServiceType(e.getServiceType());
        dto.setPhone(e.getPhone());
        dto.setEmail(e.getEmail());
        dto.setAddress(e.getAddress());
        dto.setNote(e.getNote());
        dto.setCreatedDate(e.getCreatedDate());
        dto.setModifiedDate(e.getModifiedDate());
        return dto;
    }

    private PlanningMapDTO toPlanningMapDTO(PlanningMapEntity e) {
        PlanningMapDTO dto = new PlanningMapDTO();
        dto.setId(e.getId());
        dto.setMapType(e.getMapType());
        dto.setIssuedBy(e.getIssuedBy());
        dto.setIssuedDate(e.getIssuedDate());
        dto.setExpiredDate(e.getExpiredDate());
        dto.setImageUrl(e.getImageUrl());
        dto.setDocumentUrl(e.getDocumentUrl());
        dto.setNote(e.getNote());
        dto.setCreatedDate(e.getCreatedDate());
        // Đánh dấu hết hạn để Thymeleaf hiển thị cảnh báo
        dto.setExpired(e.getExpiredDate() != null
                && e.getExpiredDate().isBefore(LocalDate.now()));
        return dto;
    }

    private LegalAuthorityDTO toLegalAuthorityDTO(LegalAuthorityEntity e) {
        LegalAuthorityDTO dto = new LegalAuthorityDTO();
        dto.setId(e.getId());
        dto.setAuthorityName(e.getAuthorityName());
        dto.setAuthorityType(e.getAuthorityType());
        dto.setAuthorityTypeLabel(
                AUTHORITY_LABELS.getOrDefault(e.getAuthorityType(), e.getAuthorityType()));
        dto.setAddress(e.getAddress());
        dto.setPhone(e.getPhone());
        dto.setEmail(e.getEmail());
        dto.setNote(e.getNote());
        dto.setCreatedDate(e.getCreatedDate());
        return dto;
    }

    private NearbyAmenityDTO toNearbyAmenityDTO(NearbyAmenityEntity e) {
        NearbyAmenityDTO dto = new NearbyAmenityDTO();
        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setAmenityType(e.getAmenityType());
        dto.setAmenityTypeLabel(
                AMENITY_LABELS.getOrDefault(e.getAmenityType(), e.getAmenityType()));
        dto.setDistanceMeter(e.getDistanceMeter());
        dto.setAddress(e.getAddress());
        dto.setLatitude(e.getLatitude());
        dto.setLongitude(e.getLongitude());
        dto.setCreatedDate(e.getCreatedDate());
        return dto;
    }
}