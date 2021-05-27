package com.neukrang.jybot.listener;

import com.neukrang.jybot.musicplayer.PlayerManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;
import org.springframework.stereotype.Component;

@Component
public class MusicListener extends ListenerAdapter {

    private final PlayerManager playerManager;

    public MusicListener() {
        playerManager = new PlayerManager();
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().startsWith("!stop")) {
            event.getGuild().getAudioManager().closeAudioConnection();
        }
        if (!event.getMessage().getContentRaw().startsWith("!play")) return;
        if (event.getAuthor().isBot()) return;

        Guild guild = event.getGuild();
        VoiceChannel channel = guild.getVoiceChannelsByName("music", true).get(0);
        AudioManager manager = guild.getAudioManager();

        manager.setSendingHandler(playerManager.getMusicManager(guild).getSendHandler());
        manager.openAudioConnection(channel);
        playerManager.loadAndPlay(event.getChannel(), "https://www.youtube.com/watch?v=YQHsXMglC9A");
    }
}
