package com.neukrang.jybot.command.core;

import com.neukrang.jybot.command.constraint.IConstraint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Component
public class CommonValidator {

    private final Map<String, IConstraint> constraintMap;

    public boolean isValid(List<String> constraintList, GuildMessageReceivedEvent event) {
        for (String constraintName : constraintList) {
            IConstraint constraint = constraintMap.get(constraintName);
            if (constraint == null) {
                log.error("constraint: %s를 찾을 수 없습니다.", constraintName);
            }

            if (!constraint.isValid(event)) {
                event.getChannel().sendMessage(constraint.getErrorMessage()).queue();
                return false;
            }
        }

        return true;
    }
}
