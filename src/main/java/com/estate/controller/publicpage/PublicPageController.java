package com.estate.controller.publicpage;

import com.estate.enums.PropertyType;
import com.estate.enums.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/suntower")
@RequiredArgsConstructor
public class PublicPageController {
    @GetMapping("")
    public String home(
            Model model,
            @RequestParam(required = false) String buildingName
    ) {
        model.addAttribute("propertyTypes", PropertyType.values());
        model.addAttribute("transactionTypes", TransactionType.values());

        return "publicPage/publicPage";
    }
}
