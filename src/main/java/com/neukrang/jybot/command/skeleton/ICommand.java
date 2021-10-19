package com.neukrang.jybot.command.skeleton;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public interface ICommand {

    void handle(GuildMessageReceivedEvent event);
    List<String> getConstraintList();
    String getHelpMessage();
    String getCommandName();
    Category getCategory();
}
