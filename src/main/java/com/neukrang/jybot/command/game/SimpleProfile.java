package com.neukrang.jybot.command.game;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SimpleProfile {

    private String name;
    private String tier;
    private Integer rank;
    private Integer win;
    private Integer lose;
    private String winRate;
    private List<String> mostThree =  new ArrayList<>();
}
