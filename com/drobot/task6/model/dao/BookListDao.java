package com.drobot.task6.model.dao;

import com.drobot.task6.exception.DaoException;
import com.drobot.task6.model.entity.CustomBook;
import com.drobot.task6.type.CustomTag;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface BookListDao {

    boolean add(CustomBook book) throws DaoException;
    boolean remove(int id) throws DaoException;
    List<CustomBook> findAll(CustomTag sortTag) throws DaoException;
    List<CustomBook> findById(int id) throws DaoException;
    List<CustomBook> find(CustomTag tag, CustomTag sortTag, String stringValue) throws DaoException;
    List<CustomBook> find(CustomTag tag, CustomTag sortTag, List<String> stringList) throws DaoException;

    default boolean closeStatement(Statement statement) {
        boolean result = false;
        try {
            if (statement != null) {
                statement.close();
                result = true;
            }
        } catch (SQLException e) {
            System.out.println("Statement wasn't closed");
        }
        return result;
    }

    default boolean closeConnection(Connection connection) {
        boolean result = false;
        try {
            if (connection != null) {
                connection.close();
                result = true;
            }
        } catch (SQLException e) {
            System.out.println("Connection wasn't closed");
        }
        return result;
    }
}
