package com.nc13.moviemates.Scraping;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DbService {
    private final JdbcTemplate jdbcTemplate;
@Transactional
    public void saveMovieTitle(String title) {
        System.out.println("무비저장");
        String sql = "INSERT INTO movies (title) VALUES (?)";
        jdbcTemplate.update(sql, title);
    }
}
