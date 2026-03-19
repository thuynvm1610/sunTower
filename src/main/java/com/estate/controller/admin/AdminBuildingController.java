package com.estate.controller.admin;

import com.estate.dto.*;
import com.estate.enums.Direction;
import com.estate.enums.Level;
import com.estate.enums.PropertyType;
import com.estate.enums.TransactionType;
import com.estate.security.CustomUserDetails;
import com.estate.service.BuildingDetailService;
import com.estate.service.BuildingService;
import com.estate.service.DistrictService;
import com.estate.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/building")
public class AdminBuildingController {

    @Autowired private BuildingService       buildingService;
    @Autowired private StaffService          staffService;
    @Autowired private DistrictService       districtService;
    @Autowired private BuildingDetailService buildingDetailService;

    @GetMapping("/list")
    public String listBuildings(
            Model model,
            @AuthenticationPrincipal CustomUserDetails user) {

        model.addAttribute("managers",         staffService.getStaffsName());
        model.addAttribute("wards",            buildingService.getWardName());
        model.addAttribute("streets",          buildingService.getStreetName());
        model.addAttribute("directions",       Direction.values());
        model.addAttribute("levels",           Level.values());
        model.addAttribute("districts",        districtService.findAll());
        model.addAttribute("propertyTypes",    PropertyType.values());
        model.addAttribute("transactionTypes", TransactionType.values());
        model.addAttribute("page",             "building");
        model.addAttribute("staffName",        staffService.getStaffName(user.getCustomerId()));
        model.addAttribute("staffAvatar",      staffService.getStaffAvatar(user.getCustomerId()));

        return "admin/building-list";
    }

    @GetMapping("/search")
    public String searchBuildings(
            BuildingFilterDTO filter,
            Model model,
            @AuthenticationPrincipal CustomUserDetails user) {

        model.addAttribute("filter",           filter);
        model.addAttribute("managers",         staffService.getStaffsName());
        model.addAttribute("wards",            buildingService.getWardName());
        model.addAttribute("streets",          buildingService.getStreetName());
        model.addAttribute("directions",       Direction.values());
        model.addAttribute("levels",           Level.values());
        model.addAttribute("districts",        districtService.findAll());
        model.addAttribute("propertyTypes",    PropertyType.values());
        model.addAttribute("transactionTypes", TransactionType.values());
        model.addAttribute("page",             "building");
        model.addAttribute("staffName",        staffService.getStaffName(user.getCustomerId()));
        model.addAttribute("staffAvatar",      staffService.getStaffAvatar(user.getCustomerId()));

        return "admin/building-search";
    }

    @GetMapping("/add")
    public String addBuildingForm(
            Model model,
            @AuthenticationPrincipal CustomUserDetails user) {

        model.addAttribute("staffs",      staffService.getStaffsName());
        model.addAttribute("districts",   districtService.findAll());
        model.addAttribute("directions",  Direction.values());
        model.addAttribute("levels",      Level.values());
        model.addAttribute("page",        "building");
        model.addAttribute("staffName",   staffService.getStaffName(user.getCustomerId()));
        model.addAttribute("staffAvatar", staffService.getStaffAvatar(user.getCustomerId()));

        return "admin/building-add";
    }

    @GetMapping("/edit/{id}")
    public String editBuilding(
            @PathVariable("id") Long id,
            Model model,
            @AuthenticationPrincipal CustomUserDetails user) {

        model.addAttribute("building",    buildingService.findById(id));
        model.addAttribute("managers",    staffService.getStaffsName());
        model.addAttribute("districts",   districtService.findAll());
        model.addAttribute("directions",  Direction.values());
        model.addAttribute("levels",      Level.values());
        model.addAttribute("page",        "building");
        model.addAttribute("staffName",   staffService.getStaffName(user.getCustomerId()));
        model.addAttribute("staffAvatar", staffService.getStaffAvatar(user.getCustomerId()));

        return "admin/building-edit";
    }

    @GetMapping("/{id}")
    public String detailBuilding(
            @PathVariable("id") Long id,
            Model model,
            @AuthenticationPrincipal CustomUserDetails user) {

        model.addAttribute("building", buildingService.viewById(id));

        model.addAttribute("suppliers",
                buildingDetailService.getSuppliers(id));

        model.addAttribute("planningMaps",
                buildingDetailService.getPlanningMaps(id));

        model.addAttribute("nearbyAmenities",
                buildingDetailService.getNearbyAmenities(id));

        // legal_authority: chỉ ADMIN hoặc STAFF phụ trách building mới xem
        if (user.getRole().equals("ADMIN")
                || buildingService.isStaffManagesBuilding(user.getCustomerId(), id)) {
            model.addAttribute("legalAuthorities",
                    buildingDetailService.getLegalAuthorities(id));
        }

        model.addAttribute("page", "building");
        model.addAttribute("staffName", staffService.getStaffName(user.getCustomerId()));
        model.addAttribute("staffAvatar", staffService.getStaffAvatar(user.getCustomerId()));

        return "admin/building-detail";
    }
}