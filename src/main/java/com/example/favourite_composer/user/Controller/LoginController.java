package com.example.favourite_composer.user.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.favourite_composer.user.domain.User;
import com.example.favourite_composer.user.service.UserDetailsServiceImpl;

@Controller
public class LoginController {

    @Autowired
    private UserDetailsServiceImpl userService;

    @GetMapping("/signin")
    public String showLogin() {
        return "login/signinView";
    }

    @GetMapping("/new")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "login/registerView";
    }

    @PostMapping("/new")
    public String processRegister(
            @ModelAttribute User user,
            Model model) {

        User created = userService.addUser(user);

        if (created == null) {
            model.addAttribute("error", "Username already exists");
            return "login/registerView";
        }

        return "redirect:/signin";
    }
}
