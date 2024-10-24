package com.nc13.moviemates.controller;

import com.nc13.moviemates.component.model.ReservationModel;
import com.nc13.moviemates.entity.HistoryEntity;
import com.nc13.moviemates.entity.ReservationEntity;
import com.nc13.moviemates.entity.UserEntity;
import com.nc13.moviemates.service.HistoryService;
import com.nc13.moviemates.service.MovieService;
import com.nc13.moviemates.entity.UserEntity;
import com.nc13.moviemates.service.ReservationService;
import com.nc13.moviemates.service.UserService;
import com.querydsl.core.Tuple;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.RegEx;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationController {
    private final ReservationService service;
    private final UserService userService;

    @GetMapping(("/list/{userId}"))
    public String getReservationWithMovieForUser(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserEntity  loginUser  = (UserEntity) session.getAttribute("loginUser");
        if (loginUser == null || loginUser.getId() == null) {
            model.addAttribute("errorMessage", "User not logged in");
            return "error";  // 로그인하지 않은 경우 에러 페이지로 이동
        }

        Long userId = loginUser.getId();
        System.out.println("예약:"+loginUser );
        System.out.println(loginUser.getId());
        Optional<UserEntity> optionalUser = userService.findById(userId);
        if (optionalUser.isPresent()) {
            model.addAttribute("user", optionalUser.get());
        } else {
            model.addAttribute("errorMessage", "User not found");
        }
        // Reservation Movie 정보 조회
        List<Map<String, Object>> reservationMovies = service.findReservationWithMovieByUserId(userId);

        // Reservation Schedule 정보 조회
        List<Map<String, Object>> reservationSchedules = service.findReservationWithScheduleByUserId(userId);

        // 날짜 및 시간 포맷터 설정
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        // 각 예약 스케줄에 대해 포맷팅 작업
        reservationSchedules.forEach(schedule -> {
            LocalDate showDate = (LocalDate) schedule.get("showDate");
            LocalTime showTime = (LocalTime) schedule.get("showTime");

            if (showDate != null) {
                schedule.put("formattedShowDate", showDate.format(dateFormatter));
            }
            if (showTime != null) {
                schedule.put("formattedShowTime", showTime.format(timeFormatter));
            }
        });
        model.addAttribute("reservationMovie", service.findReservationWithMovieByUserId(userId));
        model.addAttribute("reservationSchedule", service.findReservationWithScheduleByUserId(userId));
        return "profile/reservation";
        }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ReservationEntity>> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @ResponseBody
    @PostMapping("/register")
    public ResponseEntity<Boolean> insert(@RequestBody ReservationModel reservation) {
        System.out.println("컨트롤러 진입");
        return ResponseEntity.ok(service.save(reservation));
    }

    @PutMapping
    public ResponseEntity<Boolean> update(@RequestBody ReservationModel reservation) {
        return ResponseEntity.ok(service.save(reservation));
    }

    @ResponseBody
    @PostMapping("/updateMany")
    public ResponseEntity<Boolean> updateByJspreadSheet(@RequestBody List<ReservationModel> reservationList) {
        System.out.println("예매 정보 수정 컨트롤러 진입 성공!");
        System.out.println("예매리스트" + reservationList);
        return ResponseEntity.ok(service.update(reservationList));
    }

    @ResponseBody
    @PostMapping("/deleteMany")
    public ResponseEntity<Long> deleteMany(@RequestBody List<Long> reservationIdList) {
        return ResponseEntity.ok(service.deleteMany(reservationIdList));
    }

    @GetMapping("/iamport")
    public String goPayment(){
        return "iamport";
    }

    @DeleteMapping("/cancel/{id}")
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
