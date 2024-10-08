package com.nc13.moviemates.controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
public class JsoupCrawlingController {

    @GetMapping("/api/chart")
    public List<Map<String, String>> getChart() {
        String url = "https://music.bugs.co.kr/chart";
        Document doc;
        List<Map<String, String>> chartList = new ArrayList<>();

        try {
            doc = Jsoup.connect(url).get();
            Elements element = doc.select("table.byChart");

            Iterator<Element> title = element.select("p.title").iterator();
            Iterator<Element> artist = element.select("p.artist").iterator();
            Iterator<Element> rank = element.select("strong").iterator();

            while (rank.hasNext()) {
                Map<String, String> songData = new HashMap<>();
                songData.put("rank", rank.next().text());
                songData.put("artist", artist.next().text());
                songData.put("title", title.next().text());
                chartList.add(songData);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return chartList; // JSON 형식으로 반환
    }
}