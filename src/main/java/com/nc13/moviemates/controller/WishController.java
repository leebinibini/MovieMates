package com.nc13.moviemates.controller;

import com.nc13.moviemates.component.model.WishModel;
import com.nc13.moviemates.entity.UserEntity;
import com.nc13.moviemates.entity.WishEntity;
import com.nc13.moviemates.service.UserService;
import com.nc13.moviemates.service.WishService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/wish")
public class WishController {
    private final WishService service;
    private final UserService userService;

    @GetMapping("/list/{userId}")
    public String showWishList(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserEntity  loginUser  = (UserEntity) session.getAttribute("loginUser");
        if (loginUser == null || loginUser.getId() == null) {
            model.addAttribute("errorMessage", "User not logged in");
            return "error";  // 로그인하지 않은 경우 에러 페이지로 이동
        }

        Long userId = loginUser.getId();
        Optional<UserEntity> userOptional = userService.findById(userId);
        if (userOptional.isPresent()) {
            model.addAttribute("user", userOptional.get());  // 값이 있으면 ReviewEntity를 넘김
        } else {
            throw new RuntimeException("User not found");
        }
        model.addAttribute("wishMovie", service.findWishesWithMovieDetails(userId));

        return "profile/wishlist";
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<WishEntity>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @ResponseBody
    @PostMapping("/register")
    public ResponseEntity<Boolean> insert(@RequestBody WishModel wish) {
        // 변환된 WishEntity를 저장
        return ResponseEntity.ok(service.save(wish));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteById(id));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }

    @GetMapping("/existsById/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable Long id) {
        return ResponseEntity.ok(service.existsById(id));
    }

    @PostMapping("/toggle")
    public ResponseEntity<Boolean> toggleWish(@RequestBody WishModel wish) {
        boolean isWishlisted = service.existsByMovieIdandUserId(wish.getMovieId(), wish.getUserId());
        if (isWishlisted) {
            service.delete(wish); // 위시리스트에서 삭제
        } else {
            service.save(wish); // WishEntity를 저장// 위시리스트에 추가
        }
        return ResponseEntity.ok(!isWishlisted); // 토글 후 상태 반환
    }
}
