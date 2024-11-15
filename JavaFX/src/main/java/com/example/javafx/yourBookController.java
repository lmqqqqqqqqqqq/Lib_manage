package com.example.javafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class yourBookController {
    DatabaseConnect databaseConnect = new DatabaseConnect();

    @FXML
    private HBox borrowed;

    @FXML
    public void initialize() throws Exception {
        loadBorrowed();
    }

    @FXML
    public void loadBorrowed() throws Exception {
        borrowed.getChildren().clear();
        StringBuilder Q = new StringBuilder("SELECT * FROM books INNER JOIN user_books");
        Q.append(" ON user_books.idbooks = books.idbooks AND user_books.idusers = ? " +
                "AND user_books.borrow = 1 limit 7");
        PreparedStatement stm = databaseConnect.connect().prepareStatement(Q.toString());
        stm.setObject(1,LoginController.user.getId());
        stm.executeQuery();
        ResultSet rs = stm.getResultSet();
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
        if (result.isEmpty()) {
            System.out.println("No books found");
        } else {
            for (Books b : result) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javafx/resultBookShow.fxml"));
                AnchorPane bookPane = loader.load();
                resultBookShow controller = loader.getController();
                controller.setOutputData(getClass().getResource("/com/example/javafx/test.png").toExternalForm(), b.getTitle(), b.getAuthor());
                borrowed.getChildren().add(bookPane);
            }
        }

    }
}
