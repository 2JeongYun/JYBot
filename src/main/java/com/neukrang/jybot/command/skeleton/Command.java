package com.neukrang.jybot.command.skeleton;

import lombok.Getter;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class Command implements ICommand {

    protected String commandName = "";
    protected Category category = Category.NONE;
    protected String helpMessage = "";
    protected List<String> constraintList = new ArrayList<>();

    @Override
    public abstract void handle(GuildMessageReceivedEvent event);

    protected abstract void init();
}
