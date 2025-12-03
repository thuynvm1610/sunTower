package com.estate.controller.admin;

import com.estate.dto.ContractDetailDTO;
import com.estate.dto.ContractFilterDTO;
import com.estate.dto.ContractFormDTO;
import com.estate.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/contract")
public class AdminContractController {
    @Autowired
    CustomerService customerService;

    @Autowired
    BuildingService buildingService;

    @Autowired
    StaffService staffService;

    @Autowired
    ContractService contractService;

    @Autowired
    RentAreaService rentAreaService;

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

    @GetMapping("/add")
    public String addCustomerForm(Model model) {
        model.addAttribute("staffs", staffService.getStaffsName());
        model.addAttribute("buildings", buildingService.getBuildingsName());
        model.addAttribute("customers", customerService.getCustomersName());

        Map<Long, List<Integer>> rentAreas = rentAreaService.getAllRentAreas();
        model.addAttribute("rentAreas", rentAreas);

        model.addAttribute("page", "contract");

        return "admin/contract-add";
    }

    @GetMapping("/edit/{id}")
    public String editContract(
            @PathVariable("id") Long id,
            Model model
    ) {
        ContractFormDTO contract = contractService.findById(id);
        model.addAttribute("contract", contract);

        model.addAttribute("buildings", buildingService.getBuildingsName());
        model.addAttribute("customers", customerService.getCustomersName());
        model.addAttribute("staffs", staffService.getStaffsName());

        model.addAttribute("page", "contract");

        return "admin/contract-edit";
    }

    @GetMapping("/{id}")
    public String detailContract(
            @PathVariable("id") Long id,
            Model model
    ) {
        ContractDetailDTO contract = contractService.viewById(id);
        model.addAttribute("contract", contract);
        model.addAttribute("page", "contract");
        return "admin/contract-detail";
    }
}
