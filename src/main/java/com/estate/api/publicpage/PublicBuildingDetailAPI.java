package com.estate.api.customer;

import com.estate.dto.NearbyAmenityDTO;
import com.estate.dto.PlanningMapDTO;
import com.estate.dto.SupplierDTO;
import com.estate.service.BuildingDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/suntower/building")
@RequiredArgsConstructor
public class PublicBuildingDetailAPI {

    private final BuildingDetailService buildingDetailService;

    /**
     * Trả về thông tin bổ sung của building: suppliers, planningMaps, nearbyAmenities.
     */
    @GetMapping("/{id}/detail")
    public ResponseEntity<BuildingExtraDetailResponse> getBuildingExtraDetail(@PathVariable Long id) {
        List<SupplierDTO> suppliers = buildingDetailService.getSuppliers(id);
        List<PlanningMapDTO> planningMaps = buildingDetailService.getPlanningMaps(id);
        List<NearbyAmenityDTO> nearbyAmenities = buildingDetailService.getNearbyAmenities(id);

        return ResponseEntity.ok(new BuildingExtraDetailResponse(
                suppliers, planningMaps, nearbyAmenities
        ));
    }

    // ── Inner record dùng làm response wrapper ──────────────────────────────
    public record BuildingExtraDetailResponse(
            List<SupplierDTO> suppliers,
            List<PlanningMapDTO> planningMaps,
            List<NearbyAmenityDTO> nearbyAmenities
    ) {}
}
