package com.drobot.task6.controller.command;

import com.drobot.task6.exception.CommandException;
import com.drobot.task6.model.entity.CustomBook;

import java.util.List;
import java.util.Optional;

public interface ActionCommand {
        Optional<List<CustomBook>> execute(String ... params) throws CommandException;
}
