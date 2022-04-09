package com.neukrang.jybot.command.music;

import com.neukrang.jybot.command.constraint.NeedTarget;
import com.neukrang.jybot.command.constraint.SameChannel;
import com.neukrang.jybot.command.skeleton.Category;
import com.neukrang.jybot.command.skeleton.Command;
import com.neukrang.jybot.command.skeleton.CommandInfo;
import com.neukrang.jybot.crawler.YouTubeCrawler;
import com.neukrang.jybot.musicplayer.PlayerManager;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class AddCommand extends Command {

    private final PlayerManager playerManager;
    private final YouTubeCrawler youtubeCrawler;

    @Override
    protected CommandInfo createCommandInfo() {
        return CommandInfo.builder()
                .commandName("add")
                .category(Category.MUSIC)
                .helpMessage("!add [제목]\n"
                        + "음악을 큐에 추가합니다.")
                .constraintList(List.of(NeedTarget.class, SameChannel.class))
                .build();
    }

    @Override
    public void handle(GuildMessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        int keywordIdx = content.indexOf(' ');

        String keyword = content.substring(keywordIdx + 1);
        System.out.println("keyword = " + keyword);

        if (isUrl(keyword))
            playerManager.load(event.getChannel(), keyword);
        else
            playerManager.load(event.getChannel(), youtubeCrawler.getUrl(keyword).getUrl());
    }

    private boolean isUrl(String keyword) {
        if (keyword.startsWith("http://"))
            return true;
        return false;
    }
}
