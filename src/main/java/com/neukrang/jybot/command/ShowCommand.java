package com.neukrang.jybot.command;

import com.neukrang.jybot.command.skeleton.TargetCommand;
import com.neukrang.jybot.musicplayer.PlayerManager;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ShowCommand extends TargetCommand {

    private final PlayerManager playerManager;

    @Override
    public void handle(GuildMessageReceivedEvent event) {
        playerManager.printStatus(event.getChannel());
    }

    @Override
    public String getHelp() {
        return "!show 입력값\n"
                + "입력값에 대한 정보를 보여줍니다.";
    }

    @Override
    public String getName() {
        return "show";
    }
}
