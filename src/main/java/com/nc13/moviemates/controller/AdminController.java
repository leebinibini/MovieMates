package com.nc13.moviemates.controller;

import com.nc13.moviemates.repository.UserRepository;
import com.nc13.moviemates.service.UserService;
import com.nc13.moviemates.service.UserService;
import com.nc13.moviemates.serviceImpl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @GetMapping()
    public String showAdmin() {
        return "admin/home";
    }

    @GetMapping("/")
    public String adminLogout() {
        return "redirect:/";
    }

    @GetMapping("/pages-starter")
    public String adminToStarter() {
        return "admin/pages-starter";
    }

    @GetMapping("register")
    public String adminregister() {
        return "admin/register";
    }

    @GetMapping("password")
    public String adminpassword() {
        return "admin/password";
    }

    @GetMapping("/movie")
    public String adminToMovie() {
        return "admin/movie/list";
    }

    @GetMapping("/theater")
    public String adminToTheater(){
        return "admin/theater/list";
    }

    @GetMapping("/schedule")
    public String adminToSchedule(){
        return "admin/schedule/list";
    }

    @GetMapping("/user")
    public String adminToUser() {
        return "admin/user/list";
    }

    @GetMapping("/reservation")
    public String adminToReservation() {
        return "admin/reservation/list";
    }

    @GetMapping("/review")
    public String adminToReview() {
        return "admin/review/list";
    }


    @GetMapping("charts")
    public String admincharts() {
        return "admin/charts";
    }

    @GetMapping("tables")
    public String admintables() {
        return "admin/tables";
    }


}
