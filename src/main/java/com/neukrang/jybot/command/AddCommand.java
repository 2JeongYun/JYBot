package com.neukrang.jybot.command;

import com.neukrang.jybot.command.skeleton.TargetCommand;
import com.neukrang.jybot.musicplayer.PlayerManager;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AddCommand extends TargetCommand {

    private final PlayerManager playerManager;

    @Override
    public void handle(GuildMessageReceivedEvent event) {
        String[] urls = getTargets(event.getMessage().getContentRaw());
        for (String url : urls) {
            playerManager.load(event.getChannel(), url);
        }
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
