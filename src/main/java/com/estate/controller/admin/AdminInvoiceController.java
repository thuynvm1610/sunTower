package com.estate.controller.admin;

import com.estate.service.BuildingService;
import com.estate.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/invoice")
public class AdminInvoiceController {
    @Autowired
    CustomerService customerService;

    @Autowired
    BuildingService buildingService;

    @GetMapping("/list")
    public String listInvoices(Model model) {
        model.addAttribute("customers", customerService.getCustomersName());
        model.addAttribute("buildings", buildingService.getBuildingsName());

        model.addAttribute("page", "invoice");

        return "admin/invoice-list";
    }
}
