package com.nc13.moviemates.controller;

import org.jsoup.Jsoup;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/single")
    public String toSingle() {
        return "single";
    }


}
