package com.neukrang.jybot.listener;

import com.neukrang.jybot.command.core.CommandCenter;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CommandListener extends ListenerAdapter {

    private final CommandCenter commandCenter;
    @Value("${spring.profiles.active}")
    private String activeProfile;
    private final String PROD = "prod";

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (activeProfile.equals(PROD)) {
            if (!event.getMessage().getContentRaw().startsWith("!")) return;
        } else {
            if (!event.getMessage().getContentRaw().startsWith("?!")) return;
        }
        if (event.getAuthor().isBot()) return;

        commandCenter.handle(event);
    }
}
