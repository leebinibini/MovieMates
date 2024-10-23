package com.nc13.moviemates.util;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class DbInit {
    private final JdbcTemplate jdbcTemplate;
@Transactional
    public void saveMovieTitle(String title) {
        String sql = "INSERT INTO movies (title) VALUES (?)";
        jdbcTemplate.update(sql, title);
    }

    @RequiredArgsConstructor
    @Service
    public static class MovieJsoup {
        private final DbInit dbService;
        String url = "https://megabox.co.kr/movie-detail?rpstMovieNo=24045600";

        public void crawling(String url) {
            try {
                // Jsoup을 사용하여 웹 페이지 로드
                Document doc = Jsoup.connect(url).get();

                // 크롤링 작업
                String titleSelector = "#contents > div.movie-detail-page > div.movie-detail-cont > p.title";
                Elements elements = doc.select(titleSelector);

                for (Element element : elements) {
                    String title = element.text().trim();

                    // 데이터베이스에 저장
                    dbService.saveMovieTitle(title);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
