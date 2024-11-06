package com.nc13.moviemates.controller;

import com.nc13.moviemates.entity.MovieEntity;
import com.nc13.moviemates.entity.PosterEntity;
import com.nc13.moviemates.entity.UserEntity;
import com.nc13.moviemates.service.MovieService;
import com.nc13.moviemates.service.PosterService;
import com.nc13.moviemates.service.ReviewService;
import com.nc13.moviemates.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.ast.tree.expression.Star;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HomeController {

    private final MovieService movieService;
    private final PosterService posterService;
    private final ReviewService reviewService;
    private final UserService userService;

    //홈페이지 화면 가져오기
        @GetMapping("/")
        public String home(Model model, HttpServletRequest request) {
            HttpSession session = request.getSession();
            Long userId = null;
            if (session != null) {
                UserEntity loginUser = (UserEntity) session.getAttribute("loginUser");
                if (loginUser != null) {
                    userId = loginUser.getId(); // 로그인한 유저의 ID를 가져옴
                    model.addAttribute("userId", userId); // 모델에 userId 추가
                    Optional<UserEntity> user = userService.findById(userId);
                    user.ifPresent(value -> model.addAttribute("userData", value));
                }
            }
            List<MovieEntity> movie = movieService.findIsShowingMovie();
            List<String> star = new ArrayList<>() {{
                add("☆☆☆☆☆");
            }
        };
            model.addAttribute("topMovieReviews",reviewService.findTop5MoviesWithLongestReview());
            model.addAttribute("userId", userId);
            model.addAttribute("star", star);
            List<MovieEntity> chart = movieService.findChart();
            model.addAttribute("charts", chart);
            model.addAttribute("movies", movie);
            model.addAttribute("movieInfos", movieService.findIsShowingMovie());
            return "index";
    }

}
