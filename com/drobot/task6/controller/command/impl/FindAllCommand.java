package com.drobot.task6.controller.command.impl;

import com.drobot.task6.controller.command.ActionCommand;
import com.drobot.task6.exception.CommandException;
import com.drobot.task6.exception.ServiceException;
import com.drobot.task6.model.entity.CustomBook;
import com.drobot.task6.model.service.BookStorageService;
import com.drobot.task6.type.CustomTag;

import java.util.List;
import java.util.Optional;

public class FindAllCommand implements ActionCommand {

    private static final int SORT_TAG_POSITION = 0;

    @Override
    public Optional<List<CustomBook>> execute(String... params) throws CommandException {
        List<CustomBook> result;
        if (params.length == 1) {
            CustomTag sortTag;
            BookStorageService storageService;
            try {
                sortTag = CustomTag.valueOf(params[SORT_TAG_POSITION].toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new CommandException("Invalid tag");
            }
            storageService = new BookStorageService();
            try {
                result = storageService.findAll(sortTag);
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        } else {
            throw new CommandException("Invalid input");
        }
        return Optional.of(result);
    }
}
