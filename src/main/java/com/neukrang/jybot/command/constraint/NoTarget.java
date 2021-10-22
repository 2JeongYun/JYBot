package com.neukrang.jybot.command.constraint;

import com.neukrang.jybot.command.core.CommandUtil;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NoTarget implements IConstraint {

    @Override
    public boolean isValid(GuildMessageReceivedEvent event) {
        List<String> targets = CommandUtil.parse(event);

        if (targets.size() > 0) {
            return false;
        }
        return true;
    }

    @Override
    public String getErrorMessage() {
        return "!명령어 형식으로 사용해주세요.";
    }
}
