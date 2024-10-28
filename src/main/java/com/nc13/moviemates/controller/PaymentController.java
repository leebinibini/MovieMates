package com.nc13.moviemates.controller;

import com.nc13.moviemates.entity.PaymentEntity;
import com.nc13.moviemates.service.PaymentService;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import io.jsonwebtoken.io.IOException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
@Controller
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService service;
    private IamportClient iamportClient;

    @Value("${iamport.apiKey}")
    private String apiKey;

    @Value("${iamport.secretKey}")
    private String secretKey;

    @PostConstruct
    public void init() {
        // API 키와 시크릿 키로 결제 클라이언트를 초기화하고 빈으로 등록
        this.iamportClient = new IamportClient(apiKey, secretKey);
    }
    @Transactional
    @PostMapping("/validation/{imp_uid}")
    public ResponseEntity<?> validatePayment(@PathVariable String imp_uid, @RequestBody Map<String, Object> requestData) {
        try {
            // Iamport API 결제 정보 조회
            IamportResponse<Payment> paymentResponse = iamportClient.paymentByImpUid(imp_uid);

            if (paymentResponse.getResponse() != null) {
                // 결제 상태 출력
                System.out.println("결제 상태: " + paymentResponse.getResponse().getStatus());
                System.out.println(requestData);
                if ("paid".equals(paymentResponse.getResponse().getStatus())) {
                    // 결제 성공
                    boolean saveResult = service.processPaymentAndSave(paymentResponse.getResponse(), requestData);
                    System.out.println("saveResult:" + saveResult);
                    System.out.println("결제 성공");
                    if (!saveResult) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("결제 정보 저장 실패");
                    }
                    return ResponseEntity.ok("결제 완료 및 검증 성공");
                } else {
                    // 결제 실패
                    System.out.println("결제 상태: " + paymentResponse.getResponse().getStatus());
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("결제 검증 실패: " + paymentResponse.getResponse().getStatus());
                }
            } else {
                // 결제 정보가 없을 경우
                System.out.println("결제 정보가 없습니다.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("결제 정보가 없습니다.");
            }
        } catch (IamportResponseException | IOException | java.io.IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류");
        }
    }

    @GetMapping()
    public ResponseEntity<List<?>> getList() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<PaymentEntity>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping()
    public ResponseEntity<Boolean> insert(@RequestBody PaymentEntity payment) {
        return ResponseEntity.ok(service.save(payment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteById(id));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }

    @GetMapping("/existsById/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable Long id) {
        return ResponseEntity.ok(service.existsById(id));
    }


}
