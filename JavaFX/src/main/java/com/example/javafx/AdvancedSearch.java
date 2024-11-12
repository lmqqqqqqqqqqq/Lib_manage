package com.example.javafx;

import java.awt.print.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdvancedSearch extends SearchController {

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

    @Override
    public void resetSearch() {

    }
}
