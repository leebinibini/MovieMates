package com.nc13.moviemates.controller;

import com.nc13.moviemates.entity.UserEntity;
import com.nc13.moviemates.repository.UserRepository;
import com.nc13.moviemates.service.UserService;
import com.nc13.moviemates.service.UserService;
import com.nc13.moviemates.serviceImpl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@CrossOrigin
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @GetMapping()
    public String showAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserEntity loginUser = (UserEntity) session.getAttribute("loginUser");

        if ("ROLE_ADMIN".equals(loginUser.getRole().getKey())) {
            return "admin/home";
        } else {
            session.invalidate();
            return "redirect:/";
        }

    }

    @GetMapping("/logout")
    public String adminLogout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        log.info("관리자 로그아웃 전: {}", session);
        if (session != null) {
            session.invalidate();
        }
        log.info("관리자 로그아웃 후:{}" , session);
        return "redirect:/";
    }

    @GetMapping("/movie")
    public String adminToMovie(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserEntity loginUser = (UserEntity) session.getAttribute("loginUser");

        if ("ROLE_ADMIN".equals(loginUser.getRole().getKey())) {
            return "admin/movie/list";
        } else {
            session.invalidate();
            return "redirect:/";
        }
    }

    @GetMapping("/theater")
    public String adminToTheater(HttpServletRequest request){
        HttpSession session = request.getSession();
        UserEntity loginUser = (UserEntity) session.getAttribute("loginUser");

        if ("ROLE_ADMIN".equals(loginUser.getRole().getKey())) {
            return "admin/theater/list";
        } else {
            session.invalidate();
            return "redirect:/";
        }
    }

    @GetMapping("/schedule")
    public String adminToSchedule(HttpServletRequest request){
        HttpSession session = request.getSession();
        UserEntity loginUser = (UserEntity) session.getAttribute("loginUser");

        if ("ROLE_ADMIN".equals(loginUser.getRole().getKey())) {
            return "admin/schedule/list";
        } else {
            session.invalidate();
            return "redirect:/";
        }
    }

    @GetMapping("/user")
    public String adminToUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserEntity loginUser = (UserEntity) session.getAttribute("loginUser");

        if ("ROLE_ADMIN".equals(loginUser.getRole().getKey())) {
            return "admin/user/list";
        } else {
            session.invalidate();
            return "redirect:/";
        }
    }

    @GetMapping("/reservation")
    public String adminToReservation(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserEntity loginUser = (UserEntity) session.getAttribute("loginUser");

        if ("ROLE_ADMIN".equals(loginUser.getRole().getKey())) {
            return "admin/reservation/list";
        } else {
            session.invalidate();
            return "redirect:/";
        }
    }

    @GetMapping("/review")
    public String adminToReview(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserEntity loginUser = (UserEntity) session.getAttribute("loginUser");

        if ("ROLE_ADMIN".equals(loginUser.getRole().getKey())) {
            return "admin/review/list";
        } else {
            session.invalidate();
            return "redirect:/";
        }
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
