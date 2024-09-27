package com.nc13.moviemates.controller;

import com.nc13.moviemates.entity.MovieEntity;
import com.nc13.moviemates.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final MovieService movieService;

    //홈페이지 화면 가져오기
    @GetMapping("/")
    public String home(Model model) {
            List<MovieEntity> movie = movieService.findAll();
            model.addAttribute("movies", movie);

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
