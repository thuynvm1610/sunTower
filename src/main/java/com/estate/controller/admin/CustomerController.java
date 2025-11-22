package com.estate.controller.admin;

import com.estate.dto.BuildingFilterDTO;
import com.estate.enums.Direction;
import com.estate.enums.Level;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/customer")
public class CustomerController {
    @GetMapping("/list")
    public String listBuildings(Model model) {
        model.addAttribute("page", "customer");
        return "admin/customer-list";
    }

    @GetMapping("/search")
    public String searchCustomers(
            @RequestParam(required = false) String fullName,
            Model model
    ) {
        model.addAttribute("fullName", fullName);
        model.addAttribute("page", "customer");
        return "admin/customer-search";
    }
}
