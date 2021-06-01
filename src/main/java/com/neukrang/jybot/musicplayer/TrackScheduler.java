package com.neukrang.jybot.musicplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TrackScheduler extends AudioEventAdapter {

    private final AudioPlayer player;
    private final BlockingQueue<AudioTrack> queue;

    public TrackScheduler(AudioPlayer player) {
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
    }

    public void pause() {
        this.player.setPaused(true);
    }

    public void unPause() {
        this.player.setPaused(false);
    }

    public void queue(AudioTrack track) {
        if (!player.startTrack(track, true))
            this.queue.offer(track);
    }

    public void nextTrack() {
        this.player.startTrack(this.queue.poll(), false);
    }

    public String getStatusMessage() {
        StringBuffer sb = new StringBuffer();
        AudioTrack[] tracks = queue.toArray(new AudioTrack[0]);

        if (player.getPlayingTrack() != null) {
            sb.append(String.format("-----현재 재생중인 곡: %s-----\n",
                    player.getPlayingTrack().getInfo().title));
        }

        if (player.isPaused()) {
            sb.append("-----일시 정지-----");
        }

        sb.append("-----뮤직 큐-----\n");
        if (queue.isEmpty()) {
            sb.append("큐가 비어있습니다.");
        } else {
            for (int i = 0; i < queue.size(); i++) {
                sb.append(String.format("%d. %s\n", i, tracks[i].getInfo().title));
            }
        }

        return sb.toString();
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) {
            nextTrack();
        }
    }
}
