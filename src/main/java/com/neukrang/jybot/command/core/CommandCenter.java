package com.neukrang.jybot.command.core;

import com.neukrang.jybot.command.skeleton.ICommand;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

@RequiredArgsConstructor
@Component
public class CommandCenter {

    private final Map<String, ICommand> commandMap;
    private final CommonValidator validator;

    public void handle(GuildMessageReceivedEvent event) {
        String commandName = extractCommandName(event);
        String commandBeanName = commandName + "Command";
        if (!commandMap.containsKey(commandBeanName)) {
            event.getChannel()
                    .sendMessage("해당되는 명령어가 없습니다. '!help' 를 참조하세요.")
                    .queue();
            return;
        }

        ICommand command = commandMap.get(commandBeanName);

        if (!validator.isValid(command.getConstraintList(), event)) {
            return;
        }
        command.handle(event);
    }

    private String extractCommandName(GuildMessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        return content.split(" ")[0].replaceFirst("!", "");
    }
}
