package com.nc13.moviemates.controller;

import com.nc13.moviemates.component.model.MovieModel;
import com.nc13.moviemates.component.model.WishModel;
import com.nc13.moviemates.entity.MovieEntity;
import com.nc13.moviemates.entity.QMovieEntity;
import com.nc13.moviemates.entity.UserEntity;
import com.nc13.moviemates.entity.WishEntity;
import com.nc13.moviemates.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import com.nc13.moviemates.util.WebCrawlerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.JsonPath;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/movie")
public class MovieController {
    private final MovieService service;
    private final TheaterService theaterService;
    private final ScheduleService scheduleService;
    private final ReviewService reviewService;
    private final WishService wishService;
    private final WebCrawlerService webCrawlerService;
    private final UserService userService;

    @GetMapping("/list")
    public ResponseEntity<List<MovieEntity>> getList() {
        return ResponseEntity.ok(service.findAll());
    }


    @GetMapping("/names")
    public ResponseEntity<List<String>> getNowPlayingList() {
        List<String> title = service.getNowPlayingList();
        return ResponseEntity.ok(title);
    }

    @GetMapping("/order/{movieId}")
    public ResponseEntity<Map<String, Object>> getOrderList(@PathVariable("movieId") Long movieId){
        Map<String, Object> map = new HashMap<>();
        System.out.println(movieId);
        String title = service.findById(movieId)
                .orElseThrow(()-> new RuntimeException("Movie not found"))
                .getTitle();

        map.put("theater", theaterService.findByMovieId(movieId));
        map.put("schedule", scheduleService.findByMovieId(movieId));
        map.put("title", title);
        System.out.println(map);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/single/{movieId}")
    public String getSingle(@PathVariable("movieId") Long movieId,
                            Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserEntity loginUser  = (UserEntity) session.getAttribute("loginUser");
        Long userId = loginUser.getId();

        if (loginUser != null) {
            Optional<UserEntity> user = userService.findById(loginUser.getId());
            user.ifPresent(value -> model.addAttribute("userData", value));
            model.addAttribute("isWishlisted", wishService.existsByMovieIdandUserId(movieId, loginUser.getId()));
        } else {
            model.addAttribute("userData", null);
            model.addAttribute("isWishlisted", false); // 로그인하지 않은 경우 기본값 설정
        }


        Optional<MovieModel> movie = service.findById(movieId);
        if (movie.isPresent()) {
            model.addAttribute("movie", movie.get());
        } else {
            // 영화가 없을 경우 처리
            return "index";
        }

        // Theater, Schedule, Review 리스트 추가
        model.addAttribute("theaterList", theaterService.findByMovieId(movieId));
        model.addAttribute("scheduleList", scheduleService.findByMovieId(movieId));
       // model.addAttribute("reviewList", reviewService.findAllByMovieId(movieId));
        model.addAttribute("movieList", service.findIsShowingMovie());
        model.addAttribute("reviewList", reviewService.findReviewsWithUserImage(movieId));
        System.out.println(movie.get());
        return "single";
    }


    @GetMapping("/{id}")
    public ResponseEntity <Optional<MovieModel>> getById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/register")
    public String toMovieRegister(Model model){
        model.addAttribute("movieList", service.findAll());
        model.addAttribute("theaterList", theaterService.findAll());
        return "admin/movie/register";
    }

    @ResponseBody
    @PostMapping("/register")
    public ResponseEntity<Long> insert (@RequestBody MovieModel movie){
        return ResponseEntity.ok(service.save(movie));
    }

    @ResponseBody
    @PostMapping("/updateMany")
    public ResponseEntity<Boolean> updateByJspreadsheet(@RequestBody List<MovieModel> movieList) {
        System.out.println("영화 수정 컨트롤러 진입 성공!");
        System.out.println("영화리스트" + movieList);
        return ResponseEntity.ok(service.update(movieList));
    }

    @ResponseBody
    @PostMapping("/deleteMany")
    public ResponseEntity<Long> deleteMany(@RequestBody List<Long> movieIdList){
        return ResponseEntity.ok(service.deleteMany(movieIdList));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable long id){
        return ResponseEntity.ok(service.deleteById(id));
    }

    @GetMapping("/exists/{id}")
    public Boolean existsById(Long id) {
        return service.existsById(id);
    }

    @PostMapping("/findMovieIdByName")
    public ResponseEntity<Long> findMovieIdByName(@RequestParam("name") String name){

        return ResponseEntity.ok(service.findMovieIdByName(name));
    }


    public long count() {
        return service.count();}


    @GetMapping("/search")
    public String getSearchList(@RequestParam String searchStr, Model model) {
        model.addAttribute("searchMovieList", service.findSearchList(searchStr));

        return "/search";
    }

//    @GetMapping("/crawl")
//    public String crawlMovies() {
//        try {
//            webCrawlerService.crawl();
//            return "Crawling complete!";
//        } catch (Exception e) {
//            return "Error occurred: " + e.getMessage();
//        }
//    }

}

