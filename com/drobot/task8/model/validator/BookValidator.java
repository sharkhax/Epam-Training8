package com.drobot.task8.model.validator;

import java.time.Year;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookValidator {

    private static final int MAX_NAME_LENGTH = 500;
    private static final int MIN_RELEASE_YEAR = 868;
    private static final int MAX_RELEASE_YEAR = Year.now().getValue();
    private static final int MIX_PAGES = 1;
    private static final int MAX_PAGES = 6500;
    private static final int MAX_AUTHORS = 10;
    private static final int MAX_AUTHOR_LENGTH = 50;
    private static final String AUTHOR_REGEX_PART1 = "\\b[a-zA-Z'.\\s-]{1,";
    private static final String AUTHOR_REGEX_PART2 = "}";
    private static final String AUTHOR_REGEX = AUTHOR_REGEX_PART1 + MAX_AUTHOR_LENGTH + AUTHOR_REGEX_PART2;

    public boolean isNameValid(String name) {
        return (name != null && name.length() < MAX_NAME_LENGTH);
    }

    public boolean isReleaseYearValid(int releaseYear) {
        return (releaseYear >= MIN_RELEASE_YEAR && releaseYear <= MAX_RELEASE_YEAR);
    }
    public boolean isReleaseYearValid(String stringReleaseYear) {
        try {
            int releaseYear = Integer.parseInt(stringReleaseYear);
            return isReleaseYearValid(releaseYear);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean arePagesValid(int pages) {
        return (pages > MIX_PAGES && pages < MAX_PAGES);
    }

    public boolean arePagesValid(String stringPages) {
        try {
            int pages = Integer.parseInt(stringPages);
            return arePagesValid(pages);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean areFieldsValid(String name, int releaseYear, int pages, List<String> authors) {
        return isNameValid(name) && isReleaseYearValid(releaseYear) && arePagesValid(pages)
                && areAuthorsValid(authors);
    }

    public boolean areAuthorsValid(List<String> authors) {
        boolean result = false;
        if (authors != null && !authors.isEmpty() && authors.size() <= MAX_AUTHORS) {
            result = true;
            for (String actualAuthor : authors) {
                if (!isAuthorValid(actualAuthor)) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    private boolean isAuthorValid(String author) {
        boolean result = false;
        if (author != null) {
            Pattern pattern = Pattern.compile(AUTHOR_REGEX);
            Matcher matcher = pattern.matcher(author);
            result = matcher.matches();
        }
        return result;
    }
}