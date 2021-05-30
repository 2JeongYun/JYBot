package com.neukrang.jybot.command;

import lombok.Getter;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Getter
@Component
public class CommandMapper {

    private final ApplicationContext applicationContext;
    private final Map<String, ICommand> commandMap;

    public CommandMapper(ApplicationContext applicationContext, CommandMap commandMap) {
        this.applicationContext = applicationContext;
        this.commandMap = commandMap.getCommandMap();
        loadCommands();
    }

    public void handle(GuildMessageReceivedEvent event) {
        String commandName = getCommandName(event);
        ICommand command = commandMap.get(commandName);
        if (command == null) {
            handleError(event);
            return;
        }
        command.handle(event);
        System.out.println(commandMap.keySet().toString());
    }

    public void handleError(GuildMessageReceivedEvent event) {
        event.getChannel().sendMessage("해당되는 명령어가 없습니다. '!help' 를 참조하세요.").queue();
    }

    private String getCommandName(GuildMessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        return content.split(" ")[0].replaceFirst("!", "");
    }

    private void loadCommands() {
        List<String> beanNameList = new ArrayList<>(Arrays.asList(
                "joinCommand", "outCommand", "playCommand", "testCommand",
                "helpCommand"
        ));
        for (String beanName : beanNameList) {
            add(getCommand(beanName));
        }
    }

    private void add(ICommand command) {
        String name = command.getName();
        if (commandMap.containsKey(name)) return;
        commandMap.put(name, command);
    }

    private ICommand getCommand(String beanName) {
        return applicationContext.getBean(beanName, ICommand.class);
    }
}
