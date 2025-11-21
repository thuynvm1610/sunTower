package com.estate.controller.admin;


import com.estate.dto.BuildingDetailDTO;
import com.estate.dto.BuildingFilterDTO;
import com.estate.dto.BuildingFormDTO;
import com.estate.enums.Direction;
import com.estate.enums.Level;
import com.estate.service.BuildingService;
import com.estate.service.DistrictService;
import com.estate.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/building")
public class BuildingController {
    @Autowired
    private BuildingService buildingService;

    @Autowired
    private UserService userService;

    @Autowired
    private DistrictService districtService;

    @GetMapping("/list")
    public String listBuildings(Model model) {
        model.addAttribute("managers", userService.getStaffName());
        model.addAttribute("wards", buildingService.getWardName());
        model.addAttribute("streets", buildingService.getStreetName());
//        model.addAttribute("directions", buildingService.getDirectionName());
        model.addAttribute("directions", Direction.values());
//        model.addAttribute("levels", buildingService.getLevelName());
        model.addAttribute("levels", Level.values());
        model.addAttribute("districts", districtService.findAll());
        model.addAttribute("page", "building");
        return "admin/building-list";
    }

    @GetMapping("/search")
    public String searchBuildings(
            BuildingFilterDTO filter,
            Model model
    ) {
        model.addAttribute("filter", filter);

        model.addAttribute("managers", userService.getStaffName());
        model.addAttribute("wards", buildingService.getWardName());
        model.addAttribute("streets", buildingService.getStreetName());
//        model.addAttribute("directions", buildingService.getDirectionName());
        model.addAttribute("directions", Direction.values());
//        model.addAttribute("levels", buildingService.getLevelName());
        model.addAttribute("levels", Level.values());
        model.addAttribute("districts", districtService.findAll());
        model.addAttribute("page", "building");
        return "admin/building-search";
    }

    @GetMapping("/add")
    public String addBuildingForm(Model model) {
        model.addAttribute("building", new BuildingFormDTO());

        model.addAttribute("managers", userService.getStaffName());

        model.addAttribute("districts", districtService.findAll());

        model.addAttribute("directions", Direction.values());

        model.addAttribute("levels", Level.values());

        model.addAttribute("page", "building");

        return "admin/building-add";
    }

    @GetMapping("/edit/{id}")
    public String editBuilding(
            @PathVariable("id") Long id,
            Model model
    ) {
        BuildingFormDTO building = buildingService.findById(id);
        model.addAttribute("building", building);

        model.addAttribute("managers", userService.getStaffName());

        model.addAttribute("districts", districtService.findAll());

        model.addAttribute("directions", Direction.values());

        model.addAttribute("levels", Level.values());

        model.addAttribute("page", "building");

        return "admin/building-edit";
    }

    @GetMapping("/{id}")
    public String detailBuilding(
            @PathVariable("id") Long id,
            Model model
    ) {
        BuildingDetailDTO building = buildingService.viewById(id);
        model.addAttribute("building", building);

        model.addAttribute("page", "building");

        return "admin/building-detail";
    }

}
