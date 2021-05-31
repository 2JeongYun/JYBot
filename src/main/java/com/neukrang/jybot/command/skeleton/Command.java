package com.neukrang.jybot.command.skeleton;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public abstract class Command implements ICommand {

    @Override
    public abstract void handle(GuildMessageReceivedEvent event);

    @Override
    public abstract String getHelp();

    @Override
    public abstract String getName();

    protected void handleError(GuildMessageReceivedEvent event) {}
}
