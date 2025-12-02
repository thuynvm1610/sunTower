package com.estate.controller.customer;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @GetMapping("/home")
    public String home(
            Model model,
            HttpServletRequest request
    ) {
        model.addAttribute(
                "today",
                LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
                )
        );
        model.addAttribute("clientIp", request.getRemoteAddr());
        return "customer/home";
    }
}
