package com.nc13.moviemates.controller;

import com.nc13.moviemates.entity.MovieEntity;
import com.nc13.moviemates.entity.PosterEntity;
import com.nc13.moviemates.service.MovieService;
import com.nc13.moviemates.service.PosterService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.ast.tree.expression.Star;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HomeController {

    private final MovieService movieService;
    private final PosterService posterService;

    //홈페이지 화면 가져오기
        @GetMapping("/")
        public String home(Model model, HttpServletRequest request) {
            log.info("top5 크게보기");
            // top5 크게보기
            List<MovieEntity> movie = movieService.findAll();

            log.info("현재 상영중인 영화 세로 포스터");
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


}
