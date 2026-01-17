package com.estate.api.staff;

import com.estate.dto.BuildingFilterDTO;
import com.estate.dto.BuildingListDTO;
import com.estate.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/staff/building")
public class StaffBuildingAPI {
    @Autowired
    BuildingService buildingService;

    @GetMapping("/search")
    public Page<BuildingListDTO> getBuildingsSearchPage(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            BuildingFilterDTO filter
    ) {
        return buildingService.search(filter, page - 1, size);
    }
}
