package com.nc13.moviemates.Scraping;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Iterator;
@RequiredArgsConstructor
@Service
public class MovieJsoup {
    private final DbService dbService;
    String url = "https://megabox.co.kr/movie-detail?rpstMovieNo=24045600";

    public void crawling() {
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



