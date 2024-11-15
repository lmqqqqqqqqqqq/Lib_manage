package com.example.javafx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdvancedSearch extends SearchController {
    public final static List<String> lang = Arrays.asList("English", "Spanish", "French", "German"
            , "Russian", "Vietnamese", "Standard Arabic", "Hindi", "Chinese");
    public final static List<String> SortBy = Arrays.asList("Newest first", "Oldest first");

    @Override
    public List<Books> search(String query, List<Object> params, Connection connect) throws SQLException {
        PreparedStatement stm = connect.prepareStatement(query);
        for (int i = 0; i < params.size(); i++) {
            stm.setObject(i + 1, params.get(i));
        }
        ResultSet rs = stm.executeQuery();
        List<Books> result = new ArrayList<>();
        while (rs.next()) {
            String title = rs.getString("title");
            String author = rs.getString("author");
            String publisher = rs.getString("publisher");
            String isbn = rs.getString("ISBN");
            String subject = rs.getString("subject");
            String description = rs.getString("description");
            String id = rs.getString("idbooks");
            String language = rs.getString("language");
            String year = rs.getString("created_date");
            Books bok = new Books(id, title, description, author, subject, publisher, isbn, language, year);
            result.add(bok);
        }

        return result;
    }

    /**
     * bug when choose only sort by and when everything is null select the newest first.
     * process to get query from database.
     * @param title
     * @param author
     * @param subject
     * @param publisher
     * @param isbn
     * @param language
     * @param year
     * @param sortBy
     * @param params
     * @return query.
     */
    public String process(String title, String author, String subject
            , String publisher, String isbn, String language
            , String year, String sortBy, List<Object> params) {
        StringBuilder Q = new StringBuilder("SELECT * FROM books WHERE 1=1");

        if (title != null && !title.isEmpty()) {
            Q.append(" AND title LIKE ?");
            params.add("%" + title + "%");
        }
        if (author != null && !author.isEmpty()) {
            Q.append(" AND author LIKE ?");
            params.add("%" + author + "%");
        }
        if (subject != null && !subject.isEmpty()) {
            Q.append(" AND subject LIKE ?");
            params.add("%" + subject + "%");
        }
        if (publisher != null && !publisher.isEmpty()) {
            Q.append(" AND publisher LIKE ?");
            params.add("%" + publisher + "%");
        }
        if (isbn != null && !isbn.isEmpty()) {
            Q.append(" AND isbn = ?");
            params.add(isbn);
        }
        if (year != null && !year.isEmpty()) {
            Q.append(" AND YEAR(created_date) = ?");
            params.add(year);
        }
        if (sortBy != null && !sortBy.equals("Sort by")) {
            if(sortBy.equals("Newest first")) {
                Q.append(" ORDER BY created_date DESC");
            } else {
                Q.append(" ORDER BY created_date ASC");
            }
        }
        if (language != null && !language.equals("Language")) {
            Q.append(" AND language = ?");
            params.add(language);
        }
        Q.append(" AND is_deleted = 0");
        return Q.toString();
    }
}

