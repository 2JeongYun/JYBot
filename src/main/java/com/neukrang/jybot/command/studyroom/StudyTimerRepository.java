package com.neukrang.jybot.command.studyroom;

import net.dv8tion.jda.api.entities.Guild;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class StudyTimerRepository {

    private Map<Guild, StudyTimer> timerMap = new ConcurrentHashMap<>();

    public void save(Guild guild, StudyTimer studyTimer) {
        timerMap.put(guild, studyTimer);
    }

    public Optional<StudyTimer> getStudyTimer(Guild guild) {
        return Optional.ofNullable(timerMap.get(guild));
    }

    public void deleteTimer(Guild guild) {
        Optional.ofNullable(timerMap.get(guild))
                .ifPresent(studyTimer -> {
                    studyTimer.stopTimer();
                    timerMap.remove(guild);
                });
    }
}
