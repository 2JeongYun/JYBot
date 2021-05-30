package com.neukrang.jybot.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Configuration
@PropertySource("classpath:/application-private.yml")
public class ApplicationConfig {

    private final ApplicationContext context;
    private final Environment env;

    @Bean
    public JDA jda() {
        final String token = env.getProperty("app.discord.token");
        JDA jda = null;
        try {
            jda = JDABuilder.createDefault(token).build();
        } catch (LoginException e) {
            log.error("디스코드 연결 실패");
            throw new RuntimeException(e);
        }

        return setJdaListeners(jda);
    }

    public JDA setJdaListeners(JDA jda) {
        List<String> beanNames = new ArrayList<>(Arrays.asList(
                "commandListener", "readyListener", "debugListener",
                "musicListener"
        ));

        for (String name : beanNames) {
            jda.addEventListener(context.getBean(name));
        }
        log.info("JDA 리스너 추가 완료");
        return jda;
    }
}
