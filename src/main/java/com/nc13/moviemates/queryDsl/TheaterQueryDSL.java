package com.nc13.moviemates.queryDsl;

import com.nc13.moviemates.component.model.TheaterModel;
import com.nc13.moviemates.entity.TheaterEntity;

import java.util.List;

public interface TheaterQueryDSL {
    List<TheaterEntity> getAll();
    Long findTheaterIdByName (String name);
    TheaterEntity getById(Long id);
    Boolean exists(Long id);
    Long getRowCount();
    Long deleteMany(List<Long> theaterIdList);
    void update(TheaterModel theaterModel);
    List<TheaterEntity> findByMovieId(Long movieId);
}
