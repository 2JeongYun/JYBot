package com.neukrang.jybot.command;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.stereotype.Component;

@Component
public class TestCommand implements ICommand {

    @Override
    public void handle(GuildMessageReceivedEvent event) {
        event.getChannel().sendMessage("Pong!").queue();
    }

    @Override
    public String getHelp() {
        return null;
    }

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public void handleError(GuildMessageReceivedEvent event) {}
}
