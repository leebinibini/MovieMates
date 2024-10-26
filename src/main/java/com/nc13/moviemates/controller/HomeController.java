package com.nc13.moviemates.controller;

import com.nc13.moviemates.entity.MovieEntity;
import com.nc13.moviemates.entity.PosterEntity;
import com.nc13.moviemates.entity.UserEntity;
import com.nc13.moviemates.service.MovieService;
import com.nc13.moviemates.service.PosterService;
import com.nc13.moviemates.service.ReviewService;
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

    @GetMapping("details")
    public String Details(){
        return  "details";
    }

    @GetMapping("/test")
    @ResponseBody
    public Map<String, String> test() {

        var map = new HashMap<String, String>();
        map.put("test", "안녕 ");

        return map;
    }

    @GetMapping("/api/form-elements")
    public String toCheck(){
        return  "admin/form-advanced";
    }

    @GetMapping("/api/form-fileuploads")
    public String toCheck2(){
        return  "admin/form-fileuploads";
    }

    @GetMapping("/api/admin/index")
    public String toCheck3(){
        return  "admin/home";
    }

    @GetMapping("/api/admin/tables-basic")
    public String toCheck4(){
        return  "admin/tables-basic";
    }

    @GetMapping("/api/admin/tables-datatable")
    public String toCheck5(){
        return  "admin/tables-datatable";
    }

    @GetMapping("/api/admin/ui-buttons")
    public String toCheck6(){
        return  "admin/ui-buttons";
    }

    @GetMapping("/api/admin/icons-remixicons")
    public String toCheck7(){
        return  "admin/icons-remixicons";
    }

}
