package com.drobot.task8.controller;

import com.drobot.task8.controller.command.ActionCommand;
import com.drobot.task8.type.CommandType;

import java.util.Optional;

public class CommandProvider {

    private CommandProvider() {
    }

    public static Optional<ActionCommand> defineCommand(String commandName) {
        try {
            CommandType type = CommandType.valueOf(commandName.toUpperCase());
            ActionCommand command = type.getCommand();
            return Optional.of(command);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
