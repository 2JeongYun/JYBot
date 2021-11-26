package com.neukrang.jybot.command.core;

import com.neukrang.jybot.command.skeleton.ICommand;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CommandCenter {

    private final Map<String, ICommand> commandMap;
    private final CommonValidator validator;

    public void handle(GuildMessageReceivedEvent event) {
        String commandName = extractCommandName(event);
        String commandBeanName = commandName + "Command";

        Optional<ICommand> command = Optional.ofNullable(commandMap.get(commandBeanName));
        if (command.isEmpty())
            command = findByCommandName(commandMap, commandName);

        if (command.isEmpty()) {
            event.getChannel()
                    .sendMessage("해당되는 명령어가 없습니다. '!help' 를 참조하세요.")
                    .queue();
            return;
        }

        if (!validator.isValid(command.get().getConstraintList(), event)) {
            return;
        }
        command.get().handle(event);
    }

    private String extractCommandName(GuildMessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        return content.split(" ")[0].replaceFirst("!", "");
    }

    private Optional<ICommand> findByCommandName(Map<String, ICommand> map, String commandName) {
        return map.values().stream()
                .filter(iCommand -> iCommand.getCommandName().equals(commandName))
                .findFirst();
    }
}
