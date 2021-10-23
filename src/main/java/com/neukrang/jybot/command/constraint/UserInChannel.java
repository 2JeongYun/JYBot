package com.neukrang.jybot.command.constraint;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.stereotype.Component;

@Component
public class UserInChannel implements IConstraint {

    @Override
    public boolean isValid(GuildMessageReceivedEvent event) {
        Guild guild = event.getGuild();
        GuildVoiceState memberState = guild.getMember(event.getAuthor()).getVoiceState();

        if (!memberState.inVoiceChannel()) {
            return false;
        }

        return true;
    }

    @Override
    public String getErrorMessage() {
        return "음성채널에 입장한 뒤 사용해주세요.";
    }
}
