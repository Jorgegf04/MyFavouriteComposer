package com.example.favourite_composer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String home(
            @CookieValue(value = "theme", required = false) String theme, 
             @CookieValue(value = "themeMinute", required = false) String themeMinute,
            Model model) {

        
        String finalTheme = "light"; 

        if (themeMinute != null) {
            finalTheme = themeMinute;
        } else {
            finalTheme = theme;
        }


        model.addAttribute("theme", finalTheme);
    

        return "home";
    }

}