package com.neukrang.jybot.command.constraint;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.stereotype.Component;

@Component
public class SameChannel implements Constraint {

    @Override
    public boolean isValid(GuildMessageReceivedEvent event) {
        Guild guild = event.getGuild();
        GuildVoiceState memberState = guild.getMember(event.getAuthor()).getVoiceState();
        GuildVoiceState botState = guild.getSelfMember().getVoiceState();

        if (!memberState.inVoiceChannel() ||
                !botState.inVoiceChannel()) {

            return false;
        }
        if (memberState.getChannel().getIdLong() !=
                botState.getChannel().getIdLong()) {

            return false;
        }

        return true;
    }

    @Override
    public String getErrorMessage() {
        return "봇과 같은 음성채널에서 사용해주세요.";
    }
}
