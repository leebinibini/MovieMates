package com.nc13.moviemates.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class WebCrawlerService {
//        public List<String> crawl() {
//            final String url = "https://www.megabox.co.kr/?NaPm=ct%3Dm0ur9e82%7Cci%3Dcheckout%7Ctr%3Dds%7Ctrx%3Dnull%7Chk%3Dce915ce60dbb3b60404aa53f40dbdd9c25cea6e5";
//            List<String> results = new ArrayList<>();
//            try {
//                // URL로부터 HTML 문서 가져오기
//                Document document = Jsoup.connect(url).get();
//
//                // 문서에서 특정 CSS 선택자에 해당하는 요소 찾기
//                Elements paragraphs = document.select("#main_section01 > div.cont-area > div.main-movie-list");
//                for (Element paragraph : paragraphs) {
//                    results.add(paragraph.text());
//                }
//                Iterator<Element> itr = paragraphs.iterator();
//                System.out.println(itr);
////                // 링크 추출하기
////                Elements links = document.select("a[href]");
////                for (Element link : links) {
////                    results.add("Link: " + link.attr("href"));
////                    results.add("Link Text: " + link.text());
////                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return results;
//        }
    }


