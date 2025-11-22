package com.estate.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/staff")
public class StaffController {
    @GetMapping("/list")
    public String listStaffs(Model model) {
        model.addAttribute("page", "staff");
        return "admin/staff-list";
    }
}
