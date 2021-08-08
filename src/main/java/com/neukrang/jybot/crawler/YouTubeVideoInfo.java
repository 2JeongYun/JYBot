package com.neukrang.jybot.crawler;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class YouTubeVideoInfo {

    private static final String YOUTUBE_VIDEO_BASE_URL = "https://www.youtube.com/watch?v=";

    private String title;
    private String id;

    public String getUrl() {
        return "https://www.youtube.com/watch?v=" + id;
    }
}
