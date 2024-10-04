package com.nc13.moviemates.controller;

import com.nc13.moviemates.entity.HistoryEntity;
import com.nc13.moviemates.entity.UserEntity;
import com.nc13.moviemates.service.HistoryService;
import com.nc13.moviemates.service.UserService;
import com.nc13.moviemates.serviceImpl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserServiceImpl service;


    private final HistoryService historyService;

    @GetMapping("/mypage")
    public String getList(Model model){
        List<HistoryEntity> histories = historyService.findAll();
        List<String> titles = new ArrayList<>();

        for(int i=0; i<histories.size(); i++){
            //System.out.println("히스토리 제목: "+histories.get(i).getTitle());
        }
        model.addAttribute("histories", histories);


        return "profile/main";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }


    @GetMapping("/list")
    public ResponseEntity<List<?>> getList() {
        return ResponseEntity.ok(service.findAll());
    }

    /*@GetMapping("/mypage")
    public String mypage3() {

        System.out.println("llllllllllllllllllllllllllllllll");
        return "profile/main";
    }*/

    @GetMapping("/details/{id}")
    public ResponseEntity<Optional<UserEntity>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/register")
    public ResponseEntity<Boolean> insert(@RequestBody UserEntity user) {
        return ResponseEntity.ok(service.save(user));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteById(id));
    }

    @GetMapping("/quantity")
    public ResponseEntity<Long> count() {
        return ResponseEntity.ok(service.count());
    }

    @GetMapping("/presence/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable Long id) {
        return ResponseEntity.ok(service.existsById(id));
    }
}
