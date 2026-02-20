package com.example.favourite_composer.user.Controller;

import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/all")
    public String showContentForAll() {
        return "{\"content\":\"Public content\"}";
    }

    @GetMapping("/user")
    public String showContentForUsers() {
        return "{\"content\":\"Content for users\"}";
    }

    @GetMapping("/manager")
    public String showContentForManager() {
        return "{\"content\":\"Content for managers\"}";
    }

    @GetMapping("/admin")
    public String showContentForAdmins() {
        return "{\"content\":\"Content for administrators\"}";
    }
}
