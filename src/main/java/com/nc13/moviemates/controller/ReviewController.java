package com.nc13.moviemates.controller;

import com.nc13.moviemates.component.model.MovieModel;
import com.nc13.moviemates.component.model.ReviewModel;
import com.nc13.moviemates.entity.MovieEntity;
import com.nc13.moviemates.entity.ReviewEntity;
import com.nc13.moviemates.entity.ScheduleEntity;
import com.nc13.moviemates.service.MovieService;
import com.nc13.moviemates.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private final MovieService movieService;

    @GetMapping("/list")
    public ResponseEntity<List<ReviewEntity>> getList() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ReviewEntity>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/register/{userId}")
    public String showReviewPage(@PathVariable Long userId, Model model){
        List<MovieEntity> watchedMovies = movieService.getWatchedMoviesByUserId(userId);

        // 영화 목록을 모델에 추가
        model.addAttribute("watchedMovies", watchedMovies);
        model.addAttribute("userId", userId); // 유저 아이디도 전달

        return "/single2";
    }



    @ResponseBody
    @PostMapping("/register")
    public ResponseEntity<Boolean> insert(@RequestBody ReviewEntity review) {

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
