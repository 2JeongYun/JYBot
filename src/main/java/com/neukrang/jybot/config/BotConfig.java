package com.neukrang.jybot.config;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.neukrang.jybot.crawler.YouTubeCrawler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.core.env.Environment;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Configuration
@PropertySource("classpath:/application-private.yml")
public class BotConfig implements ApplicationListener<ContextClosedEvent> {

    private final ApplicationContext context;
    private final Environment env;

    @Bean
    public JDA jda() {
        final String TOKEN = env.getProperty("app.discord.token");
        JDA jda = null;
        try {
            jda = JDABuilder.createDefault(TOKEN).build();
        } catch (LoginException e) {
            log.error("디스코드 연결 실패");
            throw new RuntimeException(e);
        }

        return setJdaListeners(jda);
    }

    public JDA setJdaListeners(JDA jda) {
        List<String> beanNames = new ArrayList<>(Arrays.asList(
                "commandListener", "readyListener", "debugListener"
        ));

        for (String name : beanNames) {
            jda.addEventListener(context.getBean(name));
        }
        log.info("JDA 리스너 추가 완료");
        return jda;
    }

    @Bean
    public YouTube youTube() {
        HttpTransport httpTransport = new NetHttpTransport();
        JsonFactory jsonFactory = new GsonFactory();
        YouTube youtube = null;
        try {
            youtube = new YouTube.Builder(
                    httpTransport, jsonFactory, new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest request) throws IOException {}
            }).build();
        } catch (Exception e) {
            log.error("유튜브 연결에 실패하였습니다.\n" + e.getMessage());
        }
        return youtube;
    }

    @Bean
    public YouTubeCrawler youTubeCrawler() {
        final String API_KEY = env.getProperty("app.google.apikey");

        return new YouTubeCrawler(youTube(), API_KEY);
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        context.getBean("jda", JDA.class).shutdownNow();
        log.info("종료");
    }
}
