package com.nc13.moviemates;

import com.nc13.moviemates.component.proxy.WebCrawlerService;
import com.nc13.moviemates.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
@SpringBootApplication
public class MoviematesApplication {
    private MovieService movieService;

    public static void main(String[] args) {
        SpringApplication.run(MoviematesApplication.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return args -> {
            // 애플리케이션 시작 시 크롤링 및 데이터베이스 저장 실행
            String url = "https://megabox.co.kr/";
            crawling(url);
        };
    }

    private void crawling(String url) {
    }


}
