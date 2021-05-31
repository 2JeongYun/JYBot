package com.neukrang.jybot.command;

import com.neukrang.jybot.command.skeleton.Command;
import com.neukrang.jybot.command.skeleton.SingleCommand;
import com.neukrang.jybot.command.skeleton.TargetCommand;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Map;

public class FormatChecker {

    private final Map<String, Command> commandMap;

    public FormatChecker(Map<String, Command> commandMap) {
        this.commandMap = commandMap;
    }

    public boolean isValidFormat(Command command, GuildMessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        String[] words = content.split(" ");

        if (command instanceof SingleCommand) {
            if (words.length >= 2)
                return false;
        } else if (command instanceof TargetCommand) {
            if (words.length <= 1)
                return false;
        }
        return true;
    }

    public void sendFormatErrorMessage(Command command, TextChannel channel) {
        if (command instanceof SingleCommand) {
            channel.sendMessage("단독으로 사용되는 명령어 입니다. '!명령어' 만 입력해주세요.").queue();
        } else if (command instanceof TargetCommand) {
            channel.sendMessage("입력값이 필요한 명령어 입니다. '!명령어 값' 을 입력해주세요.").queue();
        }
    }
}
