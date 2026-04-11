package com.estate.api.staff;

import com.estate.dto.LegalAuthorityDTO;
import com.estate.dto.NearbyAmenityDTO;
import com.estate.dto.PlanningMapDTO;
import com.estate.dto.SupplierDTO;
import com.estate.security.CustomUserDetails;
import com.estate.service.BuildingDetailService;
import com.estate.service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/staff/building")
@RequiredArgsConstructor
public class StaffBuildingDetailAPI {

    private final BuildingDetailService buildingDetailService;
    private final BuildingService buildingService;

    /**
     * Trả về thông tin bổ sung của building: suppliers, planningMaps, nearbyAmenities.
     * legalAuthorities chỉ trả về nếu staff được phân công phụ trách building đó.
     */
    @GetMapping("/{id}/detail")
    public ResponseEntity<BuildingExtraDetailResponse> getBuildingExtraDetail(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        // Staff chỉ được xem building mình phụ trách
        if (!buildingService.isStaffManagesBuilding(user.getUserId(), id)) {
            throw new AccessDeniedException("Bạn không có quyền xem chi tiết tòa nhà này.");
        }

        List<LegalAuthorityDTO> legalAuthorities = buildingDetailService.getLegalAuthorities(id);
        List<SupplierDTO> suppliers = buildingDetailService.getSuppliers(id);
        List<PlanningMapDTO> planningMaps = buildingDetailService.getPlanningMaps(id);
        List<NearbyAmenityDTO> nearbyAmenities = buildingDetailService.getNearbyAmenities(id);

        return ResponseEntity.ok(new BuildingExtraDetailResponse(
                legalAuthorities, suppliers, planningMaps, nearbyAmenities
        ));
    }

    // ── Inner record dùng làm response wrapper ──────────────────────────────
    public record BuildingExtraDetailResponse(
            List<LegalAuthorityDTO> legalAuthorities,
            List<SupplierDTO> suppliers,
            List<PlanningMapDTO> planningMaps,
            List<NearbyAmenityDTO> nearbyAmenities
    ) {}
}