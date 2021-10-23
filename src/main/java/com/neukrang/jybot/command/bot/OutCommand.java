package com.neukrang.jybot.command.bot;

import com.neukrang.jybot.command.skeleton.Category;
import com.neukrang.jybot.command.skeleton.Command;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;

@Component
public class OutCommand extends Command {

    @PostConstruct
    @Override
    protected void init() {
        commandName = "out";
        category = Category.BOT;

        helpMessage = "!out\n" + "봇을 음성채널에서 퇴장시킵니다.";
        constraintList = new ArrayList<>(Arrays.asList(
                "noTarget",
                "botInChannel"
        ));
    }

    @Override
    public void handle(GuildMessageReceivedEvent event) {
        event.getGuild().getAudioManager().closeAudioConnection();
    }
}
