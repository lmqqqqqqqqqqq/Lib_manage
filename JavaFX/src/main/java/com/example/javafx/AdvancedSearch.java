package com.example.javafx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdvancedSearch {
    public final static List<String> lang = Arrays.asList("en (English)", "es (Spanish)", "fr (French)", "de (German)"
            , "ru (Russian)", "vi (Vietnamese)", "hi (Hindi)", "zh (Chinese)", "ja (Japanese)", "ko (Korean)");
    public final static List<String> SortBy = Arrays.asList("newest", "oldest");

    public static List<Books> search(String query, List<Object> params, Connection connect) throws SQLException {
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
            String genre = rs.getString("genre");
            String description = rs.getString("description");
            String id = rs.getString("idbooks");
            String language = rs.getString("language");
            String year = rs.getString("created_date");
            String image = rs.getString("image");
            String rating = rs.getString("rating");
            int views = rs.getInt("views");
            Books bok = new Books(id, title, description, author, genre, publisher, isbn, language, year, image, rating, false, views);
            result.add(bok);
        }

        return result;
    }

    /**
     * excute query with only 1 param. ( Using for yourBook)
     * @param query is query.
     * @param o is o.
     * @param connect is connection.
     * @return a.
     * @throws SQLException is excepyion.
     */
    public static List<Books> search(String query, Object o, Connection connect) throws SQLException {
        PreparedStatement stm = connect.prepareStatement(query);
        stm.setObject(1, o);
        ResultSet rs = stm.executeQuery();
        List<Books> result = new ArrayList<>();
        while (rs.next()) {
            String title = rs.getString("title");
            String author = rs.getString("author");
            String publisher = rs.getString("publisher");
            String isbn = rs.getString("ISBN");
            String genre = rs.getString("genre");
            String description = rs.getString("description");
            String id = rs.getString("idbooks");
            String language = rs.getString("language");
            String year = rs.getString("created_date");
            String image = rs.getString("image");
            String rating = rs.getString("rating");
            int views = rs.getInt("views");
            Books bok = new Books(id, title, description, author, genre, publisher, isbn, language, year, image, rating, false, views);
            result.add(bok);
        }

        return result;
    }

    public static List<Books> search(String Query, Connection connect) throws SQLException {
        PreparedStatement stm = connect.prepareStatement(Query);
        ResultSet rs = stm.executeQuery();
        List<Books> result = new ArrayList<>();
        while (rs.next()) {
            String title = rs.getString("title");
            String author = rs.getString("author");
            String publisher = rs.getString("publisher");
            String isbn = rs.getString("ISBN");
            String genre = rs.getString("genre");
            String description = rs.getString("description");
            String id = rs.getString("idbooks");
            String language = rs.getString("language");
            String year = rs.getString("created_date");
            String image = rs.getString("image");
            String rating = rs.getString("rating");
            int views = rs.getInt("views");
            Books bok = new Books(id, title, description, author, genre, publisher, isbn, language, year, image,rating, false, views);
            result.add(bok);
        }
        return result;
    }

    /**
     * when everything is null select the newest first.
     * process to get query from database.
     * @param title .
     * @param author .
     * @param genre .
     * @param publisher .
     * @param isbn .
     * @param language .
     * @param year .
     * @param sortBy .
     * @param params .
     * @return query.
     */
    public static String process(String title, String author, String genre
            , String publisher, String isbn, String language
            , String year, String sortBy, List<Object> params) {

        boolean normalSearch = true;

        StringBuilder Q = new StringBuilder("SELECT * FROM books WHERE 1=1 AND is_deleted=0");

        if (title != null && !title.isEmpty()) {
            Q.append(" AND title LIKE ?");
            params.add("%" + title + "%");
            normalSearch = false;
        }
        if (author != null && !author.isEmpty()) {
            Q.append(" AND author LIKE ?");
            params.add("%" + author + "%");
            normalSearch = false;
        }
        if (genre != null && !genre.isEmpty()) {
            Q.append(" AND genre LIKE ?");
            params.add("%" + genre + "%");
            normalSearch = false;
        }
        if (publisher != null && !publisher.isEmpty()) {
            Q.append(" AND publisher LIKE ?");
            params.add("%" + publisher + "%");
            normalSearch = false;
        }
        if (isbn != null && !isbn.isEmpty()) {
            Q.append(" AND isbn = ?");
            params.add(isbn);
            normalSearch = false;
        }
        if (year != null && !year.isEmpty()) {
            Q.append(" AND YEAR(created_date) = ?");
            params.add(year);
            normalSearch = false;
        }
        if (language != null && !language.equals("Language")) {
            Q.append(" AND language LIKE ?");
            params.add("%" + language + "%");
            normalSearch = false;
        }
        if (sortBy != null && !sortBy.equals("Sort by")) {
            if(sortBy.equals("Newest first")) {
                Q.append(" ORDER BY created_date DESC");
                normalSearch = false;
            } else {
                Q.append(" ORDER BY created_date ASC");
                normalSearch = false;
            }
        }
        // if everything is null search the newest first
        if(normalSearch) {
            Q.append(" ORDER BY created_date DESC");
            return Q.toString();
        } else {
            return Q.toString();
        }
    }

    public static List<Books> getAllBooks(Connection connect) throws SQLException {
        PreparedStatement stm = connect.prepareStatement("SELECT * FROM books");
        ResultSet rs = stm.executeQuery();
        List<Books> result = new ArrayList<>();
        while (rs.next()) {
            String title = rs.getString("title");
            String author = rs.getString("author");
            String publisher = rs.getString("publisher");
            String isbn = rs.getString("ISBN");
            String genre = rs.getString("genre");
            String description = rs.getString("description");
            String id = rs.getString("idbooks");
            String language = rs.getString("language");
            String year = rs.getString("created_date");
            String image = rs.getString("image");
            String rating = rs.getString("rating");
            int views = rs.getInt("views");
            Books bok = new Books(id, title, description, author, genre, publisher, isbn, language, year, image, rating, false, views);
            result.add(bok);
        }
        return result;
    }
}

