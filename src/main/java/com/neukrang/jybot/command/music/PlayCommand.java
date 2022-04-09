package com.neukrang.jybot.command.music;

import com.neukrang.jybot.command.constraint.NoTarget;
import com.neukrang.jybot.command.constraint.SameChannel;
import com.neukrang.jybot.command.skeleton.Category;
import com.neukrang.jybot.command.skeleton.Command;
import com.neukrang.jybot.command.skeleton.CommandInfo;
import com.neukrang.jybot.musicplayer.PlayerManager;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class PlayCommand extends Command {

    private final PlayerManager playerManager;

    @Override
    protected CommandInfo createCommandInfo() {
        return CommandInfo.builder()
                .commandName("play")
                .category(Category.MUSIC)
                .helpMessage("!play\n" + "곡을 재생합니다.")
                .constraintList(List.of(NoTarget.class, SameChannel.class))
                .build();
    }

    @Override
    public void handle(GuildMessageReceivedEvent event) {
        playerManager.unPause(event.getChannel());
    }
}
