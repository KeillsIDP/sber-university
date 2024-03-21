package me.keills.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageRoutingController {
    @GetMapping("/")
    public String mainPage(Model model){
        return "index";
    }

    @GetMapping("/registration")
    public String registrationPage(Model model){
        return "registration";
    }
}
