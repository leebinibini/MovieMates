package com.nc13.moviemates.controller;

import com.nc13.moviemates.entity.MovieEntity;
import com.nc13.moviemates.entity.PosterEntity;
import com.nc13.moviemates.service.MovieService;
import com.nc13.moviemates.service.PosterService;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.ast.tree.expression.Star;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final MovieService movieService;
    private final PosterService posterService;

    //홈페이지 화면 가져오기
        @GetMapping("/")
        public String home(Model model) {
            // top5 크게보기
            List<MovieEntity> movie = movieService.findAll();

            // 현재 상영중인 영화 세로 포스터

            //movie chart

            List<String> star = new ArrayList<>() {{
                add("☆☆☆☆☆");
            }
        };

            model.addAttribute("star", star);
            List<MovieEntity> chart = movieService.findChart();
            model.addAttribute("charts", chart);
            model.addAttribute("movies", movie);
            model.addAttribute("movieInfos", movieService.findAll());
            return "index";
    }

    @GetMapping("/single")
    public String toSingle() {
        return "single";
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

}
