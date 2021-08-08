package com.neukrang.jybot.command;

import com.neukrang.jybot.command.skeleton.SingleCommand;
import com.neukrang.jybot.musicplayer.PlayerManager;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UnPauseCommand extends SingleCommand {

    private final PlayerManager playerManager;

    @Override
    public void handle(GuildMessageReceivedEvent event) {
        playerManager.unPause(event.getChannel());
    }

    @Override
    public String getHelp() {
        return "일시 정지를 해제합니다.";
    }

    @Override
    public String getName() {
        return "unPause";
    }
}