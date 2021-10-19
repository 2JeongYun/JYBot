package com.neukrang.jybot.command.music;

import com.neukrang.jybot.command.skeleton.Category;
import com.neukrang.jybot.command.skeleton.Command;
import com.neukrang.jybot.musicplayer.PlayerManager;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;

@RequiredArgsConstructor
@Component
public class PlayCommand extends Command {

    private final PlayerManager playerManager;

    @PostConstruct
    @Override
    protected void init() {
        commandName = "play";
        category = Category.MUSIC;

        helpMessage = "!play\n" + "곡을 재생합니다.";
        constraintList = new ArrayList<>(Arrays.asList(
                "noTarget",
                "sameChannel"
        ));
    }

    @Override
    public void handle(GuildMessageReceivedEvent event) {
        playerManager.unPause(event.getChannel());
    }
}
