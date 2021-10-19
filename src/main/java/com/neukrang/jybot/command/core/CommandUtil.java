package com.neukrang.jybot.command.core;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;

public class CommandUtil {

    public static List<String> parse(GuildMessageReceivedEvent event) {
        ArrayList<String> ret = new ArrayList<>();
        String content = event.getMessage().getContentRaw();
        String[] contents = content.split(" ");

        for (int i = 1; i < contents.length; i++) {
            ret.add(contents[i]);
        }
        return ret;
    }
}
