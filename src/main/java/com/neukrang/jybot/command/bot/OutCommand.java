package com.neukrang.jybot.command.bot;

import com.neukrang.jybot.command.skeleton.Category;
import com.neukrang.jybot.command.skeleton.Command;
import net.dv8tion.jda.api.entities.GuildVoiceState;
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
                "noTarget"
        ));
    }

    @Override
    public void handle(GuildMessageReceivedEvent event) {
        GuildVoiceState voiceState = event.getGuild().getSelfMember().getVoiceState();

        if (!voiceState.inVoiceChannel()) {
            handleError(event);
            return;
        }
        event.getGuild().getAudioManager().closeAudioConnection();
    }

    public void handleError(GuildMessageReceivedEvent event) {
        event.getChannel().sendMessage("봇이 음성채널에 접속중이지 않습니다.");
    }
}
