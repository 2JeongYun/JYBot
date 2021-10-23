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
public class SkipCommand extends Command {

    private final PlayerManager playerManager;

    @PostConstruct
    @Override
    protected void init() {
        commandName = "skip";
        category = Category.MUSIC;

        helpMessage = "!skip\n" + "현재 곡을 스킵합니다.";
        constraintList = new ArrayList<>(Arrays.asList(
                NoTarget.class,
                SameChannel.class
        ));
    }

    @Override
    public void handle(GuildMessageReceivedEvent event) {
        playerManager.skip(event.getChannel());
    }
}
