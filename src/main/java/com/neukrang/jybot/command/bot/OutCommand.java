package com.neukrang.jybot.command.bot;

import com.neukrang.jybot.command.constraint.BotInChannel;
import com.neukrang.jybot.command.constraint.NoTarget;
import com.neukrang.jybot.command.skeleton.Category;
import com.neukrang.jybot.command.skeleton.Command;
import com.neukrang.jybot.command.skeleton.CommandInfo;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OutCommand extends Command {

    @Override
    protected CommandInfo createCommandInfo() {
        return CommandInfo.builder()
                .commandName("out")
                .category(Category.BOT)
                .helpMessage("!out\n" + "봇을 음성채널에서 퇴장시킵니다.")
                .constraintList(List.of(NoTarget.class, BotInChannel.class))
                .build();
    }

    @Override
    public void handle(GuildMessageReceivedEvent event) {
        event.getGuild().getAudioManager().closeAudioConnection();
    }
}
