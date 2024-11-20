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
    private HBox favorite;
    @FXML
    private AnchorPane yourBookPane;
    @FXML
    public void initialize() throws Exception {
        resultBookShow.setParentPane(yourBookPane);
        loadBorrowed();
        loadFavorite();
    }

    @FXML
    public void loadBorrowed() throws Exception {
        borrowed.getChildren().clear();
        StringBuilder Q = new StringBuilder("SELECT * FROM books INNER JOIN user_books");
        Q.append(" ON user_books.idbooks = books.idbooks AND user_books.idusers = ? " +
                "AND user_books.borrow = 1");

        List<Books> result = AdvancedSearch.search(Q.toString(), LoginController.user.getId(), databaseConnect.connect());

        if (result.isEmpty()) {
            System.out.println("No books found");
        } else {
            showLoad.intoBox(borrowed, result);
        }
    }

    @FXML
    public void loadFavorite() throws Exception {
        favorite.getChildren().clear();
        StringBuilder Q = new StringBuilder("SELECT * FROM books INNER JOIN user_books" +
                " ON user_books.idbooks = books.idbooks AND user_books.idusers = ? " +
                "AND user_books.is_favorite = 1");
        List<Books> result = AdvancedSearch.search(Q.toString(), LoginController.user.getId(), databaseConnect.connect());

        if (result.isEmpty()) {
            System.out.println("No books found");
        } else {
            showLoad.intoBox(favorite, result);
        }
    }

}
