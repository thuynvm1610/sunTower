package com.estate.controller.admin;

import com.estate.dto.ContractFilterDTO;
import com.estate.service.BuildingService;
import com.estate.service.CustomerService;
import com.estate.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/contract")
public class ContractController {
    @Autowired
    CustomerService customerService;

    @Autowired
    BuildingService buildingService;

    @Autowired
    StaffService staffService;

    @GetMapping("/list")
    public String listContracts(Model model) {
        model.addAttribute("customers", customerService.getCustomersName());
        model.addAttribute("buildings", buildingService.getBuildingsName());
        model.addAttribute("staffs", staffService.getStaffsName());
        model.addAttribute("page", "contract");
        return "admin/contract-list";
    }

    @GetMapping("/search")
    public String searchContracts(
            ContractFilterDTO filter,
            Model model
    ) {
        model.addAttribute("filter", filter);

        model.addAttribute("customers", customerService.getCustomersName());
        model.addAttribute("buildings", buildingService.getBuildingsName());
        model.addAttribute("staffs", staffService.getStaffsName());

        model.addAttribute("page", "contract");

        return "admin/contract-search";
    }
}
