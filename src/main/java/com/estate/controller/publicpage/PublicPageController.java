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

    @GetMapping("/gioi-thieu")
    public String about() {
        return "publicPage/gioi-thieu";
    }

    @GetMapping("/toa-nha")
    public String buildings() {
        return "publicPage/toa-nha";
    }

    @GetMapping("/lien-he")
    public String contact() {
        return "publicPage/lien-he";
    }
}
