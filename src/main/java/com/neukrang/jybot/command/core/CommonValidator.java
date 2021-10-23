package com.neukrang.jybot.command.core;

import com.neukrang.jybot.command.constraint.IConstraint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class CommonValidator {

    private final ApplicationContext context;

    public boolean isValid(List<Class> constraintList, GuildMessageReceivedEvent event) {
        for (Class constraintClazz : constraintList) {
            IConstraint constraint = (IConstraint) context.getBean(constraintClazz);

            if (!constraint.isValid(event)) {
                event.getChannel().sendMessage(constraint.getErrorMessage()).queue();
                return false;
            }
        }

        return true;
    }
}
