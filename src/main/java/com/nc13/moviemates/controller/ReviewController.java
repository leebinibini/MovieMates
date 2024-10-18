package com.nc13.moviemates.controller;

import com.nc13.moviemates.component.model.MovieModel;
import com.nc13.moviemates.component.model.ReviewModel;
import com.nc13.moviemates.entity.MovieEntity;
import com.nc13.moviemates.entity.ReviewEntity;
import com.nc13.moviemates.entity.ScheduleEntity;
import com.nc13.moviemates.entity.UserEntity;
import com.nc13.moviemates.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    private final ReviewService reviewService;
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
    public String getMoviesByUserId(@RequestParam Long userId, @RequestParam Long movieId, Model model) {
        Optional<MovieEntity> movie = historyService.findMovieForReview(userId, movieId);
        if (movie.isPresent()) {
            boolean isWishlisted = wishService.existsByMovieIdandUserId(movieId, userId);
            System.out.println("isWishlisted 값: " + isWishlisted);
            model.addAttribute("isWishlisted", isWishlisted);
            model.addAttribute("theaterList", theaterService.findByMovieId(movieId));
            model.addAttribute("scheduleList", scheduleService.findByMovieId(movieId));
            model.addAttribute("reviewList", reviewService.findAllByMovieId(movieId));
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



    @ResponseBody
    @PostMapping("/register")
    public ResponseEntity<String> insert(@RequestBody ReviewEntity review) {

       /* 영화를 본 사람만 볼 수 작성할 수 있도록
       boolean hasWatched = reviewService.hasUserWatchedMovie(review.getWriterId(), review.getMovieId());
        if (!hasWatched) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("리뷰를 작성하려면 먼저 영화를 보셔야 합니다.");
        }*/
        // 유저 아이디가 있는지 확인
        if (review.getWriterId() == null) {
            System.out.println("유저 아이디가 없습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유저 아이디가 필요합니다.");
        }

        System.out.println("등록 컨트롤러 왔니");
        System.out.println("review: " + review);

        // 리뷰 저장 성공 시 true 반환, 실패 시 false 반환
        boolean result = service.save(review);
        if (result) {
            return ResponseEntity.ok("리뷰가 성공적으로 등록되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("리뷰 등록에 실패했습니다.");
        }
    }


    @PutMapping
    public ResponseEntity<Boolean> update(@RequestBody ReviewEntity review){
        return ResponseEntity.ok(service.save(review));
    }

    @ResponseBody
    @PostMapping("/deleteMany")
    public ResponseEntity<Long> deleteMany(@RequestBody List<Long> reviewIdList){
        return ResponseEntity.ok(service.deleteMany(reviewIdList));
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

    @GetMapping("/myList/{userId}")
    public String showReviewList(Model model, @PathVariable Long userId) {
        model.addAttribute("user", userService.findById(userId));
        model.addAttribute("writerId", userId);
        model.addAttribute("reviewList", service.getReviewsByWriterId(userId));
        model.addAttribute("movieWithReview", service.findReviewsWithMovieByUserId(userId));
        System.out.println(service.findReviewsWithMovieByUserId(userId));
        System.out.println(service.getReviewsByWriterId(userId));
        return "profile/myReviewList";
    }
}
