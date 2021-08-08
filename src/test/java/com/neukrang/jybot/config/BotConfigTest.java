package com.neukrang.jybot.config;

import com.google.api.services.youtube.YouTube;
import com.neukrang.jybot.crawler.YouTubeCrawler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BotConfigTest {

    @Autowired
    ApplicationContext context;

    @Test
    public void 유튜브_크롤러에_DI된_유튜브_객체는_등록된_빈과_동일하다() throws Exception {
        YouTube youTube = context.getBean("youTube", YouTube.class);
        YouTubeCrawler youTubeCrawler = context.getBean("youTubeCrawler", YouTubeCrawler.class);

        Field youTubeField = youTubeCrawler.getClass().getDeclaredField("youTube");
        youTubeField.setAccessible(true);
        YouTube youTubeInCrawler = (YouTube) youTubeField.get(youTubeCrawler);

        assertThat(youTube).isEqualTo(youTubeInCrawler);
    }
}
