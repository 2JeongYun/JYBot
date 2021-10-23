package com.neukrang.jybot.command.music;

import com.neukrang.jybot.command.constraint.NoTarget;
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
public class QueueCommand extends Command {

    private final PlayerManager playerManager;

    @PostConstruct
    @Override
    protected void init() {
        commandName = "queue";
        category = Category.MUSIC;

        helpMessage = "!queue\n" + "다음 곡 목록을 보여줍니다.";
        constraintList = new ArrayList<>(Arrays.asList(
                NoTarget.class
        ));
    }

    @Override
    public void handle(GuildMessageReceivedEvent event) {
        playerManager.printStatus(event.getChannel());
    }
}
