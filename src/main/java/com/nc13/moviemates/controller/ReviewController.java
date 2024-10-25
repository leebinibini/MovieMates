package com.nc13.moviemates.controller;

import com.nc13.moviemates.entity.MovieEntity;
import com.nc13.moviemates.entity.ReviewEntity;
import com.nc13.moviemates.entity.UserEntity;
import com.nc13.moviemates.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {
    private final ReviewService service;
    private final HistoryService historyService;
    private final TheaterService theaterService;
    private final ScheduleService scheduleService;
    private final WishService wishService;
    private final MovieService movieService;
    private final UserService userService;
    @GetMapping("/list")
    public ResponseEntity<List<ReviewEntity>> getList() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ReviewEntity>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/register")
    public String getMoviesByUserId(@RequestParam("movieId") Long movieId,
                                    Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserEntity loginUser  = (UserEntity) session.getAttribute("loginUser");
        Long userId = loginUser.getId();
        System.out.println(userId);
        Optional<MovieEntity> movie = historyService.findMovieForReview(userId, movieId);
        if (movie.isPresent()) {
            boolean isWishlisted = wishService.existsByMovieIdandUserId(movieId, userId);
            System.out.println("isWishlisted 값: " + isWishlisted);
            model.addAttribute("isWishlisted", isWishlisted);
            model.addAttribute("theaterList", theaterService.findByMovieId(movieId));
            model.addAttribute("scheduleList", scheduleService.findByMovieId(movieId));
            model.addAttribute("reviewList", service.findAllByMovieId(movieId));
            model.addAttribute("movieList", movieService.findAll());
            model.addAttribute("movie", movie.get());  // 영화 정보를 모델에 추가
            model.addAttribute("userId", userId);// 유저 아이디도 모델에 추가
            return  "single";  // 리뷰 작성 페이지로 이동
        } else {
            // 영화 정보를 찾지 못한 경우 예외 처리 (예: 에러 페이지로 이동)
            return "error/404";  // 적절한 에러 페이지 반환
        }
        // 영화 제목 리스트 반환
    }

    @GetMapping("/history/register")
    public String getMoviesByHistoryUserId(@RequestParam("movieId") Long movieId,
                                    Model model, HttpSession session) {
        UserEntity loginUser  = (UserEntity) session.getAttribute("loginUser");
        Long userId = loginUser.getId();
        System.out.println(userId);
        Optional<MovieEntity> movie = historyService.findMovieForReview(userId, movieId);

            model.addAttribute("theaterList", theaterService.findByMovieId(movieId));
            model.addAttribute("scheduleList", scheduleService.findByMovieId(movieId));
           // model.addAttribute("reviewList", service.findAllByMovieId(movieId));
            model.addAttribute("movieList", movieService.findAll());
            model.addAttribute("movie", movie.get());  // 영화 정보를 모델에 추가
            model.addAttribute("userId", userId);// 유저 아이디도 모델에 추가
            model.addAttribute("reviewList", service.findReviewsWithUserImage(movieId));
        System.out.println(service.findReviewsWithUserImage(movieId));
            return  "review";  // 리뷰 작성 페이지로 이동

    }

    @ResponseBody
    @PostMapping("/register")
    public ResponseEntity<String> insert(@RequestBody ReviewEntity review, HttpSession session) {
        // 세션에서 로그인한 유저 정보 가져오기
        UserEntity loginUser = (UserEntity) session.getAttribute("loginUser");

        // 유저가 로그인되어 있지 않다면
        if (loginUser == null || loginUser.getId() == null) {
            System.out.println("유저 아이디가 없습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유저 아이디가 필요합니다.");
        }

        Long userId = loginUser.getId();
        review.setWriterId(userId); // 리뷰 작성자 정보 설정
        boolean hasWatched = historyService.hasWatchedMovie(userId, review.getMovieId());
        System.out.println("리뷰 등록 컨트롤러 진입");
        System.out.println("리뷰 정보: " + review);
        if (!hasWatched) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("영화를 본 사용자만 리뷰를 작성할 수 있습니다.");
        }

        boolean result = service.save(review);
        // 리뷰 저장
        if (result) {
            return ResponseEntity.ok("리뷰가 성공적으로 등록되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("리뷰 등록에 실패했습니다.");
        }
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> update(@RequestBody ReviewEntity review, @PathVariable Long id, HttpSession session){
        UserEntity loginUser = (UserEntity) session.getAttribute("loginUser");
        Long userId = loginUser.getId();
        Optional<ReviewEntity> optionalReview = service.findById(id);

        if (!optionalReview.isPresent()) {
            return ResponseEntity.notFound().build(); // 리뷰가 없을 경우
        }

        ReviewEntity existingReview = optionalReview.get(); // Optional에서 ReviewEntity를 가져옴

        // 내용만 업데이트
        existingReview.setContent(review.getContent());

        // 기존 리뷰의 다른 필드는 그대로 유지하고 저장
        service.save(existingReview);

        return ResponseEntity.ok(true);
    }

    @ResponseBody
    @PostMapping("/deleteMany")
    public ResponseEntity<Long> deleteMany(@RequestBody List<Long> reviewIdList){
        return ResponseEntity.ok(service.deleteMany(reviewIdList));
    }

    @DeleteMapping("/delete/{id}")
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

    @GetMapping("/myList/{userId}")
    public String showReviewList(Model model, HttpServletRequest request) {
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
        model.addAttribute("movieWithReview", service.findReviewsWithMovieByUserId(userId));
        return "profile/myreviewlist";
    }

}
