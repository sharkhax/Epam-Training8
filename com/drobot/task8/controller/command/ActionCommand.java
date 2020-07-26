package com.drobot.task8.controller.command;

import com.drobot.task8.exception.CommandException;
import com.drobot.task8.model.entity.CustomBook;

import java.util.List;
import java.util.Optional;

public interface ActionCommand {
        Optional<List<CustomBook>> execute(String ... params) throws CommandException;
}
