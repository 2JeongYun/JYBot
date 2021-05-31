package com.neukrang.jybot.command.skeleton;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public abstract class Command implements ICommand {

    @Override
    public abstract void handle(GuildMessageReceivedEvent event);

    @Override
    public abstract String getHelp();

    @Override
    public abstract String getName();

    public boolean isValidFormat(GuildMessageReceivedEvent event) {
        return true;
    };

    public void handleFormatError(TextChannel textChannel) {
        if (this instanceof SingleCommand) {
            textChannel.sendMessage("단독으로 사용되는 명령어 입니다. '!명령어' 만 입력해주세요.").queue();
        } else if (this instanceof TargetCommand) {
            textChannel.sendMessage("입력값이 필요한 명령어 입니다. '!명령어 값' 을 입력해주세요.").queue();
        }
    }

    protected void handleError(GuildMessageReceivedEvent event) {}
}
