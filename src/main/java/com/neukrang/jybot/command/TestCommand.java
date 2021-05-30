package com.neukrang.jybot.command;

import com.neukrang.jybot.command.skeleton.SingleCommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.stereotype.Component;

@Component
public class TestCommand extends SingleCommand {

    @Override
    public void handle(GuildMessageReceivedEvent event) {
        event.getChannel().sendMessage("Pong!").queue();
    }

    @Override
    public String getHelp() {
        return "테스트 명령어";
    }

    @Override
    public String getName() {
        return "ping";
    }
}
