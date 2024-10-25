package com.nc13.moviemates.controller;

import com.nc13.moviemates.util.WebCrawlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@RequiredArgsConstructor
@Controller
@RequestMapping("/api/crawl")
public class WebCrawlController {
    @Autowired
    private final WebCrawlerService webCrawlerService;

//    @Autowired
//    public WebCrawlController(WebCrawlerService webCrawlerService){
//        this.webCrawlerService = webCrawlerService;
//    }

//    @GetMapping("")
//    public List<String> crawl(){
//        return webCrawlerService.crawl();
//    }
}
