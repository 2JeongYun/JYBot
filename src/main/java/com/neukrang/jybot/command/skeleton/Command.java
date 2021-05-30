package com.neukrang.jybot.command.skeleton;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public abstract class Command implements ICommand {

    @Override
    public void handle(GuildMessageReceivedEvent event) {
        if (!isValidFormat(event)) {
            handleError(event);
            return;
        }
    };

    @Override
    public abstract String getHelp();

    @Override
    public abstract String getName();

    protected abstract boolean isValidFormat(GuildMessageReceivedEvent event);

    protected void handleError(GuildMessageReceivedEvent event) {}
}
