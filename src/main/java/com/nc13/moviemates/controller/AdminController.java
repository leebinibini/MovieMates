package com.nc13.moviemates.controller;

import com.nc13.moviemates.repository.UserRepository;
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
    private final UserServiceImpl userService;
    private final UserRepository userRepository;

    @GetMapping()
    public String showAdmin() {
        return "admin/home";
    }

    @GetMapping("/login")
    public String adminLogin() {
        return "admin/home";
    }

//    @ResponseBody
//    @PostMapping("/login")
//    public Map<String, String> adminLogin(@RequestBody Map<String, String> loginTry) {
//        System.out.println("컨트롤러진입!");
//        System.out.println(loginTry);
//    }


    @GetMapping("logout")
    public String adminLogout() {
        return "redirect:/";
    }

    @GetMapping("register")
    public String adminregister() {
        return "admin/register";
    }

    @GetMapping("password")
    public String adminpassword() {
        return "admin/password";
    }


    @GetMapping("layout-static")
    public String adminLayoutStatic() {
        return "admin/layout-static";
    }

    @GetMapping("layout-sidenav-light")
    public String adminLayoutsidenavStatic() {
        return "admin/layout-sidenav-light";
    }

    @GetMapping("charts")
    public String admincharts() {
        return "admin/charts";
    }

    @GetMapping("tables")
    public String admintables() {
        return "admin/tables";
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
