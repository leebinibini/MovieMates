package com.nc13.moviemates.queryDsl;

import com.nc13.moviemates.entity.ReservationEntity;
import com.querydsl.core.Tuple;

import java.util.List;
import java.util.Map;

public interface ReservationQueryDSL {
    List<ReservationEntity> getAll();
    ReservationEntity getById(Long id);
    Boolean exists(Long id);
    Long getRowCount();
    boolean existsByUserIdAndMovieId(Long userId, Long movieId);
    Long deleteMany(List<Long> reservationIdList);
    List<Map<String, Object>> findReservationWithMovieByUserId(Long userId);
    List<Map<String, Object>> findReservationWithScheduleByUserId(Long userId);

}
