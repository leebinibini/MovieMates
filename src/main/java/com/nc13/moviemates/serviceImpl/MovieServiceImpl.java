package com.nc13.moviemates.serviceImpl;

import com.nc13.moviemates.util.MovieSelenium;
import com.nc13.moviemates.component.model.MovieModel;
import com.nc13.moviemates.entity.MovieEntity;
import com.nc13.moviemates.repository.MovieRepository;
import com.nc13.moviemates.repository.WishRepository;
import com.nc13.moviemates.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository repository;
    private final MovieSelenium movieSelenium;
    private final WishRepository wishRepository;

    @Override
    public List<MovieEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public List<MovieEntity> findChart() {
        return repository.findChart();
    }

    @Override
    public Long save(MovieModel movie) {
        MovieEntity ent = MovieEntity.builder()
                .title(movie.getTitle())
                .widthPosterUrl(movie.getWidthPosterUrl())
                .lengthPosterUrl(movie.getPosterUrl())
                .posterUrl(movie.getPosterUrl())
                .genre(movie.getGenre())
                .director(movie.getDirector())
                .plot(movie.getPlot())
                .runningTime(movie.getRunningTime())
                .rate(movie.getRate())
                .releaseDate(movie.getReleaseDate())
                .actors(movie.getActors())
                .ageClass(movie.getAgeClass())
                .build();

        MovieEntity savedEntity = repository.save(ent);

        long id = savedEntity.getId();
        return existsById(id)? id : 0;
    }

    @Override
    @Transactional
    public Boolean update(List<MovieModel> movieList) {
        System.out.println("영화서비스 진입 성공!");
        movieList.forEach(movieModel -> {
            MovieEntity movieEntity = MovieEntity.builder()
                    .id(movieModel.getId())
                    .title(movieModel.getTitle())
                    .genre(movieModel.getGenre())
                    .plot(movieModel.getPlot())
                    .releaseDate(movieModel.getReleaseDate())
                    .director(movieModel.getDirector())
                    .runningTime(movieModel.getRunningTime())
                    .actors(movieModel.getActors())
                    .lengthPosterUrl(movieModel.getLengthPosterUrl())
                    .widthPosterUrl(movieModel.getWidthPosterUrl())
                    .rate(movieModel.getRate())
                    .ageClass(movieModel.getAgeClass())
                    .build(); // 변환 메서드 호출
            repository.save(movieEntity);
        });
        return true;
    }


    @Override
    public List<String> getNowPlayingList() {
        return repository.getNowPlayingList();
    }

    /*@Override
    public Boolean save(MovieEntity movie) {
       MovieEntity ent = repository.save(movie);
       Long id = ent.getId();
       return existsById(id);
    }*/

    @Override
    public Optional<MovieModel> findById(Long id) {
        Optional<MovieEntity> ent = repository.findById(id);

        // Optional의 값이 존재하면 MovieModel로 변환하고, 그렇지 않으면 빈 Optional을 반환
        return ent.map(movieEntity -> MovieModel.builder()
                .id(movieEntity.getId())
                .genre(movieEntity.getGenre())
                .title(movieEntity.getTitle())
                .director(movieEntity.getDirector())
                .rate(movieEntity.getRate())
                .runningTime(movieEntity.getRunningTime())
                .posterUrl(movieEntity.getPosterUrl())
                .lengthPosterUrl(movieEntity.getLengthPosterUrl())
                .widthPosterUrl(movieEntity.getWidthPosterUrl())
                .build());
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
    @Transactional
    public Long deleteMany(List<Long> movieIdList) {
        return repository.deleteMany(movieIdList);
    }

    @Override
    public Boolean deleteById(Long id) {
        repository.deleteById(id);
        return !existsById(id);
    }

    @Override
    public Long findMovieIdByName(String name) {
        return repository.findMovieIdByName(name);
    }


    @Override
    public List<MovieEntity> getWatchedMoviesByUserId(Long userId) {
        return List.of();
    }

    @Override
    public MovieEntity findEntityById(Long id) {
        return repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Movie not Found with id: " +id));
    }

    // 변환 메서드
    private MovieEntity modelToEntity(MovieModel movieModel) {
        return MovieEntity.builder()
                .id(movieModel.getId())
                .genre(movieModel.getGenre())
                .title(movieModel.getTitle())
                .director(movieModel.getDirector())
                .rate(movieModel.getRate())
                .runningTime(movieModel.getRunningTime())
                .posterUrl(movieModel.getPosterUrl())
                .lengthPosterUrl(movieModel.getLengthPosterUrl())
                .widthPosterUrl(movieModel.getWidthPosterUrl())
                // 필요한 필드를 변환
                .build();
    }

    @Override
    public Page<MovieModel> findSearchList(String searchStr, Pageable pageable) {
        // MovieEntity 리스트를 조회
        Page<MovieEntity> movieEntities = repository.findByTitleContaining(searchStr, pageable);

        // MovieEntity를 MovieModel로 변환
        List<MovieModel> movieModels = movieEntities
                .stream()
                .map(this::convertToModel) // 변환 메서드 호출
                .collect(Collectors.toList());

        // Page 객체로 변환
        return new PageImpl<>(movieModels, pageable, movieEntities.getTotalElements());
    }

    // 엔티티를 모델로 변환하는 메서드
    private MovieModel convertToModel(MovieEntity movieEntity) {
        return MovieModel.builder()
                .id(movieEntity.getId())
                .title(movieEntity.getTitle())
                .director(movieEntity.getDirector())
                .posterUrl(movieEntity.getPosterUrl())
                .genre(movieEntity.getGenre())
                .plot(movieEntity.getPlot())
                .releaseDate(movieEntity.getReleaseDate())
                .ageClass(movieEntity.getAgeClass())
                .rate(movieEntity.getRate())
                .build();
    }


//   @Override
//    public void crawlMovies() throws IOException {
//
//        System.out.println("Hello world!");
//
//
//        // Selenium 설정 (ChromeDriver 경로 필요)
//        System.setProperty("webdriver.chrome.driver", "C:\\Users\\qpdjv\\Downloads\\chromedriver.exe");
//        WebDriver driver = new ChromeDriver();
//        driver.get("https://www.megabox.co.kr/movie");
//
//        List<MovieEntity> movieList = new ArrayList<>();
//        for (int i = 1; i <= 10; i++) {
//            // 영화 제목 가져오기
//            String titleXpath = "//*[@id='movieList']/li[" + i + "]/div[2]/p[2]";
//            WebElement titleElement = driver.findElement(By.xpath(titleXpath));
//            String movieText = titleElement.getText();
//            System.out.println("영화 제목 " + i + ": " + movieText);
//
//            // 고유 ID (rpstMovieNo) 가져오기
//            String movieIdXpath = "//*[@id='movieList']/li[" + i + "]/@data-movie-id"; // 고유 ID가 있는지 확인해야 합니다.
//            WebElement movieIdElement = driver.findElement(By.xpath(movieIdXpath));
//            String movieId = movieIdElement.getAttribute("data-movie-id");  // 이 부분은 실제 속성을 확인해야 함.
//            System.out.println("영화 ID " + i + ": " + movieId);
//
//            // 상세 페이지 URL 구성
//            String detailPageUrl = "https://www.megabox.co.kr/movie-detail?rpstMovieNo=" + movieId;
//            driver.get(detailPageUrl);  // 상세 페이지로 이동
//
//            // 상세 페이지에서 plot 정보 가져오기
//            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"info\"]/div[1]")));  // plot 요소가 나타날 때까지 대기
//
//            WebElement plotElement = driver.findElement(By.xpath("//*[@id=\"info\"]/div[1]"));
//            String plotText = plotElement.getText().trim().replaceAll("[^\\p{Print}]", "");
//            System.out.println("영화 " + i + " 줄거리: " + plotText);
//
//            // 다시 목록 페이지로 돌아가기
//            driver.navigate().back();
//
//            // MovieEntity에 저장
//            MovieEntity movie = MovieEntity.builder()
//                    .title(movieText)
//                    .plot(plotText)
//                    // 다른 정보도 추가
//                    .build();
//
//            movieList.add(movie);
//        }
//
////  movieList를 데이터베이스에 저장
//        repository.saveAll(movieList);
//
//        driver.quit();
//
//// movieList를 데이터베이스에 저장
//        repository.saveAll(movieList);
//
//        driver.quit();
//        System.setProperty("webdriver.chrome.driver", "C:\\Users\\qpdjv\\Downloads\\chromedriver.exe");
//        WebDriver driver = new ChromeDriver();
//        driver.get("https://www.megabox.co.kr/movie");
//
//        WebDriver driverDetail1 = new ChromeDriver();
//        driverDetail1.get("https://www.megabox.co.kr/movie-detail?rpstMovieNo=24045600");
//
//        List<MovieEntity> movieList = new ArrayList<>();
//        for (int i = 1; i <= 10; i++) {
//            // XPath를 동적으로 변경하여 li[i] 요소를 가져옴
//
//            //plot
//            String plotXpath = "//*[@id=\"info\"]/div[1]";
//            WebElement plot = driverDetail1.findElement(By.xpath(plotXpath));
//            String plotText = plot.getText().trim().replaceAll("[^\\p{Print}]", "");
//            // 영화 제목 출력
//            System.out.println("영화  " + i + ": " + plot.getText());
//
//            String plotXpath = "//*[@id='movieList']/li[" + i + "]/div[1]/div[3]/a/div[1]";
//            WebElement plot = driver.findElement(By.xpath(plotXpath));
//            String plotText = plot.getText();
//            System.out.println("추가 정보 " + i + ": " + plotText);
//
//            //title
//            String titleXpath = "//*[@id='movieList']/li[" + i + "]/div[2]/p[2]";
//            WebElement title = driver.findElement(By.xpath(titleXpath));
//            String movieText = title.getText();
//            // 영화 제목 출력
//            System.out.println("영화 제목 " + i + ": " + title.getText());
//
//            // poster URL
//
//            String posterXpath = "//*[@id='movieList']/li[" + i + "]/div[1]/img";
//            WebElement poster = driver.findElement(By.xpath(posterXpath));
//            String posterUrl = poster.getAttribute("src"); // 포스터 URL을 가져옴
//            System.out.println("포스터 " + i + "의 URL: " + posterUrl);
//
//
//
//            //개봉일자
//            String releaseDateXpath = "//*[@id='movieList']/li[" + i + "]/div[3]/span[2]";
//            WebElement releaseDate = driver.findElement(By.xpath(releaseDateXpath));
//            String releaseDateText = releaseDate.getText();
//            System.out.println("추가 정보 " + i + ": " + releaseDateText);
//
//            //연령제한
//            //*[@id="movieList"]/li[1]/div[2]/p[1]
//            String ageClassXpath = "//*[@id='movieList']/li[" + i + "]/div[2]/p[1]";
//            WebElement ageClass = driver.findElement(By.xpath(ageClassXpath));
//            String ageClassText = ageClass.getText();
//            System.out.println("연령등급 " + i + ": " + ageClassText);
//
//
//
//            //평점 //*[@id="movieList"]/li[1]/div[1]/div[3]/a/div[2]/div
//            String rateXpath = "//*[@id='movieList']/li[" + i + "]/div[1]/div[3]/a/div[2]/div";
//            WebElement rate = driver.findElement(By.xpath(rateXpath));
//            System.out.println("평점 " + i + ": " + rate.getText());
//            String rateText = rate.getText().replaceAll("[^0-9.]", ""); // 숫자와 소수점만 남기기
//            System.out.println("평점 " + i + ": " + rateText);
//
//            try {
//                // 문자열을 double로 변환
//                double rateValue = Double.parseDouble(rateText);
//                System.out.println("평점 " + i + ": " + rateValue);
//            } catch (NumberFormatException e) {
//                System.out.println("평점 변환에 실패했습니다: " + rateText);
//            }
//
//
//            MovieEntity movie = MovieEntity.builder()
//                    .title(movieText)
//                    .genre(null)
//                    .director(null)
//                    .actors(null)
//                    .plot(plotText)
//                    .releaseDate(releaseDateText)
//                    .runningTime(120)
//                    .posterUrl(posterUrl)
//                    .ageClass(ageClassText)
//                 //   .rate(Double.parseDouble(rateText))
//                    .build();
//
//            movieList.add(movie);
//            repository.saveAll(movieList);
//
//            for (MovieEntity movie2 : movieList) {
//                System.out.println("저장된 영화 제목: " + movie2.getTitle());
//                System.out.println("plot:"+movie2.getPlot());
//            }
//        }
//        driver.quit();
//    }
}

