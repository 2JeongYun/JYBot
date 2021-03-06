package com.neukrang.jybot.config;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.neukrang.jybot.crawler.YouTubeCrawler;
import com.neukrang.jybot.listener.CommandListener;
import com.neukrang.jybot.listener.DebugListener;
import com.neukrang.jybot.listener.ReadyListener;
import com.neukrang.jybot.util.ApiCaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Configuration
public class BotConfig implements ApplicationListener<ContextClosedEvent> {

    private final List<Class> listeners = new ArrayList<>(
            Arrays.asList(
                    CommandListener.class,
                    DebugListener.class,
                    ReadyListener.class
            )
    );

    private final ApplicationContext context;

    @Value("${app.citadel.url}")
    private String citadelBaseUrl;

    @Value("${app.discord.token}")
    private String discordToken;

    @Value("${app.google.apikey}")
    private String googleApiKey;

    @Value("${app.citadel.apikey}")
    private String citadelApiKey;

    @Bean
    public JDA jda() {
        JDA jda = null;
        try {
            jda = JDABuilder.createDefault(discordToken).build();
            setJdaListeners(jda);
        } catch (LoginException e) {
            log.error("???????????? ?????? ??????");
            throw new RuntimeException(e);
        }

        return jda;
    }

    public void setJdaListeners(JDA jda) {
        for (Class clazz : listeners) {
            jda.addEventListener(context.getBean(clazz));
        }
        log.info("JDA ????????? ?????? ??????");
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
            log.error("????????? ????????? ?????????????????????.\n" + e.getMessage());
        }
        return youtube;
    }

    @Bean
    public YouTubeCrawler youTubeCrawler() {
        return new YouTubeCrawler(youTube(), googleApiKey);
    }

    @Bean
    public ApiCaller citadelApiCaller() {
        return new ApiCaller(citadelApiKey, citadelBaseUrl);
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        context.getBean("jda", JDA.class).shutdownNow();
        log.info("??????");
    }
}
