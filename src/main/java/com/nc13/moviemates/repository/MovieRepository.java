package com.nc13.moviemates.repository;

import com.nc13.moviemates.entity.MovieEntity;
import com.nc13.moviemates.queryDsl.MovieQueryDSL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<MovieEntity, Long>, MovieQueryDSL {

    @Query("SELECT m.title FROM MovieEntity m")
    List<String> getNowPlayingList();

}
