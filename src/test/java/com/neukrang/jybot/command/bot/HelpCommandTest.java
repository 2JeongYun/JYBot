package com.neukrang.jybot.command.bot;

import com.neukrang.jybot.command.skeleton.Category;
import com.neukrang.jybot.command.skeleton.ICommand;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.neukrang.jybot.TestUtil.*;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class HelpCommandTest {

    ICommand command1;
    ICommand command2;

    private HelpCommand helpCommand;
    private Map<String, ICommand> commandMap;

    @BeforeEach
    public void setUp() {
        commandMap = new HashMap<>();
        command1 = makeMockCommand("command1", Category.BOT, "helpMessage1", new ArrayList<>());
        command2 = makeMockCommand("command2", Category.BOT, "helpMessage2", new ArrayList<>());

        commandMap.put(commandBeanNameOf(command1.getCommandName()), command1);
        commandMap.put(commandBeanNameOf(command2.getCommandName()), command2);

        helpCommand = new HelpCommand(commandMap);
        helpCommand.init();
    }

    @DisplayName("!help")
    @Test
    public void helpAll() {
        //given
        GuildMessageReceivedEvent event = makeMockMsgEvent("!help");
        TextChannel textChannel = event.getChannel();

        //when
        helpCommand.handle(event);

        //then
        verify(textChannel).sendMessage(contains(command1.getHelpMessage()));
        verify(textChannel).sendMessage(contains(command2.getHelpMessage()));
    }

    @DisplayName("!help [명령어]")
    @Test
    public void helpOne() {
        //given
        ICommand target = command2;
        GuildMessageReceivedEvent event = makeMockMsgEvent("!help " + target.getCommandName());
        TextChannel textChannel = event.getChannel();

        //when
        helpCommand.handle(event);

        //then
        verify(textChannel).sendMessage(contains(target.getHelpMessage()));
        verify(textChannel, times(0)).sendMessage(contains(command1.getHelpMessage()));
    }

    @DisplayName("!help [명령어] 명령어가 없을 때 안내")
    @Test
    public void helpNotExist() {
        //given
        GuildMessageReceivedEvent event = makeMockMsgEvent("!help " + "notExist");
        TextChannel textChannel = event.getChannel();

        //when
        helpCommand.handle(event);

        //then
        verify(textChannel).sendMessage(contains("명령어가 없습니다."));
        verify(textChannel, times(0)).sendMessage(contains(command1.getHelpMessage()));
        verify(textChannel, times(0)).sendMessage(contains(command2.getHelpMessage()));
    }
}