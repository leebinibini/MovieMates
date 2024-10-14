package com.nc13.moviemates.controller;

import com.nc13.moviemates.component.model.MovieModel;
import com.nc13.moviemates.component.model.ReviewModel;
import com.nc13.moviemates.entity.MovieEntity;
import com.nc13.moviemates.entity.ReviewEntity;
import com.nc13.moviemates.entity.ScheduleEntity;
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

    @GetMapping("/list")
    public ResponseEntity<List<ReviewEntity>> getList() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ReviewEntity>> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/register")
    public String getMoviesByUserId(@RequestParam("userId") Long userId, @RequestParam("movieId") Long movieId, Model model) {
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
    public ResponseEntity<Boolean> insert(@RequestBody ReviewEntity review) {
        System.out.println("등록 컨트롤러 왓니");
        System.out.println("review:"+ review);
        return ResponseEntity.ok(service.save(review));
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
}
