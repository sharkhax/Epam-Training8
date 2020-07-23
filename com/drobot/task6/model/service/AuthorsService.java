package com.drobot.task6.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthorsService {

    private static final String REGEX = "\\b[a-zA-Z'.\\s-]+";

    public List<String> createAuthorsListFromString(String authors) {
        List<String> authorsList = new ArrayList<>();

        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(authors);

        int index = 0;
        while (matcher.find(index)) {
            authorsList.add(authors.substring(matcher.start(), matcher.end()));
            index = matcher.end();
        }
        return authorsList;
    }
}
