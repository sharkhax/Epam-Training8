package com.drobot.task8.model.dao;

import com.drobot.task8.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface Dao<T, E> {

    boolean add(E book) throws DaoException;
    boolean remove(int id) throws DaoException;
    List<E> findAll(T sortTag) throws DaoException;
    List<E> findById(int id) throws DaoException;
    List<E> find(T tag, T sortTag, String stringValue) throws DaoException;
    List<E> find(T tag, T sortTag, List<String> stringList) throws DaoException;

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
