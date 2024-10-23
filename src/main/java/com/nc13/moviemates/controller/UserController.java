package com.nc13.moviemates.controller;

import com.nc13.moviemates.component.model.UserModel;
import com.nc13.moviemates.entity.HistoryEntity;
import com.nc13.moviemates.entity.UserEntity;
import com.nc13.moviemates.service.HistoryService;
import com.nc13.moviemates.service.UserService;
import com.nc13.moviemates.serviceImpl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Slf4j
@Controller
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService service;
    private final HistoryService historyService;
    private final UserService userService;

    @GetMapping("/mypage/{id}")
    public String getList(Model model, @PathVariable Long id){
        Optional<UserEntity> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            model.addAttribute("user", userOptional.get());  // 값이 있으면 ReviewEntity를 넘김
        } else {
            throw new RuntimeException("User not found");
        }
        HistoryEntity histories = historyService.findById(id).orElse(null);
        model.addAttribute("histories", histories);
        return "profile/main";
    }




    @GetMapping("/login")
    public String login() {
        return "admin/auth-login";
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserEntity user, HttpServletRequest request) {
        System.out.println("유저는!!" + user);
        Map<String, Object> response = new HashMap<>();
        UserEntity loginUser = service.login(user);
        System.out.println("유저는!!" + loginUser);
        log.info("##### 로그인 사용자 정보 : {}", loginUser);

        if (loginUser != null) {
            // 세션에 사용자 정보 저장
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", loginUser);
            log.info("##### 로그인 세션 정보 : {}", session.getAttribute("loginUser"));

            // 로그인 성공 처리
            response.put("status", "success");
            response.put("user", loginUser);
            System.out.println("역할출력" + loginUser.getRole());
            if ("ROLE_ADMIN".equals(loginUser.getRole().getKey())) {
                response.put("redirectUrl", "/api/admin");  // 관리자 로그인 페이지로 리다이렉트
            } else {
                response.put("redirectUrl", "/");  // 일반 사용자는 메인 페이지로 리다이렉트
            }

            return ResponseEntity.ok(response);  // 200 OK와 함께 성공 응답
        } else {
            // 로그인 실패 처리
            response.put("status", "error");
            response.put("message", "로그인 실패: 잘못된 사용자 정보입니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);  // 401 Unauthorized 응답
        }
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }


    @GetMapping("/list")
    public ResponseEntity<List<?>> getList() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        if (service.findById(id).isPresent()) {
            return ResponseEntity.ok(service.findById(id).get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // register.html을 반환
    }

    @ResponseBody
    @PostMapping("/register")
    public ResponseEntity<Boolean> insert(@RequestBody UserEntity user) {
        System.out.println(user);
        return ResponseEntity.ok(service.insert(user));
    }

    @GetMapping("/profile/setting/{id}")
    public String getProfile(Model model, @PathVariable Long id)
    {
        Optional<UserEntity> userOptional = service.findById(id);
        if(userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            // user의 필드를 다루는 로직을 추가할 수 있습니다.
        }
        model.addAttribute("userId", 1);
        model.addAttribute("user", userOptional.orElse(null));
        System.out.println(userOptional.get());
        System.out.println(userOptional);
        return "profile/setting";}

    @ResponseBody
    @PostMapping("/updateMany")
    public ResponseEntity<Boolean> updateByJspreadsheet(@RequestBody List<UserModel> userList) {
        return ResponseEntity.ok(service.update(userList));
    }

    @PutMapping("/update")
    public ResponseEntity<Boolean> update(@RequestBody UserEntity user) {
        return ResponseEntity.ok(service.save(user));
    }
    @ResponseBody
    @PostMapping("/update/{userId}")
    public ResponseEntity<Boolean> update(@RequestPart("userData") UserModel userData, @RequestPart("password") String password,
                                          @RequestPart(value = "file", required = false) MultipartFile file) {
        System.out.println("넘어온 값" + userData);
        System.out.println("넘어온 값" + password);
        if(!service.existsByPassword(password)){
            return ResponseEntity.ok(false);
        }
        return ResponseEntity.ok(service.updateUserInfo(userData, file));
    }

    @ResponseBody
    @PostMapping("/deleteMany")
    public ResponseEntity<Boolean> deleteMany(@RequestBody List<Long> userIdList){
        return ResponseEntity.ok(service.deleteMany(userIdList));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteById(id));
    }

    @GetMapping("/quantity")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }

    @GetMapping("/presence/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable Long id) {
        return ResponseEntity.ok(service.existsById(id));
    }
}
