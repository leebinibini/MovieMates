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
    private final UserRepository userRepository;

    @GetMapping()
    public String showAdmin() {
        return "admin/home";
    }

//    @GetMapping("/login")
//    public String adminLogin() {
//        return "admin/home";
//    }

    @ResponseBody
    @PostMapping("/login")
    public String adminAuth(@RequestBody Map<String, String> loginTry) {
        System.out.println("adminAuth 컨트롤러 진입!!!");
        String email = loginTry.get("email");
        String password = loginTry.get("password");

        boolean isAuthenticated = userService.authenticate(email, password);

        if (isAuthenticated ) {
            System.out.println("인증완료!");
            return "true"; // 인증 성공 시 home 페이지로 이동
        } else {
            System.out.println("인증실패!");
            return "false"; // 인증 실패 시 에러 메시지 반환
        }
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
