package com.neukrang.jybot.command;

import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.stereotype.Component;

@Component
public class OutCommand implements ICommand {

    @Override
    public void handle(GuildMessageReceivedEvent event) {
        GuildVoiceState voiceState = event.getGuild().getSelfMember().getVoiceState();

        if (!voiceState.inVoiceChannel()) {
            handleError(event);
            return;
        }
        event.getGuild().getAudioManager().closeAudioConnection();
    }

    @Override
    public String getHelp() {
        return "봇을 음성채널에서 퇴장시킵니다.";
    }

    @Override
    public String getName() {
        return "out";
    }

    public void handleError(GuildMessageReceivedEvent event) {
        event.getChannel().sendMessage("봇이 음성채널에 접속중이지 않습니다.");
    }
}
