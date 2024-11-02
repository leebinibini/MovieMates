package com.nc13.moviemates;

import com.nc13.moviemates.service.MovieService;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor
@SpringBootApplication
public class MoviematesApplication {
    private MovieService movieService;

    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.load(); // .env 파일 로드
        System.setProperty("AWS_ACCESS_KEY", dotenv.get("AWS_ACCESS_KEY"));
        System.setProperty("AWS_SECRET_KEY", dotenv.get("AWS_SECRET_KEY"));
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
