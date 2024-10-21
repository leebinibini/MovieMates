package com.nc13.moviemates.queryDslImpl;

import com.nc13.moviemates.entity.QMovieEntity;
import com.nc13.moviemates.entity.QReservationEntity;
import com.nc13.moviemates.entity.QScheduleEntity;
import com.nc13.moviemates.entity.ReservationEntity;
import com.nc13.moviemates.queryDsl.ReservationQueryDSL;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ReservationQueryDSLImpl implements ReservationQueryDSL {
    private final JPAQueryFactory jpaQueryFactory;
    private final QReservationEntity qReservation = QReservationEntity.reservationEntity;
    @Override
    public List<ReservationEntity> getAll() {
        return jpaQueryFactory.selectFrom(qReservation).fetch();
    }

    @Override
    public ReservationEntity getById(Long id) {
        throw new UnsupportedOperationException("UnImpleamentdeMethod'getById'");
    }

    @Override
    public Long getRowCount() {
        return jpaQueryFactory.select(qReservation.id.count()).from(qReservation).fetchOne();
    }

    @Override
    public Long deleteMany(List<Long> reservationIdList) {
        long deletedCount = jpaQueryFactory
                .delete(qReservation)
                .where(qReservation.id.in(reservationIdList))
                .execute();

        return deletedCount; // 삭제된 행의 수 반환
    }

    @Override
    public Boolean exists(Long id) {
        return jpaQueryFactory.selectFrom(qReservation).where(qReservation.id.eq(id)).fetchCount()>0;
    }

    @Override
    public boolean existsByUserIdAndMovieId(Long userId, Long movieId) {
        Long count = jpaQueryFactory
                .select(qReservation.count())
                .from(qReservation)
                .where(qReservation.userId.eq(userId)
                        .and(qReservation.movieId.eq(movieId)))
                .fetchOne();
        return count != null && count > 0;
    }

    @Override
    public List<Map<String, Object>> findReservationWithMovieByUserId(Long userId) {
        QMovieEntity qMovie = QMovieEntity.movieEntity;

        List<Tuple> reservationsWithMovies = jpaQueryFactory
                .select(qReservation.id, qReservation.userId, qReservation.scheduleId, qReservation.reservationDate, qReservation.seatId,
                        qReservation.paymentId, qReservation.ticketPrice, qReservation.status, qReservation.movieId,
                        qMovie.title, qMovie.lengthPosterUrl, qMovie.plot)
                .from(qReservation)
                .join(qMovie).on(qReservation.movieId.eq(qMovie.id))
                .where(qReservation.userId.eq(userId))// movieId로 조인
                .fetch();  // 리스트로 결과 반환

        return reservationsWithMovies.stream()
                .map(tuple -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("reservationId", tuple.get(QReservationEntity.reservationEntity.id));
                    map.put("userId", tuple.get(QReservationEntity.reservationEntity.userId));
                    map.put("scheduleId", tuple.get(QReservationEntity.reservationEntity.scheduleId));
                    map.put("reservationDate", tuple.get(QReservationEntity.reservationEntity.reservationDate));
                    map.put("seatId", tuple.get(QReservationEntity.reservationEntity.seatId));
                    map.put("paymentId", tuple.get(QReservationEntity.reservationEntity.paymentId));
                    map.put("ticketPrice", tuple.get(QReservationEntity.reservationEntity.ticketPrice));
                    map.put("status", tuple.get(QReservationEntity.reservationEntity.status));
                    map.put("movieId", tuple.get(QReservationEntity.reservationEntity.movieId));
                    map.put("movieTitle", tuple.get(QMovieEntity.movieEntity.title));
                    map.put("moviePosterUrl", tuple.get(QMovieEntity.movieEntity.lengthPosterUrl));
                    map.put("plot", tuple.get(QMovieEntity.movieEntity.plot));
                    return map;
                })
                .collect(Collectors.toList());
    }


    public List<Map<String, Object>> findReservationWithScheduleByUserId(Long userId){
        QScheduleEntity qSchedule = QScheduleEntity.scheduleEntity;

        List<Tuple> reservationsWithMovies = jpaQueryFactory
                .select(qReservation.id, qReservation.userId, qReservation.scheduleId, qReservation.reservationDate, qReservation.seatId,
                        qReservation.paymentId, qReservation.ticketPrice, qReservation.status, qReservation.movieId,
                        qSchedule.showDate, qSchedule.showTime)
                .from(qReservation)
                .join(qSchedule).on(qReservation.scheduleId.eq(qSchedule.id))
                .where(qReservation.userId.eq(userId))// movieId로 조인
                .fetch();  // 리스트로 결과 반환

        return reservationsWithMovies.stream()
                .map(tuple -> {
                    Map<String, Object> map2 = new HashMap<>();
                    map2.put("reservationId", tuple.get(QReservationEntity.reservationEntity.id));
                    map2.put("userId", tuple.get(QReservationEntity.reservationEntity.userId));
                    map2.put("scheduleId", tuple.get(QReservationEntity.reservationEntity.scheduleId));
                    map2.put("reservationDate", tuple.get(QReservationEntity.reservationEntity.reservationDate));
                    map2.put("seatId", tuple.get(QReservationEntity.reservationEntity.seatId));
                    map2.put("paymentId", tuple.get(QReservationEntity.reservationEntity.paymentId));
                    map2.put("ticketPrice", tuple.get(QReservationEntity.reservationEntity.ticketPrice));
                    map2.put("status", tuple.get(QReservationEntity.reservationEntity.status));
                    map2.put("movieId", tuple.get(QReservationEntity.reservationEntity.movieId));
                    map2.put("showDate", tuple.get(QScheduleEntity.scheduleEntity.showDate));
                    map2.put("showTime", tuple.get(QScheduleEntity.scheduleEntity.showTime));
                    return map2;
                })
                .collect(Collectors.toList());
    }


}
