package com.estate.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/invoice")
public class AdminInvoiceController {
    @GetMapping("/list")
    public String listInvoices(Model model) {

        return "admin/invoice-list";
    }
}
