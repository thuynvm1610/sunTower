package com.estate.controller.publicpage;

import com.estate.enums.Direction;
import com.estate.enums.Level;
import com.estate.service.BuildingService;
import com.estate.service.DistrictService;
import com.estate.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/suntower")
public class PublicPageController {
    @Autowired
    StaffService staffService;

    @Autowired
    BuildingService buildingService;

    @Autowired
    DistrictService districtService;

    @GetMapping("")
    public String home() {
        return "publicPage/home";
    }

    @GetMapping("/introduce")
    public String about() {
        return "publicPage/introduce";
    }

    @GetMapping("/building")
    public String buildings (
            Model model,
            @RequestParam(required = false) String buildingName
    ) {
        model.addAttribute("managers", staffService.getStaffsName());
        model.addAttribute("wards", buildingService.getWardName());
        model.addAttribute("streets", buildingService.getStreetName());
        model.addAttribute("directions", Direction.values());
        model.addAttribute("levels", Level.values());
        model.addAttribute("districts", districtService.findAll());

        model.addAttribute("buildingName", buildingName == null ? "" : buildingName);

        return "publicPage/building";
    }

    @GetMapping("/contact")
    public String contact() {
        return "publicPage/contact";
    }
}
