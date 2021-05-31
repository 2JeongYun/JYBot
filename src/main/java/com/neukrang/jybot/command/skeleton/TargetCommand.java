package com.neukrang.jybot.command.skeleton;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public abstract class TargetCommand extends Command {

    @Override
    public boolean isValidFormat(GuildMessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        String[] words = content.split(" ");

        if (words.length <= 1) {
            return false;
        }
        return true;
    }
}
