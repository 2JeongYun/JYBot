package com.neukrang.jybot.command.game;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neukrang.jybot.command.constraint.NeedTarget;
import com.neukrang.jybot.command.skeleton.Category;
import com.neukrang.jybot.command.skeleton.Command;
import com.neukrang.jybot.util.ApiCaller;
import com.neukrang.jybot.util.ApiResponse;
import com.neukrang.jybot.util.MethodType;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

@RequiredArgsConstructor
@Component
public class SimpleProfileCommand extends Command {

    private final ApiCaller citadelApiCaller;
    private String baseUrl = "/lol/v1/simple-profile";

    @PostConstruct
    @Override
    protected void init() {
        commandName = "simple";
        category = Category.GAME;

        helpMessage = "!simple [소환사명]\n" + "해당 소환사의 요약 정보를 가져옵니다.";
        constraintList = new ArrayList<>(Arrays.asList(
                NeedTarget.class
        ));
    }

    @Override
    public void handle(GuildMessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        String summonerName = content.substring(content.indexOf(' ') + 1);
        String encodedName = URLEncoder.encode(summonerName, StandardCharsets.UTF_8);
        String url = baseUrl + "/" + encodedName;

        ApiResponse<SimpleProfile> response =
                citadelApiCaller.call(url, MethodType.GET, new TypeReference<ApiResponse<SimpleProfile>>() {});
        SimpleProfile simpleProfile = response.getData();
        
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(simpleProfile.getName());
        eb.addField("티어", simpleProfile.getTier(), true);
        eb.addField("승리", simpleProfile.getWin().toString(), true);
        eb.addField("승률", simpleProfile.getWinRate().toString(), true);
        eb.addField("베테랑", simpleProfile.getMostThree().toString(), true);

        eb.setFooter("Simple Profile");

        event.getChannel().sendMessage(eb.build()).queue();
    }
}
