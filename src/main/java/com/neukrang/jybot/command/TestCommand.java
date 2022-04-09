package com.neukrang.jybot.command;

import com.neukrang.jybot.command.skeleton.Category;
import com.neukrang.jybot.command.skeleton.Command;
import com.neukrang.jybot.command.skeleton.CommandInfo;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.stereotype.Component;

@Component
public class TestCommand extends Command {

    @Override
    protected CommandInfo createCommandInfo() {
        return CommandInfo.builder()
                .commandName("ping")
                .category(Category.TEST)
                .helpMessage("테스트 명령어")
                .build();
    }

    @Override
    public void handle(GuildMessageReceivedEvent event) {
        event.getChannel().sendMessage("Pong!").queue();
    }
}
