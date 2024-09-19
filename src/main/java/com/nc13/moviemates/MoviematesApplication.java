package com.nc13.moviemates;

import com.nc13.moviemates.Scraping.DbService;
import com.nc13.moviemates.Scraping.MovieJsoup;
import com.nc13.moviemates.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor
@SpringBootApplication
public class MoviematesApplication {
    private MovieService movieService;

    @Autowired
    public void MovieApplication(MovieService movieService) {
        this.movieService = movieService;
    }

//    public void run() {
//        String url = "https://megabox.co.kr/";
//        movieService.crawlAndSaveMovies(url);
//    }
    public static void main(String[] args) {
        SpringApplication.run(MoviematesApplication.class, args);
    }
}
