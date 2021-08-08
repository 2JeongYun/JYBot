package com.neukrang.jybot.config;

import com.neukrang.jybot.command.skeleton.Command;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CommandConfig {

    @Bean
    public Map<String, Command> commandMap() {
        return new HashMap<>();
    }
}