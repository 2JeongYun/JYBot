package com.neukrang.jybot.command.constraint;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.stereotype.Component;

@Component
public class BotInChannel implements IConstraint {

    @Override
    public boolean isValid(GuildMessageReceivedEvent event) {
        GuildVoiceState voiceState = event.getGuild().getSelfMember().getVoiceState();

        if (!voiceState.inVoiceChannel()) {
            return false;
        }
        return true;
    }

    @Override
    public String getErrorMessage() {
        return "봇이 음성채널에 접속중이지 않습니다.";
    }
}
