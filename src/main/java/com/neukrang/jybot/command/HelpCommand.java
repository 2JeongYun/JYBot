package com.neukrang.jybot.command;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class HelpCommand implements ICommand {

    private final Map<String, ICommand> commandMap;

    public HelpCommand(ApplicationContext context) {
        this.commandMap = context.getBean("commandMap", Map.class);
    }

    @Override
    public void handle(GuildMessageReceivedEvent event) {
        System.out.println(commandMap.keySet().toString());

        String option = getOption(event);
        String retMsg = null;
        if (option == null)
            retMsg = this.getHelp();
        else {
            if (!commandMap.containsKey(option)) {
                handleError(event, option);
                return;
            }
            retMsg = commandMap.get(option).getHelp();
        }
        event.getChannel().sendMessage(retMsg).queue();
    }

    @Override
    public String getHelp() {
        StringBuffer retMsg = new StringBuffer();
        for (String commandName : commandMap.keySet()) {
            retMsg.append(commandName).append("\n");
        }
        return retMsg.toString();
    }

    @Override
    public String getName() {
        return "help";
    }

    public void handleError(GuildMessageReceivedEvent event, String option) {
        String retMsg = String.format("%s 에 해당하는 명령어가 없습니다. 사용 가능한 명령어는 !help 를 참조하세요", option);
        event.getChannel().sendMessage(retMsg).queue();
    }

    private String getOption(GuildMessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        String[] words = content.split(" ");
        if (words.length <= 1) return null;
        String option = words[1];
        return option;
    }
}
