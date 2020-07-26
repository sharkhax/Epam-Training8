package com.drobot.task8.controller.command.impl;

import com.drobot.task8.controller.command.ActionCommand;
import com.drobot.task8.exception.CommandException;
import com.drobot.task8.exception.ServiceException;
import com.drobot.task8.model.entity.CustomBook;
import com.drobot.task8.model.service.BookStorageService;

import java.util.List;
import java.util.Optional;

public class RemoveCommand implements ActionCommand {

    private static final int ID_POSITION = 0;

    @Override
    public Optional<List<CustomBook>> execute(String... params) throws CommandException {
        Optional<List<CustomBook>> result = Optional.empty();
        if (params.length == 1) {
            BookStorageService storageService;
            String stringId = params[ID_POSITION];
            int id;
            try {
                id = Integer.parseInt(stringId);
            } catch (IllegalArgumentException e) {
                return result;
            }
            storageService = new BookStorageService();
            try {
                if (storageService.removeBook(id)) {
                    result = Optional.of(List.of());
                }
            } catch (ServiceException e) {
                throw new CommandException(e.getMessage());
            }
        } else {
            throw new CommandException("Invalid input");
        }
        return result;
    }
}
