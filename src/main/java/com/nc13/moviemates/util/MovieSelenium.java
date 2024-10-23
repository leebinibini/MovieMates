package com.nc13.moviemates.util;

import com.nc13.moviemates.component.model.MovieModel;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
@RequiredArgsConstructor
public class MovieSelenium {

    public List<MovieModel> crawlMovies(String url) {
        WebDriver driver = new ChromeDriver();
        driver.get(url);

        List<MovieModel> movies = new ArrayList<>();
        WebElement movieContainer = driver.findElement(By.cssSelector(".list_movieranking"));
        List<WebElement> movieLinks = movieContainer.findElements(By.cssSelector(".tit_item>a"));

        for (int i = 0; i < movieLinks.size(); i++) {
            String link = movieLinks.get(i).getAttribute("href");
            driver.get(link);

            // 페이지 로드 대기
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

            // 각종 정보 추출
            String title = driver.findElement(By.xpath("//*[@id=\"contents\"]/div[1]/div[4]/p[1]")).getText();
            String releaseDateStr = driver.findElement(By.xpath("//*[@id=\"contentData\"]/div[1]/div[3]/div/p[4]")).getText();
            String runningTime = driver.findElement(By.xpath("//*[@id=\"contentData\"]/div[1]/div[3]/div/p[2]")).getText();
            String information = driver.findElement(By.xpath("//*[@id=\"info\"]/div[1]")).getText();
            String director = driver.findElement(By.xpath("//*[@id=\"contentData\"]/div[1]/div[3]/div/p[1]")).getText();
            String genre = driver.findElement(By.xpath("//*[@id=\"contentData\"]/div[1]/div[3]/div/p[2]")).getText();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // 날짜 형식에 맞게 조정 필요
            Date releaseDate = null;
            try {
                releaseDate = dateFormat.parse(releaseDateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            // MovieDomain 객체 생성
            MovieModel movieModel = new MovieModel();
            movies.add(movieModel);

            driver.navigate().back();
        }

        // WebDriver 종료
        driver.quit();

        return movies;
    }
}

