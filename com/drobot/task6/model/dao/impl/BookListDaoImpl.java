package com.drobot.task6.model.dao.impl;

import com.drobot.task6.exception.DaoException;
import com.drobot.task6.model.dao.BookListDao;
import com.drobot.task6.model.entity.CustomBook;
import com.drobot.task6.model.service.AuthorsService;
import com.drobot.task6.type.CustomTag;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookListDaoImpl implements BookListDao {

    private static final String URL = "jdbc:mysql://localhost:3306";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String ADD_STATEMENT =
            "INSERT INTO book.books(name, releaseYear, pages, authors) VALUES(?, ?, ?, ?);";
    private static final String CONTAINS_STATEMENT =
            "SELECT bookId FROM book.books WHERE(name = ? AND releaseYear = ? AND pages = ? AND authors = ?);";
    private static final String DELETE_STATEMENT = "DELETE FROM book.books WHERE bookId = ?;";
    private static final StringBuilder FIND_ALL_STATEMENT = new StringBuilder(
            "SELECT bookId, name, releaseYear, pages, authors FROM book.books ORDER BY ");
    private static final String FIND_BY_ID_STATEMENT =
            "SELECT bookId, name, releaseYear, pages, authors FROM book.books WHERE bookId = ?;";

    @Override
    public boolean add(CustomBook book) throws DaoException {
        boolean result = false;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            statement = connection.prepareStatement(ADD_STATEMENT);
            if (!contains(book, connection)) {
                fillStatement(book, statement);
                statement.execute();
                result = true;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return result;
    }

    @Override
    public boolean remove(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
          connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
          statement = connection.prepareStatement(DELETE_STATEMENT);
          statement.setInt(1, id);
          statement.execute();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return true;
    }

    @Override
    public List<CustomBook> findAll(CustomTag sortTag) throws DaoException {
        List<CustomBook> booksList;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            statement = connection.createStatement();
            FIND_ALL_STATEMENT.append(sortTag.getFieldName());
            resultSet = statement.executeQuery(FIND_ALL_STATEMENT.toString());
            booksList = createBooksListFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return booksList;
    }

    @Override
    public List<CustomBook> findById(int id) throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet;
        List<CustomBook> result;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            statement = connection.prepareStatement(FIND_BY_ID_STATEMENT);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            result = createBooksListFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return result;
    }

    @Override
    public List<CustomBook> find(CustomTag tag, CustomTag sortTag, String value) throws DaoException {
        if (tag == CustomTag.ID) {
            throw new DaoException("To find by ID use findById() method");
        }
        List<CustomBook> result;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet;
        String field = sortTag.getFieldName();
        String sqlCommand = tag.getFindStatement().append(field).toString();
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            statement = connection.prepareStatement(sqlCommand);
            statement.setString(1, value);
            resultSet = statement.executeQuery();
            result = createBooksListFromResultSet(resultSet);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
            closeConnection(connection);
        }
        return result;
    }

    @Override
    public List<CustomBook> find(CustomTag tag, CustomTag sortTag, List<String> stringList) throws DaoException {
        String stringValue = stringList.toString();
        return find(tag, sortTag, stringValue);
    }

    private boolean contains(CustomBook book, Connection connection) throws DaoException {
        PreparedStatement statement = null;
        ResultSet resultSet;
        boolean result = false;
        try {
            statement = connection.prepareStatement(CONTAINS_STATEMENT, PreparedStatement.RETURN_GENERATED_KEYS);
            fillStatement(book, statement);
            resultSet = statement.executeQuery();
            resultSet.next();
            if (resultSet.getRow() != 0) {
                result = true;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            closeStatement(statement);
        }
        return result;
    }

    private List<CustomBook> createBooksListFromResultSet(ResultSet resultSet) throws DaoException {
        List<CustomBook> result = new ArrayList<>();
        AuthorsService authorsService;
        try {
            while (resultSet.next()) {
                authorsService = new AuthorsService();
                int bookId = resultSet.getInt("bookId");
                String name = resultSet.getString("name");
                String stringReleaseYear = resultSet.getString("releaseYear");
                String stringPages = resultSet.getString("pages");
                String authors = resultSet.getString("authors");
                List<String> authorsList = authorsService.createAuthorsListFromString(authors);
                int releaseYear = Integer.parseInt(stringReleaseYear);
                int pages = Integer.parseInt(stringPages);
                CustomBook book = new CustomBook(bookId, name, releaseYear, pages, authorsList);
                result.add(book);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return result;
    }

    private boolean fillStatement(CustomBook book, PreparedStatement statement) throws DaoException {
        try {
            String name = book.getName();
            int releaseYear = book.getReleaseYear();
            int pages = book.getPages();
            String authors = book.getAuthors().toString();
            statement.setString(1, name);
            statement.setString(2, String.valueOf(releaseYear));
            statement.setString(3, String.valueOf(pages));
            statement.setString(4, authors);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return true;
    }
}
