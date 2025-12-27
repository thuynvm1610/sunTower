package com.estate.controller.publicpage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/suntower")
public class PublicPageController {
    @GetMapping("")
    public String home() {
        return "publicPage/home";
    }

    @GetMapping("/introduce")
    public String about() {
        return "publicPage/introduce";
    }

    @GetMapping("/building")
    public String buildings() {
        return "publicPage/building";
    }

    @GetMapping("/contact")
    public String contact() {
        return "publicPage/contact";
    }
}
