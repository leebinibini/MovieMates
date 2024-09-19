package com.nc13.moviemates.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String Home(){
        return "index";
    }

    @GetMapping("details")
    public String Details(){return  "details";}
}
