package com.estate.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/contract")
public class ContractController {
    @GetMapping("/list")
    public String listContracts(Model model) {
        model.addAttribute("page", "contract");
        return "admin/contract-list";
    }
}
