package com.nc13.moviemates.serviceImpl;

import com.nc13.moviemates.component.model.ReservationModel;
import com.nc13.moviemates.entity.MovieEntity;
import com.nc13.moviemates.entity.ReservationEntity;
import com.nc13.moviemates.repository.ReservationRepository;
import com.nc13.moviemates.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository repository;

    @Override
    public List<ReservationEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<ReservationEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Boolean save(ReservationModel reservation) {
        ReservationEntity ent = ReservationEntity.builder()
                .scheduleId(reservation.getScheduleId())
                .paymentId(reservation.getPaymentId())
                .reservationDate(reservation.getReservationDate())
                .seatNumber(reservation.getSeatNumber())
                .ticketPrice(reservation.getTicketPrice())
                .userId(reservation.getUserId())
                .build();
        repository.save(ent);

        Long id = ent.getId();
        return existsById(id);
    }

    @Override
    public Boolean deleteById(Long id) {
        repository.deleteById(id);
        return !existsById(id);
    }

    @Override
    public Long count() {
        return repository.count();
    }

    @Override
    public Boolean existsById(Long id) {
        return repository.existsById(id);
    }

    @Transactional
    @Override
    public Boolean update(List<ReservationModel> reservationList) {
        reservationList.forEach(reservation -> {
            System.out.println("예매서비스 진입 성공!");
            LocalDateTime reservationDateTime;

            // 만약 reservationDate가 LocalDate 또는 String 형태로 온다면 이를 LocalDateTime으로 변환
            if (reservation.getReservationDate().toString().length() == 10) {  // "yyyy-MM-dd" 형식
                LocalDate date = LocalDate.parse(reservation.getReservationDate().toString());
                reservationDateTime = date.atTime(LocalTime.now());  // 00:00:00 기본 시간 추가
            } else {
                // 이미 LocalDateTime 형식일 경우 그대로 사용
                reservationDateTime = reservation.getReservationDate();
            }

            ReservationEntity ent = ReservationEntity.builder()
                    .id(reservation.getId())
                    .userId(reservation.getUserId())
                    .scheduleId(reservation.getScheduleId())
                    .reservationDate(reservationDateTime)
                    .seatNumber(reservation.getSeatNumber())
                    .paymentId(reservation.getPaymentId())
                    .ticketPrice(reservation.getTicketPrice())
                    .build();

            repository.save(ent);
        });
        return true;
    }

    @Transactional
    @Override
    public Long deleteMany(List<Long> reservationIdList) {
        return repository.deleteMany(reservationIdList);
    }
}
