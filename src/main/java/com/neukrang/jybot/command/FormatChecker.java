package com.neukrang.jybot.command;

import com.neukrang.jybot.command.skeleton.Command;
import com.neukrang.jybot.command.skeleton.SingleCommand;
import com.neukrang.jybot.command.skeleton.TargetCommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class FormatChecker {

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

    public boolean isBothInSameVoiceChannel(Command command, GuildMessageReceivedEvent event) {
        Guild guild = event.getGuild();
        GuildVoiceState memberState = guild.getMember(event.getAuthor()).getVoiceState();
        GuildVoiceState botState = guild.getSelfMember().getVoiceState();

        if (!memberState.inVoiceChannel() ||
                !botState.inVoiceChannel()) {
            command.handleError(event);
            return false;
        }
        if (memberState.getChannel().getIdLong() !=
                botState.getChannel().getIdLong()) {
            command.handleError(event);
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

    public void sendNotInSameChannelErrorMessage(TextChannel channel) {
        channel.sendMessage("유저와 봇이 같은 음성 채널에 접속중이여야 합니다.\n").queue();
    }
}
