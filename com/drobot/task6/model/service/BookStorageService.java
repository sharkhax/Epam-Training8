package com.drobot.task6.model.service;

import com.drobot.task6.exception.DaoException;
import com.drobot.task6.exception.ServiceException;
import com.drobot.task6.model.dao.BookListDao;
import com.drobot.task6.model.dao.impl.BookListDaoImpl;
import com.drobot.task6.model.entity.CustomBook;
import com.drobot.task6.model.validator.BookValidator;
import com.drobot.task6.type.CustomTag;

import java.util.ArrayList;
import java.util.List;

public class BookStorageService {

    public boolean addBook(String name, int releaseYear, int pages, List<String> authors) throws ServiceException {
        BookValidator bookValidator = new BookValidator();
        CustomBook book;
        BookListDao dao;
        boolean result = false;
        if (bookValidator.areFieldsValid(name, releaseYear, pages, authors)) {
            dao = new BookListDaoImpl();
            book = new CustomBook(name, releaseYear, pages, authors);
            try {
                result = dao.add(book);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return result;
    }

    public boolean removeBook(int id) throws ServiceException {
        boolean result;
        BookListDao dao = new BookListDaoImpl();
        List<CustomBook> books;
        try {
            books = dao.findById(id);
            if (books.isEmpty()) {
                result = false;
            } else {
                result = dao.remove(id);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    public List<CustomBook> findAll(CustomTag sortTag) throws ServiceException {
        BookListDao dao = new BookListDaoImpl();
        List<CustomBook> result;
        try {
            result = dao.findAll(sortTag);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    public List<CustomBook> findById(String stringId) throws ServiceException {
        BookListDao dao = new BookListDaoImpl();
        List<CustomBook> result;
        try {
            int id = Integer.parseInt(stringId);
            result = dao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        } catch (NumberFormatException e) {
            result = new ArrayList<>();
        }
        return result;
    }

    public List<CustomBook> find(CustomTag tag, CustomTag sortTag, String stringValue) throws ServiceException {
        BookListDao dao;
        List<CustomBook> result = new ArrayList<>();
        BookValidator bookValidator = new BookValidator();
        boolean isValid;
        try {
            switch (tag) {
                case ID -> throw new ServiceException("To find by ID use findById() method");
                case NAME -> isValid = bookValidator.isNameValid(stringValue);
                case RELEASE_YEAR -> isValid = bookValidator.isReleaseYearValid(stringValue);
                case PAGES -> isValid = bookValidator.arePagesValid(stringValue);
                case AUTHORS -> throw new ServiceException("Invalid data type: required List<String>");
                default -> throw new ServiceException("Unsupported tag");
            }
        } catch (NumberFormatException e) {
            return result;
        }
        if (isValid) {
            try {
                dao = new BookListDaoImpl();
                result = dao.find(tag, sortTag, stringValue);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return result;
    }

    public List<CustomBook> find(CustomTag tag, CustomTag sortTag, List<String> stringList) throws ServiceException {
        BookListDao dao;
        List<CustomBook> result = new ArrayList<>();
        BookValidator bookValidator = new BookValidator();
        boolean isValid;
        switch (tag) {
            case ID -> throw new ServiceException("To find by ID use findById() method");
            case NAME, RELEASE_YEAR, PAGES -> throw new ServiceException("Invalid data type: required String");
            case AUTHORS -> isValid = bookValidator.areAuthorsValid(stringList);
            default -> throw new ServiceException("Unsupported tag");
        }
        if (isValid) {
            try {
                dao = new BookListDaoImpl();
                result = dao.find(tag, sortTag, stringList);
            } catch (DaoException e) {
                throw new ServiceException(e);
            }
        }
        return result;
    }
}
