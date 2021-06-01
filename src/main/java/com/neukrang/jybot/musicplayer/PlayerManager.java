package com.neukrang.jybot.musicplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PlayerManager {

    private final Map<Long, GuildMusicManager> musicManagers;
    private final AudioPlayerManager audioPlayerManager;

    public PlayerManager() {
        this.musicManagers = new HashMap<>();
        this.audioPlayerManager = new DefaultAudioPlayerManager();

        AudioSourceManagers.registerRemoteSources(this.audioPlayerManager);
        AudioSourceManagers.registerLocalSource(this.audioPlayerManager);
    }

    // 길드 뮤직 매니저를 맵에서 가져온다.
    public GuildMusicManager getMusicManager(Guild guild) {
        return this.musicManagers.computeIfAbsent(guild.getIdLong(), (guildId) -> {
            final GuildMusicManager guildMusicManager = new GuildMusicManager(this.audioPlayerManager);

            guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());

            return guildMusicManager;
        });
    }

    public void printStatus(TextChannel channel) {
        final GuildMusicManager musicManager = this.getMusicManager(channel.getGuild());

        channel.sendMessage(musicManager.scheduler.getStatusMessage()).queue();
    }

    public void pause(TextChannel channel) {
        final GuildMusicManager musicManager = this.getMusicManager(channel.getGuild());

        musicManager.scheduler.pause();
        channel.sendMessage("재생을 멈췄습니다.").queue();
    }

    public void unPause(TextChannel channel) {
        final GuildMusicManager musicManager = this.getMusicManager(channel.getGuild());

        musicManager.scheduler.unPause();
        channel.sendMessage("재생을 재개합니다.").queue();
    }

    public void skip(TextChannel channel) {
        final GuildMusicManager musicManager = this.getMusicManager(channel.getGuild());

        musicManager.scheduler.nextTrack();
        channel.sendMessage("현재 재생중인 곡을 스킵했습니다.").queue();
    }

    public void load(TextChannel channel, String trackUrl) {
        final GuildMusicManager musicManager = this.getMusicManager(channel.getGuild());

        this.audioPlayerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                musicManager.scheduler.queue(track);
                channel.sendMessage("음악이 추가되었습니다.").queue();
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {

            }

            @Override
            public void noMatches() {
                channel.sendMessage("리소스를 찾을 수 없습니다.").queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                channel.sendMessage("ERROR: 리소스를 로드할 수 없습니다.").queue();
            }
        });
    }
}
