package com.neukrang.jybot.command.studyroom;

import com.neukrang.jybot.command.constraint.NeedTarget;
import com.neukrang.jybot.command.core.CommandUtil;
import com.neukrang.jybot.command.skeleton.Category;
import com.neukrang.jybot.command.skeleton.Command;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Component
public class SetTimerCommand extends Command {

    private final StudyTimerRepository studyTimerRepository;

    @PostConstruct
    @Override
    protected void init() {
        commandName = "setStudyTimer";
        category = Category.BOT;

        helpMessage = "!setStudyTimer [공부 시간] [쉬는 시간]\n" +
                "공부 시간(분 단위)을 설정합니다.\n" +
                "둘 중 하나라도 0으로 설정하면 타이머가 종료됩니다.";
        constraintList = new ArrayList<>(Arrays.asList(
                NeedTarget.class
        ));
    }

    @Override
    public void handle(GuildMessageReceivedEvent event) {
        List<String> times = CommandUtil.parse(event);
        int studyMinute = Integer.valueOf(times.get(0));
        int restMinute = Integer.valueOf(times.get(1));

        stopAndDeleteTimer(event);
        if (isStopCommand(studyMinute, restMinute)) {
            return;
        }

        studyTimerRepository.save(
                event.getGuild(),
                StudyTimer.builder()
                        .studyMinute(studyMinute)
                        .restMinute(restMinute)
                        .guild(event.getGuild())
                        .textChannel(event.getChannel())
                        .build()
        );
    }

    private void stopAndDeleteTimer(GuildMessageReceivedEvent event) {
        studyTimerRepository.getStudyTimer(event.getGuild())
                .ifPresent(studyTimer -> {
                    deleteTimer(event.getGuild());
                    event.getMessage().getTextChannel().sendMessage("타이머를 초기화 했습니다.").queue();
                });
    }

    private boolean isStopCommand(int studyMinute, int restMinute) {
        return studyMinute == 0 || restMinute == 0 ? true : false;
    }

    private void deleteTimer(Guild guild) {
        studyTimerRepository.deleteTimer(guild);
    }
}
