package com.neukrang.jybot.command;

import com.neukrang.jybot.command.skeleton.Category;
import com.neukrang.jybot.command.skeleton.Command;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TestCommand extends Command {

    @PostConstruct
    @Override
    protected void init() {
        commandName = "ping";
        category = Category.TEST;

        helpMessage = "테스트 명령어";
    }

    @Override
    public void handle(GuildMessageReceivedEvent event) {
        event.getChannel().sendMessage("Pong!").queue();
    }
}
