package com.nc13.moviemates;

import com.nc13.moviemates.component.proxy.WebCrawlerService;
import com.nc13.moviemates.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
@SpringBootApplication
public class MoviematesApplication {
    private MovieService movieService;

    public static void main(String[] args) {
        SpringApplication.run(MoviematesApplication.class, args);
    }

    // CommandLineRunner로 크롤링 작업 자동 실행
    /*@Component
    @RequiredArgsConstructor
    class MovieCrawlerRunner implements CommandLineRunner {

        private final MovieService movieService;

        @Override
        public void run(String... args) throws Exception {

            movieService.crawlMovies();  // 크롤링 실행
        }
    }*/
}
