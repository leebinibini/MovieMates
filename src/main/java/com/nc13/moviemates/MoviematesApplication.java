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
        Dotenv dotenv = Dotenv.load();

        // 환경 변수 설정, 없으면 기본값 또는 오류 메시지
        String iamportApiKey = dotenv.get("IAMPORT_API_KEY");
        String iamportSecretKey = dotenv.get("IAMPORT_SECRET_KEY");
        String googleClientSecret = dotenv.get("GOOGLE_CLIENT_SECRET");

        if (iamportApiKey == null || iamportSecretKey == null || googleClientSecret == null) {
            throw new IllegalArgumentException("환경 변수가 설정되지 않았습니다. .env 파일을 확인하세요.");
        }

        System.setProperty("IAMPORT_API_KEY", iamportApiKey);
        System.setProperty("IAMPORT_SECRET_KEY", iamportSecretKey);
        System.setProperty("GOOGLE_CLIENT_SECRET", googleClientSecret);

        // .env 파일 로드
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
