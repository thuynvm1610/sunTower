package com.estate.controller.admin;

import com.estate.dto.SaleContractDetailDTO;
import com.estate.dto.SaleContractFilterDTO;
import com.estate.dto.SaleContractFormDTO;
import com.estate.security.CustomUserDetails;
import com.estate.service.BuildingService;
import com.estate.service.CustomerService;
import com.estate.service.SaleContractService;
import com.estate.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/sale-contract")
public class AdminSaleContractController {

    @Autowired private CustomerService customerService;
    @Autowired private BuildingService buildingService;
    @Autowired private StaffService staffService;
    @Autowired private SaleContractService saleContractService;

    // ── common helper ─────────────────────────────────────────────────────────
    private void addCommonAttributes(Model model, CustomUserDetails user, String page) {
        model.addAttribute("customers", customerService.getCustomersName());
        model.addAttribute("buildings", buildingService.getBuildingsName());
        model.addAttribute("staffs", staffService.getStaffsName());
        model.addAttribute("page", page);
        model.addAttribute("staffName", staffService.getStaffName(user.getCustomerId()));
        model.addAttribute("staffAvatar", staffService.getStaffAvatar(user.getCustomerId()));
    }

    @GetMapping("/list")
    public String listSaleContracts(Model model, @AuthenticationPrincipal CustomUserDetails user) {
        addCommonAttributes(model, user, "sale-contract");
        return "admin/sale-contract-list";
    }

    @GetMapping("/search")
    public String searchSaleContracts(
            SaleContractFilterDTO filter,
            Model model,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        model.addAttribute("filter", filter);
        addCommonAttributes(model, user, "sale-contract");
        return "admin/sale-contract-search";
    }

    @GetMapping("/add")
    public String addSaleContractForm(Model model, @AuthenticationPrincipal CustomUserDetails user) {
        addCommonAttributes(model, user, "sale-contract");
        return "admin/sale-contract-add";
    }

    @GetMapping("/edit/{id}")
    public String editSaleContract(
            @PathVariable("id") Long id,
            Model model,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        // Dùng DetailDTO để có tên hiển thị cho các trường readonly
        SaleContractDetailDTO contract = saleContractService.viewById(id);
        model.addAttribute("contract", contract);
        model.addAttribute("page", "sale-contract");
        model.addAttribute("staffName", staffService.getStaffName(user.getCustomerId()));
        model.addAttribute("staffAvatar", staffService.getStaffAvatar(user.getCustomerId()));
        return "admin/sale-contract-edit";
    }

    @GetMapping("/{id}")
    public String detailSaleContract(
            @PathVariable("id") Long id,
            Model model,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        SaleContractDetailDTO contract = saleContractService.viewById(id);
        model.addAttribute("contract", contract);
        model.addAttribute("page", "sale-contract");
        model.addAttribute("staffName", staffService.getStaffName(user.getCustomerId()));
        model.addAttribute("staffAvatar", staffService.getStaffAvatar(user.getCustomerId()));
        return "admin/sale-contract-detail";
    }
}