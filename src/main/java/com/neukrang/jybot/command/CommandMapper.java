package com.neukrang.jybot.command;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class CommandMapper implements ICommand {

    private final Map<String, ICommand> commandMap;

    public CommandMapper() {
        commandMap = new HashMap<>();
    }

    @Override
    public void handle(GuildMessageReceivedEvent event) {
        String commandName = getCommandName(event);
        ICommand command = commandMap.get(commandName);
        if (command == null) {
            handleError(event);
            return;
        }
        command.handle(event);
    }

    @Override
    // Fixme: 사용가능한 커맨드들의 도움말 리턴
    public String getHelp() {
        return "";
    }

    @Override
    public String getName() {
        return "command";
    }

    @Override
    public void handleError(GuildMessageReceivedEvent event) {
        event.getChannel().sendMessage("해당되는 명령어가 없습니다. '!help' 를 참조하세요.").queue();
    }

    private String getCommandName(GuildMessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        return content.split(" ")[0].replaceFirst("!", "");
    }

    private void add(ICommand command) {
        String name = command.getName();
        if (commandMap.containsKey(name)) return;

        commandMap.put(name, command);
    }
}
