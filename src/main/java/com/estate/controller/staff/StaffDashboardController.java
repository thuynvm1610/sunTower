package com.estate.controller.staff;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/staff")
public class StaffDashboardController {
    @GetMapping("/dashboard")
    public String staffDasboard() {
        return "/staff/dashboard";
    }
}
