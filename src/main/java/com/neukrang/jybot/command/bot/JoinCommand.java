package com.neukrang.jybot.command.bot;

import com.neukrang.jybot.command.constraint.NoTarget;
import com.neukrang.jybot.command.constraint.UserInChannel;
import com.neukrang.jybot.command.skeleton.Category;
import com.neukrang.jybot.command.skeleton.Command;
import com.neukrang.jybot.command.skeleton.CommandInfo;
import com.neukrang.jybot.musicplayer.PlayerManager;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class JoinCommand extends Command {

    private final PlayerManager playerManager;

    @Override
    protected CommandInfo createCommandInfo() {
        return CommandInfo.builder()
                .commandName("join")
                .category(Category.BOT)
                .helpMessage("!join\n" + "음성채널에 봇을 참가시킵니다.")
                .constraintList(List.of(NoTarget.class, UserInChannel.class))
                .build();
    }

    @Override
    public void handle(GuildMessageReceivedEvent event) {
        Guild guild = event.getGuild();
        GuildVoiceState voiceState = guild.getMember(event.getAuthor()).getVoiceState();

        AudioManager manager = guild.getAudioManager();
        manager.setSendingHandler(playerManager.getMusicManager(guild).getSendHandler());
        manager.openAudioConnection(voiceState.getChannel());
    }
}
