package com.drobot.task8.type;

public enum CustomTag {
    ID(new StringBuilder("SELECT bookId, name, releaseYear, pages, authors FROM book.books WHERE bookId = ?"),
            "bookId"),
    NAME(new StringBuilder("SELECT bookId, name, releaseYear, pages, authors FROM book.books WHERE name = ? ORDER BY "),
            "name"),
    RELEASE_YEAR(new StringBuilder("SELECT bookId, name, releaseYear, pages, authors FROM book.books WHERE releaseYear = ? ORDER BY "),
            "releaseYear"),
    PAGES(new StringBuilder("SELECT bookId, name, releaseYear, pages, authors FROM book.books WHERE pages = ? ORDER BY "),
            "pages"),
    AUTHORS(new StringBuilder("SELECT bookId, name, releaseYear, pages, authors FROM book.books WHERE authors = ? ORDER BY "),
            "authors");

    private final StringBuilder findStatement;
    private final String fieldName;

    CustomTag(StringBuilder findStatement, String fieldName) {
        this.findStatement = findStatement;
        this.fieldName = fieldName;
    }

    public StringBuilder getFindStatement() {
        return findStatement;
    }

    public String getFieldName() {
        return fieldName;
    }
}
