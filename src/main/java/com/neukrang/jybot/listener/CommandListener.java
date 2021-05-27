package com.neukrang.jybot.listener;

import com.neukrang.jybot.command.CommandMapper;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@RequiredArgsConstructor
public class CommandListener extends ListenerAdapter {

    private final CommandMapper commandMapper;

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (!event.getMessage().getContentRaw().startsWith("!")) return;
        if (event.getAuthor().isBot()) return;

        commandMapper.handle(event);
    }
}
