package com.nc13.moviemates.controller;

import com.nc13.moviemates.serviceImpl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserServiceImpl userService;

    @GetMapping()
    public String showAdmin() {
        return "admin/home";
    }

    @GetMapping("/login")
    public String adminLogin() {
        return "admin/login";
    }

    @GetMapping("logout")
    public String adminLogout() {
        return "redirect:/";
    }

    @GetMapping("register")
    public String adminregister() {
        return "admin/register";
    }

    @GetMapping("401")
    public String show401() {
        return"admin/401";
    }

    @GetMapping("404")
    public String show404() {
        return"admin/404";
    }

    @GetMapping("500")
    public String show500() {
        return"admin/500";
    }



}
