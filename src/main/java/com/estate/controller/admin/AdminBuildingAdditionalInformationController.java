package com.estate.controller.admin;

import com.estate.dto.*;
import com.estate.security.CustomUserDetails;
import com.estate.service.BuildingDetailService;
import com.estate.service.BuildingService;
import com.estate.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/admin/building-additional-information")
@RequiredArgsConstructor
public class AdminBuildingAdditionalInformationController {
    private final BuildingDetailService buildingDetailService;
    private final BuildingService buildingService;
    private final StaffService staffService;

    // ===================== PAGE =====================
    @GetMapping("/{buildingId}")
    public String detailPage(
            @PathVariable Long buildingId,
            @AuthenticationPrincipal CustomUserDetails user,
            Model model) {
        BuildingFormDTO building = buildingService.findById(buildingId);
        model.addAttribute("building", building);
        model.addAttribute("buildingLat", building.getLatitude());
        model.addAttribute("buildingLng", building.getLongitude());
        model.addAttribute("legalAuthorities", buildingDetailService.getLegalAuthoritiesByBuilding(buildingId));
        model.addAttribute("nearbyAmenities", buildingDetailService.getNearbyAmenitiesByBuilding(buildingId));
        List<PlanningMapDTO> list = buildingDetailService.getPlanningMapsByBuilding(buildingId);
        model.addAttribute("planningMaps", list);
        model.addAttribute("suppliers", buildingDetailService.getSuppliersByBuilding(buildingId));
        model.addAttribute("buildingId", buildingId);
        model.addAttribute("page", "building");
        model.addAttribute("staffName",   staffService.getStaffName(user.getCustomerId()));
        model.addAttribute("staffAvatar", staffService.getStaffAvatar(user.getCustomerId()));
        return "admin/building-additional-information";
    }

    // ===================== LEGAL AUTHORITY =====================
    @GetMapping("/legal-authority/{buildingId}/list")
    @ResponseBody
    public List<LegalAuthorityDTO> listLegalAuthorities(@PathVariable Long buildingId) {
        return buildingDetailService.getLegalAuthoritiesByBuilding(buildingId);
    }

    // ===================== NEARBY AMENITY =====================
    @GetMapping("/nearby-amenity/{buildingId}/list")
    @ResponseBody
    public List<NearbyAmenityDTO> listNearbyAmenities(@PathVariable Long buildingId) {
        return buildingDetailService.getNearbyAmenitiesByBuilding(buildingId);
    }

    // ===================== PLANNING MAP REST =====================
    @GetMapping("/planning-map/{buildingId}/list")
    @ResponseBody
    public List<PlanningMapDTO> listPlanningMaps(@PathVariable Long buildingId) {
        return buildingDetailService.getPlanningMapsByBuilding(buildingId);
    }

    // ===================== SUPPLIER =====================
    @GetMapping("/supplier/{buildingId}/list")
    @ResponseBody
    public List<SupplierDTO> listSuppliers(@PathVariable Long buildingId) {
        return buildingDetailService.getSuppliersByBuilding(buildingId);
    }
}