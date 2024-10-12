package com.nc13.moviemates.repository;

import com.nc13.moviemates.component.model.ReviewModel;
import com.nc13.moviemates.entity.ReviewEntity;
import com.nc13.moviemates.queryDsl.ReviewQueryDSL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long>, ReviewQueryDSL {
}
