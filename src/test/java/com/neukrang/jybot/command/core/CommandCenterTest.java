package com.neukrang.jybot.command.core;

import com.neukrang.jybot.command.skeleton.ICommand;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.neukrang.jybot.TestUtil.makeMockCommand;
import static com.neukrang.jybot.TestUtil.makeMockMsgEvent;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class CommandCenterTest {

    private CommandCenter commandCenter;

    String commandName1 = "command1";
    String commandName2 = "command2";

    private List<ICommand> commandList;
    private CommonValidator mockCommonValidator;

    @BeforeEach
    public void setUp() {
        commandList = new ArrayList<>();
        commandList.add(makeMockCommand(commandName1));
        commandList.add(makeMockCommand(commandName2));

        mockCommonValidator = mock(CommonValidator.class);
        given(mockCommonValidator.isValid(anyList(), any(GuildMessageReceivedEvent.class))).willReturn(true);

        commandCenter = new CommandCenter(mockCommonValidator, commandList);
    }

    @DisplayName("커맨드 매핑")
    @Test
    void handle() {
        //given
        GuildMessageReceivedEvent event = makeMockMsgEvent("!" + commandName1);
        ICommand command = commandList.stream()
                .filter(c -> event.getMessage().getContentRaw().contains(c.getCommandName()))
                .findAny()
                .get();

        //when
        commandCenter.handle(event);

        //then
        verify(command).handle(event);
        verify(mockCommonValidator).isValid(anyList(), eq(event));
    }

    @DisplayName("커맨드 매핑 실패시 !help 메시지 안내")
    @Test
    void mappingFail() {
        //given
        GuildMessageReceivedEvent event = makeMockMsgEvent("!" + "notExist");
        TextChannel textChannel = event.getChannel();

        //when
        commandCenter.handle(event);

        //then
        verify(textChannel).sendMessage(contains("!help"));
    }
}