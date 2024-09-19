package com.nc13.moviemates.controller;

import com.nc13.moviemates.service.impl.WebCrawlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/crawl")
public class WebCrawlController {
    @Autowired
    private final WebCrawlerService webCrawlerService;

//    @Autowired
//    public WebCrawlController(WebCrawlerService webCrawlerService){
//        this.webCrawlerService = webCrawlerService;
//    }

    @GetMapping("")
    public List<String> crawl(){
        return webCrawlerService.crawl();
    }
}
