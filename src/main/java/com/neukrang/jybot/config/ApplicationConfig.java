package com.neukrang.jybot.config;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.security.auth.login.LoginException;

@RequiredArgsConstructor
@Configuration
@PropertySource("classpath:/application.yml")
public class ApplicationConfig {

    private final ApplicationContext context;

    @Bean
    public JDA jda() {
        final String token = context.getEnvironment().getProperty("app.discord.token");
        try {
            return JDABuilder.createDefault(token)
                    .addEventListeners(context.getBean("readyListener"))
                    .addEventListeners(context.getBean("testListener"))
                    .build();
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }
    }
}
