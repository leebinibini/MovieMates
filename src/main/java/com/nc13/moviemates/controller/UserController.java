package com.nc13.moviemates.controller;

import com.nc13.moviemates.absent.UserPrincipal;
import com.nc13.moviemates.component.model.UserModel;
import com.nc13.moviemates.entity.HistoryEntity;
import com.nc13.moviemates.entity.UserEntity;
import com.nc13.moviemates.enums.Role;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/mypage/{id}")
    public String getList(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        UserEntity  loginUser  = (UserEntity) session.getAttribute("loginUser");
        if (loginUser == null || loginUser.getId() == null) {
            model.addAttribute("errorMessage", "User not logged in");
            return "error";  // 로그인하지 않은 경우 에러 페이지로 이동
        }

        Long id = loginUser.getId();
        Optional<UserEntity> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            model.addAttribute("user", userOptional.get());  // 값이 있으면 ReviewEntity를 넘김
        } else {
            throw new RuntimeException("User not found");
        }
        List<HistoryEntity> histories = historyService.findByUserId(id);

            model.addAttribute("histories", histories);  // 값이 있으면 ReviewEntity를 넘김

        System.out.println(histories);
        System.out.println(userOptional.get());
        if (loginUser == null) {
            System.out.println("Session has no loginUser attribute. Redirecting to login page.");
        } else {
            System.out.println("Session loginUser: " + loginUser.getId());
        }
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
               Role role = loginUser.getRole();
                System.out.println(role);
                response.put("redirectUrl", "/api/admin");  // 관리자 로그인 페이지로 리다이렉트
            } else {
                response.put("redirectUrl", "/");
                Role role = loginUser.getRole();
                String key= loginUser.getRole().getKey();
                System.out.println(key);
                System.out.println(role);// 일반 사용자는 메인 페이지로 리다이렉트
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
        log.info("로그아웃 전: {}", session);
        if (session != null) {
            session.invalidate();
        }
        log.info("로그아웃 후:{}" , session);
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
    public ResponseEntity<Boolean> insert(@RequestBody UserEntity user, HttpServletRequest request) {
        System.out.println("등록 컨트롤러 진입!:"+user);
        UserEntity savedUser = service.insert(user);  // 서비스 호출
        Boolean isRegistered = (savedUser != null && savedUser.getId() != null);

        if (isRegistered) {
            // 세션에 새 사용자 설정
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", user);
        }
        System.out.println("savedUser:"+savedUser);
        return ResponseEntity.ok(isRegistered);  // true/false 반환
    }

    @GetMapping("/profile/setting/{id}")
    public String getProfile(Model model, HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        UserEntity  loginUser  = (UserEntity) session.getAttribute("loginUser");
        if (loginUser == null || loginUser.getId() == null) {
            model.addAttribute("errorMessage", "User not logged in");
            return "error";  // 로그인하지 않은 경우 에러 페이지로 이동
        }

        Long id = loginUser.getId();
        Optional<UserEntity> userOptional = service.findById(id);
        if(userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            // user의 필드를 다루는 로직을 추가할 수 있습니다.
        }
        model.addAttribute("userId", id);
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
                                          @RequestPart(value = "file", required = false) MultipartFile file,
                                          HttpSession session
    ) {
        UserEntity loginUser = (UserEntity) session.getAttribute("loginUser");

        // 기존 비밀번호가 일치하는지 확인
        if (!passwordEncoder.matches(password, loginUser.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false); // 기존 비밀번호가 일치하지 않음
        }

        if (userData.getPassword() != null && !userData.getPassword().isEmpty()) {
            String encodedNewPassword = passwordEncoder.encode(userData.getPassword());
            userData.setPassword(encodedNewPassword); // 새로운 비밀번호 암호화 후 설정
        }
        System.out.println("현재 비밀번호 값" + password);
        System.out.println("변경된 값 값" + userData.getPassword());

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
