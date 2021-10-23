package com.neukrang.jybot;

import com.neukrang.jybot.command.constraint.IConstraint;
import com.neukrang.jybot.command.skeleton.Category;
import com.neukrang.jybot.command.skeleton.ICommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class TestUtil {

    public static ICommand makeMockCommand(String name,
                                    Category category,
                                    String helpMessage,
                                    List<Class> constraintList) {
        ICommand command = mock(ICommand.class);
        given(command.getCommandName()).willReturn(name);
        given(command.getCategory()).willReturn(category);
        given(command.getHelpMessage()).willReturn(helpMessage);
        given(command.getConstraintList()).willReturn(constraintList);

        return command;
    }

    public static ICommand makeMockCommand(String name) {
        return makeMockCommand(name, Category.TEST, name + "helpMessage", new ArrayList<>());
    }

    public static ICommand makeMockCommand(String name, List<Class> constraintList) {
        return makeMockCommand(name, Category.TEST, name + "helpMessage", constraintList);
    }

    public static IConstraint makeMockConstraint(boolean isValid, String errorMessage) {
        IConstraint constraint = mock(IConstraint.class);
        given(constraint.isValid(any())).willReturn(isValid);
        given(constraint.getErrorMessage()).willReturn(errorMessage);

        return constraint;
    }

    public static GuildMessageReceivedEvent makeMockMsgEvent(String message) {
        GuildMessageReceivedEvent event = mock(GuildMessageReceivedEvent.class);
        TextChannel textChannel = mock(TextChannel.class);
        MessageAction messageAction = mock(MessageAction.class);
        Message messageObj = mock(Message.class);

        given(event.getMessage()).willReturn(messageObj);
        given(messageObj.getContentRaw()).willReturn(message);
        given(event.getChannel()).willReturn(textChannel);
        given(textChannel.sendMessage(anyString())).willReturn(messageAction);

        return event;
    }

    public static String commandBeanNameOf(String commandName) {
        return commandName + "Command";
    }
}
