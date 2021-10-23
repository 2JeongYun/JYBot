package com.neukrang.jybot.command.music;

import com.neukrang.jybot.command.constraint.NoTarget;
import com.neukrang.jybot.command.constraint.SameChannel;
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
public class PauseCommand extends Command {

    private final PlayerManager playerManager;

    @PostConstruct
    @Override
    protected void init() {
        commandName = "pause";
        category = Category.MUSIC;

        helpMessage = "!pause\n" + "음악 재생을 일시정지 합니다.";
        constraintList = new ArrayList<>(Arrays.asList(
                NoTarget.class,
                SameChannel.class
        ));
    }

    @Override
    public void handle(GuildMessageReceivedEvent event) {
        playerManager.pause(event.getChannel());
    }
}
