package com.neukrang.jybot.command.constraint;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public interface Constraint {

    boolean isValid(GuildMessageReceivedEvent event);
    String getErrorMessage();
}
