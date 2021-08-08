package com.neukrang.jybot.crawler;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class YouTubeCrawler {

    private final YouTube youTube;
    private final String API_KEY;

    private final long NUMBER_OF_VIDEOS_RETURNED = 5;


    public YouTubeVideoInfo getUrl(String keyword) {
        YouTube.Search.List search;
        YouTubeVideoInfo ret = null;
        try {
            search = youTube.search().list("id, snippet");
            search.setKey(API_KEY);
            search.setQ(keyword);
            search.setType("video");
            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
            search.setFields("items(id/videoId, snippet/title)");

            List<SearchResult> result = search.execute().getItems();

            SearchResult target = result.get(0);

            ret = YouTubeVideoInfo.builder()
                    .title(target.getSnippet().getTitle())
                    .id(target.getId().getVideoId())
                    .build();
        } catch (IOException e) {
            log.error("유튜브 검색에 실패하였습니다.\n" + e.getMessage());
            throw new RuntimeException(e);
        }

        return ret;
    }
}
