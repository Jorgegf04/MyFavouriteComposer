package com.example.favourite_composer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class CookieController {

    // Cambiar tema (crear cookie)
    @PostMapping("/theme")
    public String setTheme(
            @RequestParam String mode,
            HttpServletResponse response,
            HttpServletRequest request) {

        Cookie cookie = new Cookie("theme", mode);
        cookie.setPath("/");
        cookie.setMaxAge(-1);

        response.addCookie(cookie);

        return "redirect:" + request.getHeader("Referer");
    }

    @GetMapping("/theme/delete")
    public String deleteView() {
        return "themes/deleteView";
    }

    // Borrar cookies
    @PostMapping("/theme/delete")
    public String deleteTheme(HttpServletResponse response) {

        Cookie c1 = new Cookie("theme", "");
        c1.setMaxAge(0);
        c1.setPath("/");

        Cookie c2 = new Cookie("themeMinute", "");
        c2.setMaxAge(0);
        c2.setPath("/");

        response.addCookie(c1);
        response.addCookie(c2);

        return "redirect:/";
    }

    // Cookie de 1 minuto
    @PostMapping("/theme/minute")
    public String setThemeOneMinute(
            @RequestParam String mode,
            HttpServletResponse response,
            HttpServletRequest request) {

        Cookie cookie = new Cookie("themeMinute", mode);

        cookie.setPath("/");
        cookie.setMaxAge(60);

        response.addCookie(cookie);

        return "redirect:" + request.getHeader("Referer");
    }

}
