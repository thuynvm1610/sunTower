package com.estate.api.publicpage;

import com.estate.dto.BuildingDetailDTO;
import com.estate.dto.BuildingFilterDTO;
import com.estate.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/suntower")
public class PublicPageAPI {
    @Autowired
    BuildingService buildingService;

    @GetMapping("/building/search")
    public List<BuildingDetailDTO> getBuildingsSearch(
            BuildingFilterDTO filter
    ) {
        return buildingService.searchByCustomer(filter);
    }
}
