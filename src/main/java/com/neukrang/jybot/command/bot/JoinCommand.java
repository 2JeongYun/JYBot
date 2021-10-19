package com.neukrang.jybot.command.bot;

import com.neukrang.jybot.command.skeleton.Category;
import com.neukrang.jybot.command.skeleton.Command;
import com.neukrang.jybot.musicplayer.PlayerManager;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;

@RequiredArgsConstructor
@Component
public class JoinCommand extends Command {

    private final PlayerManager playerManager;

    @PostConstruct
    @Override
    protected void init() {
        commandName = "join";
        category = Category.BOT;

        helpMessage = "!join\n" + "음성채널에 봇을 참가시킵니다.";
        constraintList = new ArrayList<>(Arrays.asList(
                "noTarget",
                "inChannel"
        ));
    }

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
        manager.setSendingHandler(playerManager.getMusicManager(guild).getSendHandler());
        manager.openAudioConnection(voiceState.getChannel());
    }

    public void handleError(GuildMessageReceivedEvent event) {
        event.getChannel()
                .sendMessage("해당 명령어는 음성채널에 참여하신 뒤 사용 해주세요.")
                .queue();
    }
}
