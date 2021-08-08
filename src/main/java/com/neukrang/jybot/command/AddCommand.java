package com.neukrang.jybot.command;

import com.neukrang.jybot.command.skeleton.TargetCommand;
import com.neukrang.jybot.crawler.YouTubeCrawler;
import com.neukrang.jybot.musicplayer.PlayerManager;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AddCommand extends TargetCommand {

    private final PlayerManager playerManager;
    private final YouTubeCrawler youtubeCrawler;

    @Override
    public void handle(GuildMessageReceivedEvent event) {
        String[] keywords = getTargets(event.getMessage().getContentRaw());
        for (String keyword : keywords) {
            if (isUrl(keyword))
                playerManager.load(event.getChannel(), keyword);
            else
                playerManager.load(event.getChannel(), youtubeCrawler.getUrl(keyword).getUrl());
        }
    }

    private boolean isUrl(String input) {
        if(input.contains("https://") || input.contains("http://"))
            return true;
        return false;
    }

    @Override
    public String getHelp() {
        return "!add 입력값\n"
                + "음악을 큐에 추가합니다.";
    }

    @Override
    public String getName() {
        return "add";
    }
}
