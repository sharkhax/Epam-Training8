package com.drobot.task8.controller;

import com.drobot.task8.controller.command.ActionCommand;
import com.drobot.task8.exception.CommandException;
import com.drobot.task8.model.entity.CustomBook;

import java.util.List;
import java.util.Optional;

public class Invoker {
    public Optional<List<CustomBook>> processRequest(String commandName, String... params)
            throws CommandException {
        Optional<ActionCommand> optionalCommand = CommandProvider.defineCommand(commandName);
        Optional<List<CustomBook>> result = Optional.empty();
        if (optionalCommand.isPresent()) {
            ActionCommand command = optionalCommand.get();
            result = command.execute(params);
        }
        return result;
    }
}
