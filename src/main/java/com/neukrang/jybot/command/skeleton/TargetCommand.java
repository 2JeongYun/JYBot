package com.neukrang.jybot.command.skeleton;

public abstract class TargetCommand extends Command {

    protected String[] getTargets(String content) {
        String[] words = content.split(" ");
        String[] targets = new String[words.length - 1];
        for (int i = 0; i < targets.length; i++) {
            targets[i] = words[i + 1];
        }
        return targets;
    }
}
