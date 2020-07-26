package com.drobot.task8.model.entity;

import java.util.List;

public class CustomBook {

    private int bookId;
    private String name;
    private int releaseYear;
    private int pages;
    private List<String> authors;

    public CustomBook(String name, int releaseYear, int pages, List<String> authors) {
        this.name = name;
        this.releaseYear = releaseYear;
        this.pages = pages;
        this.authors = authors;
    }

    public CustomBook(int bookId, String name, int releaseYear, int pages, List<String> authors) {
        this.bookId = bookId;
        this.name = name;
        this.releaseYear = releaseYear;
        this.pages = pages;
        this.authors = authors;
    }

    public String getName() {
        return name;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public int getPages() {
        return pages;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomBook that = (CustomBook) o;
        if (releaseYear != that.releaseYear) {
            return false;
        }
        if (pages != that.pages) {
            return false;
        }
        if (!name.equals(that.name)) {
            return false;
        }
        return authors.equals(that.authors);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + releaseYear;
        result = 31 * result + pages;
        result = 31 * result + authors.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CustomBook{");
        sb.append("bookId=").append(bookId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", releaseYear=").append(releaseYear);
        sb.append(", pages=").append(pages);
        sb.append(", authors='").append(authors).append('\'');
        sb.append('}');
        return sb.toString();
    }
}