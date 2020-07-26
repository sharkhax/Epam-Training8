package com.drobot.task8.controller.command.impl;

import com.drobot.task8.controller.command.ActionCommand;
import com.drobot.task8.exception.CommandException;
import com.drobot.task8.exception.ServiceException;
import com.drobot.task8.model.entity.CustomBook;
import com.drobot.task8.model.service.BookStorageService;
import com.drobot.task8.type.CustomTag;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class FindCommand implements ActionCommand {

    private static final int TAG_POSITION = 0;
    private static final int SORT_TAG_POSITION = 1;
    private static final int VALUE_POSITION = 2;
    private static final int ID_POSITION = 1;

    @Override
    public Optional<List<CustomBook>> execute(String... params) throws CommandException {
        List<CustomBook> result;
        CustomTag tag;
        CustomTag sortTag;
        BookStorageService storageService;
        if (params.length >= 2) {
            try {
                tag = CustomTag.valueOf(params[TAG_POSITION].toUpperCase());
                if (tag == CustomTag.ID) {
                    String id = params[ID_POSITION];
                    storageService = new BookStorageService();
                    result = storageService.findById(id);
                } else if (tag == CustomTag.AUTHORS && params.length >= 3) {
                    sortTag = CustomTag.valueOf(params[SORT_TAG_POSITION].toUpperCase());
                    List<String> authors = Arrays.asList(params).subList(VALUE_POSITION, params.length);
                    storageService = new BookStorageService();
                    result = storageService.find(tag, sortTag, authors);
                } else if (params.length == 3) {
                    sortTag = CustomTag.valueOf(params[SORT_TAG_POSITION].toUpperCase());
                    String value = params[VALUE_POSITION];
                    storageService = new BookStorageService();
                    result = storageService.find(tag, sortTag, value);
                } else {
                    throw new CommandException("Invalid input");
                }
            } catch (IllegalArgumentException e) {
                throw new CommandException("Invalid tag");
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        } else {
            throw new CommandException("Invalid input");
        }
        return Optional.of(result);
    }
}
