package com.neukrang.jybot.command.skeleton;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.annotation.PostConstruct;
import java.util.List;

public abstract class Command implements ICommand {

    protected CommandInfo commandInfo;

    @PostConstruct
    protected void setCommandInfo() {
        this.commandInfo = createCommandInfo();
    }

    protected abstract CommandInfo createCommandInfo();

    @Override
    public abstract void handle(GuildMessageReceivedEvent event);

    @Override
    public List<Class> getConstraintList() {
        return commandInfo.getConstraintList();
    }

    @Override
    public String getHelpMessage() {
        return commandInfo.getHelpMessage();
    }

    @Override
    public String getCommandName() {
        return commandInfo.getCommandName();
    }

    @Override
    public Category getCategory() {
        return commandInfo.getCategory();
    }
}
