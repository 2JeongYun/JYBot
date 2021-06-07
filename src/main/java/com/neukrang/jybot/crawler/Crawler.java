package com.neukrang.jybot.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;

public class Crawler {

    static final int TIME_OUT = 5000;

    public Document getHtml(String url) {
        Document document;
        try {
            document = Jsoup
                    .connect(url)
                    .timeout(TIME_OUT)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Html을 가져오지 못했습니다.", e);
        }
        return document;
    }

    public Document getPostResponse(String url, String key, String value) {
        Document document;
        try {
            document = Jsoup.connect(url)
                    .data(key, value)
                    .postDataCharset("EUC-KR")
                    .timeout(TIME_OUT)
                    .post();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Html을 가져오지 못했습니다.", e);
        }
        return document;
    }

    public Document getPostResponse(String url, Map<String, String> data) {
        Document document;
        try {
            document = Jsoup.connect(url)
                    .data(data)
                    .postDataCharset("EUC-KR")
                    .post();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Html을 가져오지 못했습니다.", e);
        }
        return document;
    }
}
