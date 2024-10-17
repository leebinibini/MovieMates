package com.nc13.moviemates.serviceImpl;

import com.nc13.moviemates.entity.PaymentEntity;
import com.nc13.moviemates.entity.ReservationEntity;
import com.nc13.moviemates.repository.PaymentRepository;
import com.nc13.moviemates.repository.ReservationRepository;
import com.nc13.moviemates.service.PaymentService;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository repository;
    private final ReservationRepository reservationRepository;

    @Override
    public List<?> findAll() {
        return repository.findAll();
    }

    @Override
                public Optional<PaymentEntity> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Boolean save(PaymentEntity payment) {
        PaymentEntity ent = repository.save(payment);
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

    @Override
    public Boolean processPaymentAndSave(Payment paymentResponse, Map<String, Object> requestData) {
        try {
            // requestData에서 필요한 정보 추출
            String movie = requestData.get("movie").toString();
            Long scheduleId = Long.valueOf(requestData.get("scheduleId").toString());
            Long seatId = Long.valueOf(requestData.get("seatId").toString());
            Long userId = Long.valueOf(requestData.get("userId").toString());
            String location = requestData.get("location").toString();

            // PaymentEntity 생성 및 데이터 설정
            PaymentEntity paymentEntity = PaymentEntity.builder()
                    .userId(userId)
                    .seatId(seatId)
                    .movie(movie)
                    .amount(paymentResponse.getAmount().intValue())
                    .paymentMethod(paymentResponse.getPayMethod())
                    .paymentStatus(paymentResponse.getStatus())
                    .impUid(paymentResponse.getImpUid())
                    .merchantUid(paymentResponse.getMerchantUid())
                    .buyerName(paymentResponse.getBuyerName())
                    .location(location)
                    .paymentDate(new Date())
                    .build();
            // 결제 정보 저장
            repository.save(paymentEntity);
            // 결제 정보 저장

            ReservationEntity reservationEntity = ReservationEntity.builder()
                    .userId(userId)
                    .scheduleId(scheduleId)
                    .seatId(seatId)
                    .paymentId(paymentEntity.getId())
                    .ticketPrice(paymentResponse.getAmount().intValue())
                    .reservationDate(LocalDateTime.now())// 예약 날짜 현재 시간으로 설정
                    .status(paymentResponse.getStatus())
                    .build();

// 예약 정보 저장
            reservationRepository.save(reservationEntity);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}



