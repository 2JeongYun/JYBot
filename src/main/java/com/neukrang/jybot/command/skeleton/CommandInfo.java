package com.neukrang.jybot.command.skeleton;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class CommandInfo {

    @NonNull
    private String commandName;
    @NonNull
    private String helpMessage;
    @NonNull
    private Category category;
    @Builder.Default
    private List<Class> constraintList = new ArrayList<>();
}
