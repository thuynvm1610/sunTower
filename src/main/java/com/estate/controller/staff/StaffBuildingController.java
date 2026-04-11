package com.estate.controller.staff;

import com.estate.enums.Direction;
import com.estate.enums.Level;
import com.estate.enums.PropertyType;
import com.estate.enums.TransactionType;
import com.estate.security.CustomUserDetails;
import com.estate.service.BuildingService;
import com.estate.service.DistrictService;
import com.estate.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/staff")
@RequiredArgsConstructor
public class StaffBuildingController {
    private final BuildingService buildingService;
    private final DistrictService districtService;
    private final StaffService staffService;

    @GetMapping("/buildings")
    public String building(
            Model model,
            @AuthenticationPrincipal CustomUserDetails user
        ) {
        model.addAttribute("directions", Direction.values());
        model.addAttribute("levels", Level.values());

        model.addAttribute("propertyTypes", PropertyType.values());
        model.addAttribute("transactionTypes", TransactionType.values());

        model.addAttribute("staffName", staffService.getStaffName(user.getUserId()));
        model.addAttribute("staffAvatar", staffService.getStaffAvatar(user.getUserId()));

        return "staff/building-list";
    }
}
