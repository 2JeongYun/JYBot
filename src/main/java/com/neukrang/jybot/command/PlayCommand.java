package com.neukrang.jybot.command;

import com.neukrang.jybot.musicplayer.PlayerManager;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PlayCommand implements ICommand {

    private final PlayerManager playerManager;

    @Override
    public void handle(GuildMessageReceivedEvent event) {
        Guild guild = event.getGuild();
        AudioManager manager = guild.getAudioManager();
        manager.setSendingHandler(playerManager.getMusicManager(guild).getSendHandler());

        playerManager.loadAndPlay(event.getChannel(), getResourceUrl(event));
    }

    @Override
    public String getHelp() {
        return "음악을 재생합니다.";
    }

    @Override
    public String getName() {
        return "play";
    }

    public String getResourceUrl(GuildMessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        return content.split(" ")[1];
    }
}
