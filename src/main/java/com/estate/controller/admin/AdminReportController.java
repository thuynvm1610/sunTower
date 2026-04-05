package com.estate.controller.admin;

import com.estate.dto.ReportDTO;
import com.estate.security.CustomUserDetails;
import com.estate.service.ReportService;
import com.estate.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin/report")
public class AdminReportController {

    @Autowired private ReportService reportService;
    @Autowired private StaffService staffService;

    @GetMapping("")
    public String showReport(
            Model model,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        int currentYear = LocalDate.now().getYear();
        ReportDTO report = reportService.getReport(currentYear);

        model.addAttribute("report", report);
        model.addAttribute("page", "report");
        model.addAttribute("staffName", staffService.getStaffName(user.getCustomerId()));
        model.addAttribute("staffAvatar", staffService.getStaffAvatar(user.getCustomerId()));

        return "admin/report";
    }
}