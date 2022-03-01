package com.neukrang.jybot.command.core;

import com.neukrang.jybot.command.skeleton.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class CommandCenter {

    private final CommonValidator validator;
    private Map<String, ICommand> commandMap;

    public CommandCenter(CommonValidator validator, List<ICommand> commandList) {
        this.validator = validator;
        loadCommandMap(commandList);
    }

    private void loadCommandMap(List<ICommand> commandList) {
        commandMap = new HashMap<>();
        commandList.stream()
                .forEach(command -> commandMap.put(command.getCommandName(), command));
    }

    public void handle(GuildMessageReceivedEvent event) {
        String commandName = extractCommandName(event);

        Optional<ICommand> command = Optional.ofNullable(commandMap.get(commandName));
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
}
