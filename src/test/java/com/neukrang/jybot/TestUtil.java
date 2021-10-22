package com.neukrang.jybot;

import com.neukrang.jybot.command.constraint.IConstraint;
import com.neukrang.jybot.command.skeleton.Category;
import com.neukrang.jybot.command.skeleton.ICommand;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class TestUtil {

    public static ICommand makeMockCommand(String name,
                                    Category category,
                                    String helpMessage,
                                    List<String> constraintList) {
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

    public static ICommand makeMockCommand(String name, List<String> constraintList) {
        return makeMockCommand(name, Category.TEST, name + "helpMessage", constraintList);
    }

    public static IConstraint makeMockConstraint(boolean isValid, String errorMessage) {
        IConstraint constraint = mock(IConstraint.class);
        given(constraint.isValid(any())).willReturn(isValid);
        given(constraint.getErrorMessage()).willReturn(errorMessage);

        return constraint;
    }
}
