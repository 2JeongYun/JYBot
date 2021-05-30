package com.neukrang.jybot.command;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Getter
@Component
public class CommandMap {

    private Map<String, ICommand> commandMap;

    public CommandMap() {
        commandMap = new HashMap<>();
    }
}
