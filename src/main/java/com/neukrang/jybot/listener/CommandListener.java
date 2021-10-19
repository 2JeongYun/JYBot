package com.neukrang.jybot.listener;

import com.neukrang.jybot.command.core.CommandCenter;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CommandListener extends ListenerAdapter {

    private final CommandCenter commandCenter;

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (!event.getMessage().getContentRaw().startsWith("!")) return;
        if (event.getAuthor().isBot()) return;

        commandCenter.handle(event);
    }
}
