package com.nc13.moviemates.controller;

import com.nc13.moviemates.entity.MovieEntity;
import com.nc13.moviemates.entity.PosterEntity;
import com.nc13.moviemates.service.MovieService;
import com.nc13.moviemates.service.PosterService;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
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

            // 장르 , 기준으로 잘라서 보이기
            List<String> genreList = new ArrayList<>();
            for (MovieEntity movieEntity : movie) {
                // 먼저 장르가 null인지 확인
                if (movieEntity.getGenre() != null) {
                    // null이 아니라면 ,로 분리하여 리스트에 추가
                    String[] genres = movieEntity.getGenre().split(",");
                    genreList.addAll(Arrays.asList(genres));
                }
            }
            // 현재 상영중인 영화 세로 포스터
            List<PosterEntity> poster = posterService.findAll();

           //movie chart
            List<MovieEntity> chart = movieService.findChart();
            System.out.println(chart);
            model.addAttribute("charts", chart);
            model.addAttribute("posters", poster);
            model.addAttribute("movies", movie);
            model.addAttribute("genres", genreList);
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
