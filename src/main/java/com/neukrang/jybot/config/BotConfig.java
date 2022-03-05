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
@PropertySource("classpath:/config/application-private.properties")
public class BotConfig implements ApplicationListener<ContextClosedEvent> {

    private static final String PROD = "prod";
    private static final String DEV = "dev";
    private final List<Class> listeners = new ArrayList<>(
            Arrays.asList(
                    CommandListener.class,
                    DebugListener.class,
                    ReadyListener.class
            )
    );

    private final ApplicationContext context;
    private final Environment env;

    @Value("${app.citadel.url}")
    private String citadelBaseUrl;

    @Bean
    public JDA jda() {
        String token = getDiscordBotToken();

        JDA jda = null;
        try {
            jda = JDABuilder.createDefault(token).build();
            setJdaListeners(jda);
        } catch (LoginException e) {
            log.error("디스코드 연결 실패");
            throw new RuntimeException(e);
        }

        return jda;
    }

    private String getDiscordBotToken() {
        String[] activeProfiles = env.getActiveProfiles();
        String activeProfileName = Arrays.stream(activeProfiles)
                .filter(profile -> profile.contains(PROD))
                .findAny()
                .orElse(DEV);
        String token = env.getProperty("app.discord.token." + activeProfileName);
        return token;
    }

    public void setJdaListeners(JDA jda) {
        for (Class clazz : listeners) {
            jda.addEventListener(context.getBean(clazz));
        }
        log.info("JDA 리스너 추가 완료");
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

    @Bean
    public ApiCaller citadelApiCaller() {
        final String API_KEY = env.getProperty("app.citadel.apikey");
        final String BASE_URL = citadelBaseUrl;

        return new ApiCaller(API_KEY, BASE_URL);
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        context.getBean("jda", JDA.class).shutdownNow();
        log.info("종료");
    }
}
