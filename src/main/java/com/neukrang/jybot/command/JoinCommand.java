package com.neukrang.jybot.command;

import com.neukrang.jybot.command.skeleton.ICommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import org.springframework.stereotype.Component;

@Component
public class JoinCommand implements ICommand {

    @Override
    public void handle(GuildMessageReceivedEvent event) {
        Guild guild = event.getGuild();
        Member member = guild.getMember(event.getAuthor());
        GuildVoiceState voiceState = member.getVoiceState();

        if (!voiceState.inVoiceChannel()) {
            handleError(event);
            return;
        }

        AudioManager manager = guild.getAudioManager();
        manager.openAudioConnection(voiceState.getChannel());
    }

    @Override
    public String getHelp() {
        return "음성채널에 입장한 뒤 사용하면 봇이 해당 채널에 입장합니다.";
    }

    @Override
    public String getName() {
        return "join";
    }

    public void handleError(GuildMessageReceivedEvent event) {
        event.getChannel().sendMessage("해당 명령어는 음성채널에 참여하신 뒤 사용 해주세요.").queue();
    }
}
