package com.neukrang.jybot.command.studyroom;

import lombok.Builder;
import lombok.Getter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Getter
public class StudyTimer {

    private Timer studyTimer;
    private Timer restTimer;
    private Guild guild;
    private TextChannel textChannel;
    private StudyStatus status;
    private LocalDateTime startTime;
    private int studyMinute;
    private int restMinute;

    @Builder
    public StudyTimer(int studyMinute, int restMinute, Guild guild, TextChannel textChannel) {
        this.studyMinute = studyMinute;
        this.restMinute = restMinute;
        this.guild = guild;
        this.textChannel = textChannel;
        setStudyTimer();
    }

    public void stopTimer() {
        studyTimer.cancel();
        restTimer.cancel();
    }

    private Timer setStudyTimer() {
        studyTimer = new Timer();

        TimerTask studyNotifyTask = new TimerTask() {
            @Override
            public void run() {
                notifyStudyTime();
                status = StudyStatus.STUDY;

                setRestTimer();
            }
        };

        startTime = LocalDateTime.now();
        studyTimer.schedule(studyNotifyTask, 0, convertToMilliSecond(studyMinute + restMinute));
        return studyTimer;
    }

    private void notifyStudyTime() {
        textChannel.sendMessage(createMentionInStudyRoom()
                + "\n공부 시간입니다. 다음 휴식 시각은 " + getNextTime(studyMinute) + "입니다.").queue();
    }

    private void setRestTimer() {
        restTimer = new Timer();
        TimerTask restNotifyTask = new TimerTask() {
            @Override
            public void run() {
                notifyRestTime();
                status = StudyStatus.REST;
            }
        };
        restTimer.schedule(restNotifyTask, convertToMilliSecond(studyMinute));
    }

    private void notifyRestTime() {
        textChannel.sendMessage(createMentionInStudyRoom()
                + "\n휴식 시간입니다. 다음 공부 시각은 " + getNextTime(restMinute) + "입니다.").queue();
    }

    private String getNextTime(int minute) {
        return getFormattedTime(LocalDateTime.now().plusMinutes(minute));
    }

    private String getFormattedTime(LocalDateTime localDateTime) {
        return DateTimeFormatter.ofPattern("HH시 mm분").format(localDateTime);
    }

    private long convertToMilliSecond(int minute) {
        return (long) minute * 60000;
    }

    private String createMentionInStudyRoom() {
        List<VoiceChannel> studyRoom = guild.getVoiceChannelsByName("StudyRoom", true);
        StringBuffer mentionMessage = new StringBuffer();
        studyRoom.stream()
                .forEach(room -> {
                    room.getMembers().stream()
                            .filter(member -> !member.getUser().isBot())
                            .forEach(member -> mentionMessage.append(member.getAsMention()));
                });
        return mentionMessage.toString();
    }
}
