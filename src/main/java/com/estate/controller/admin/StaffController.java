package com.estate.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/staff")
public class StaffController {
    @GetMapping("/list")
    public String listStaffs(Model model) {
        model.addAttribute("page", "staff");
        return "admin/staff-list";
    }

    @GetMapping("/search")
    public String searchStaffs(
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String fullName,
            Model model
    ) {
        model.addAttribute("role", role);
        model.addAttribute("fullName", fullName);
        model.addAttribute("page", "customer");
        return "admin/staff-search";
    }

    @GetMapping("/add")
    public String addStaffForm(Model model) {

        model.addAttribute("page", "staff");

        return "admin/staff-add";
    }
}
