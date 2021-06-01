package com.neukrang.jybot.command;

import com.neukrang.jybot.command.skeleton.Command;
import lombok.Getter;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.*;

@Getter
@Component
public class CommandMapper {

    private final ApplicationContext context;
    private final Map<String, Command> commandMap;
    private final Set<Command> sameChannelCommandSet;
    private final FormatChecker formatChecker;

    public CommandMapper(ApplicationContext context) {
        this.context = context;
        this.commandMap = context.getBean("commandMap", Map.class);
        this.formatChecker = new FormatChecker();
        this.sameChannelCommandSet = new HashSet<>();
        loadCommands();
        loadSameChannelMessage();
    }

    public void handle(GuildMessageReceivedEvent event) {
        String commandName = getCommandName(event);
        Command command = commandMap.get(commandName);
        if (command == null) {
            handleError(event);
            return;
        }
        if (!formatChecker.isValidFormat(command, event)) {
            formatChecker.sendFormatErrorMessage(command, event.getChannel());
            return;
        }
        if (sameChannelCommandSet.contains(command) &&
                !formatChecker.isBothInSameVoiceChannel(command, event)) {
            formatChecker.sendNotInSameChannelErrorMessage(event.getChannel());
            return;
        }
        command.handle(event);
    }

    public void handleError(GuildMessageReceivedEvent event) {
        event.getChannel().sendMessage("해당되는 명령어가 없습니다. '!help' 를 참조하세요.").queue();
    }

    private String getCommandName(GuildMessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        return content.split(" ")[0].replaceFirst("!", "");
    }

    private void loadSameChannelMessage() {
        List<String> beanNameList = new ArrayList<>(Arrays.asList(
                "addCommand",
                "pauseCommand",
                "skipCommand",
                "unPauseCommand"
        ));

        for (String beanName : beanNameList) {
            Command command = context.getBean(beanName, Command.class);

            if (!sameChannelCommandSet.contains(command))
                sameChannelCommandSet.add(command);
        }
    }

    private void loadCommands() {
        List<String> beanNameList = new ArrayList<>(Arrays.asList(
                "joinCommand",
                "outCommand",
                "testCommand",
                "helpCommand",
                "addCommand",
                "pauseCommand",
                "unPauseCommand",
                "skipCommand",
                "showCommand"
        ));

        for (String beanName : beanNameList) {
            Command command = context.getBean(beanName, Command.class);
            String commandName = command.getName();

            if (!commandMap.containsKey(commandName))
                commandMap.put(commandName, command);
        }
    }
}
