package com.nc13.moviemates.controller;

import com.nc13.moviemates.component.model.MovieModel;
import com.nc13.moviemates.component.model.ReviewModel;
import com.nc13.moviemates.entity.MovieEntity;
import com.nc13.moviemates.entity.ReviewEntity;
import com.nc13.moviemates.entity.ScheduleEntity;
import com.nc13.moviemates.service.HistoryService;
import com.nc13.moviemates.service.MovieService;
import com.nc13.moviemates.service.ReviewService;
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
    @GetMapping("/list")
    public ResponseEntity<List<ReviewEntity>> getList() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ReviewEntity>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/register")
    public String getMoviesByUserId(@RequestParam Long userId, Model model) {
        // userId로 리뷰에 연결된 영화 목록을 가져옴
        List<MovieEntity> movie = historyService.findMovieByUserId(userId);

        /*if (movieTitles.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);  // 영화 목록이 없을 경우 204 응답
        }*/
        if (movie == null || movie.isEmpty()) {
            model.addAttribute("error", "No movies found for this user.");
            return "error";  // error.html로 이동
        }
        model.addAttribute("movieTitles", movie);
        return "imsisingle";  // 영화 제목 리스트 반환
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
