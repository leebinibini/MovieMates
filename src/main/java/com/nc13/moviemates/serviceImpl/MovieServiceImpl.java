package com.nc13.moviemates.serviceImpl;

import com.nc13.moviemates.component.proxy.MovieSelenium;
import com.nc13.moviemates.component.model.MovieModel;
import com.nc13.moviemates.entity.MovieEntity;
import com.nc13.moviemates.repository.MovieRepository;
import com.nc13.moviemates.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository repository;
    private final MovieSelenium movieSelenium;

    @Override
    public List<MovieEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Boolean save(MovieEntity movie) {
       MovieEntity ent = repository.save(movie);
       Long id = ent.getId();
       return existsById(id);
    }

    @Override
    public Optional<MovieEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public Boolean deleteById(Long id) {
        repository.deleteById(id);
        return !existsById(id);
    }

    @Override
    public void crawlMovies() throws IOException {

//        // 영화 목록에서 각 영화의 상세 페이지로 이동
//        List<WebElement> movies = driver.findElements(By.className("movie-item"));
//
//        for (WebElement movieElement : movies) {
//            // 영화 상세 페이지로 이동
//            String movieUrl = movieElement.findElement(By.tagName("a")).getAttribute("href");
//            driver.get(movieUrl);
//
//            // Jsoup으로 HTML 파싱
//            Document doc = Jsoup.connect(movieUrl).get();
//
//            String title = doc.select("h1.title").text();
//            String genre = doc.select(".genre").text();
//            String director = doc.select(".director").text();
//            String actors = doc.select(".actors").text();
//            String plot = doc.select(".plot").text();
//            String releaseDateText = doc.select(".release-date").text();
//            LocalDate releaseDate = LocalDate.parse(releaseDateText);
//            int runningTime = Integer.parseInt(doc.select(".running-time").text());
//            String posterUrl = doc.select(".poster img").attr("src");

        System.out.println("Hello world!");

        String url = "https://megabox.co.kr/";
        // Selenium 설정 (ChromeDriver 경로 필요)
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\qpdjv\\Downloads\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.megabox.co.kr/movie");

        List<MovieEntity> movieList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            // XPath를 동적으로 변경하여 li[i] 요소를 가져옴

            //title
            String titleXpath = "//*[@id='movieList']/li[" + i + "]/div[2]/p[2]";
            WebElement title = driver.findElement(By.xpath(titleXpath));
            String movieText = title.getText();
            // 영화 제목 출력
            System.out.println("영화 제목 " + i + ": " + title.getText());

            // poster URL
            String posterXpath = "//*[@id='movieList']/li[" + i + "]/div[1]/img";
            WebElement poster = driver.findElement(By.xpath(posterXpath));
            String posterUrl = poster.getAttribute("src"); // 포스터 URL을 가져옴
            System.out.println("포스터 " + i + "의 URL: " + posterUrl);

             //줄거리 (예: 링크 또는 상세 설명 등)
            String plotXpath = "//*[@id='movieList']/li[" + i + "]/div[1]/div[3]/a/div[1]";
            WebElement plot = driver.findElement(By.xpath(plotXpath));
            String plotText = plot.getText();
            System.out.println("추가 정보 " + i + ": " + plotText);

            //평점 //*[@id="movieList"]/li[1]/div[1]/div[3]/a/div[2]/div
           /* String rateXpath = "//*[@id='movieList']/li[" + i + "]/div[1]/div[3]/a/div[2]/div;
            WebElement rate = driver.findElement(By.xpath(rateXpath));
            System.out.println("평점 " + i + ": " + rate.getText());
            String rateText = rate.getText().replaceAll("[^0-9.]", ""); // 숫자와 소수점만 남기기
            System.out.println("평점 " + i + ": " + rateText);
            try {
                // 문자열을 double로 변환
                double rateValue = Double.parseDouble(rateText);
                System.out.println("평점 " + i + ": " + rateValue);
            } catch (NumberFormatException e) {
                System.out.println("평점 변환에 실패했습니다: " + rateText);
            }
*/


            MovieEntity movie = MovieEntity.builder()
                    .title(movieText)
                    .genre(null)
                    .director(null)
                    .actors(null)
                    .plot(plotText)
                    .releaseDate(null)
                    .runningTime(120)
                    .posterUrl(posterUrl)
                    //.rate(Double.parseDouble(rateText))
                    .build();

            movieList.add(movie);
            repository.saveAll(movieList);

            for (MovieEntity movie2 : movieList) {
                System.out.println("저장된 영화 제목: " + movie2.getTitle());
                System.out.println("저장된 영화 포스터" + movie2.getPosterUrl());
            }
        }
        driver.quit();
    }
    }

