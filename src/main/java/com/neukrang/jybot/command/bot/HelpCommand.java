package com.neukrang.jybot.command.bot;

import com.neukrang.jybot.command.core.CommandUtil;
import com.neukrang.jybot.command.skeleton.Category;
import com.neukrang.jybot.command.skeleton.Command;
import com.neukrang.jybot.command.skeleton.ICommand;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class HelpCommand extends Command {

    private final Map<String, ICommand> commandMap;

    @PostConstruct
    @Override
    protected void init() {
        commandName = "help";
        category = Category.BOT;

        helpMessage = makeHelpMessage();
        constraintList = new ArrayList<>(Arrays.asList(
                "notManyTarget"
        ));
    }

    @Override
    public void handle(GuildMessageReceivedEvent event) {
        List<String> targets = CommandUtil.parse(event);
        String retMsg = null;

        if (targets.size() == 0)
            retMsg = this.getHelpMessage();
        else {
            String target = targets.get(0);
            String beanName = target + "Command";
            if (!commandMap.containsKey(beanName)) {
                handleError(event, target);
                return;
            }

            retMsg = commandMap.get(beanName).getHelpMessage();
        }
        event.getChannel().sendMessage(retMsg).queue();
    }

    private String makeHelpMessage() {
        StringBuffer retMsg = new StringBuffer();
        for (String commandName : commandMap.keySet()) {
            ICommand command = commandMap.get(commandName);

            if (command.getCategory() != Category.TEST &&
                    command.getCategory() != Category.NONE) {
                retMsg.append(command.getHelpMessage()).append("\n");
                retMsg.append("-----------------------------------\n");
            }
        }
        return retMsg.toString();
    }

    public void handleError(GuildMessageReceivedEvent event, String target) {
        String retMsg = String.format("%s 에 해당하는 명령어가 없습니다. 사용 가능한 명령어는 !help 를 참조하세요", target);
        event.getChannel().sendMessage(retMsg).queue();
    }
}
