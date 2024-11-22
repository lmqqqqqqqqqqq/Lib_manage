package com.example.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class BookDetailsController {
    DatabaseConnect db = new DatabaseConnect();

    @FXML
    private Label bookNameLabel;
    @FXML
    private Label authorLabel;
    @FXML
    private Label publisherLabel;
    @FXML
    private Label descriptionText;
    @FXML
    private Label idLabel;
    @FXML
    private Label createdDateLabel;
    @FXML
    private Label ISBNLabel;
    @FXML
    private Label genreLabel;
    @FXML
    private ImageView bookImage;
    @FXML
    private Label ratingLabel;
    @FXML
    private Label languageLabel;
    @FXML
    private Button borrowButton;
    @FXML
    private Button favouriteButton;
    @FXML
    private Button returnButton;
    @FXML
    private Button unfavouriteButton;

    private AnchorPane currentPane;

    private Books books;
    User user = LoginController.user;
    public void initialize(Books book, AnchorPane currentPane) {
        this.currentPane = currentPane;
        books = book;
        bookNameLabel.setText(books.getTitle());
        authorLabel.setText(books.getAuthor());
        publisherLabel.setText(books.getPublisher());
        ISBNLabel.setText(books.getIsbn());
        idLabel.setText(books.getId());
        descriptionText.setText(books.getDescription());
        createdDateLabel.setText(books.getYear());
        genreLabel.setText(books.getGenre());
        languageLabel.setText(books.getLanguage());
        ratingLabel.setText(books.getRating());
        String imageUrl = books.getImageLinks();
        LoadBookImage.loadBookImage(imageUrl, bookImage);
        if(isFromApi()) {
            borrowButton.setDisable(true);
            borrowButton.setVisible(false);
        } else {
            Check();
            if(isBorrowed()) {
                borrowButton.setDisable(true);
                borrowButton.setVisible(false);
                returnButton.setDisable(false);
                returnButton.setVisible(true);
            } else {
                borrowButton.setDisable(false);
                borrowButton.setVisible(true);
                returnButton.setDisable(true);
                returnButton.setVisible(false);
            }
            if(isFavourite()) {
                favouriteButton.setDisable(true);
                favouriteButton.setVisible(false);
                unfavouriteButton.setDisable(false);
                unfavouriteButton.setVisible(true);
            } else {
                favouriteButton.setDisable(false);
                favouriteButton.setVisible(true);
                unfavouriteButton.setDisable(true);
                unfavouriteButton.setVisible(false);
            }
        }
    }

    public void borrowOnAction() {
        LocalDate borrowDate = LocalDate.now();
        LocalDate returnDate = borrowDate.plusDays(10);
        String query = "update user_books set borrow = 1, borrow_date = ?, due_date = ? where idusers = ? and idbooks = ?";
        try (Connection connection = db.connect()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setDate(1, Date.valueOf(borrowDate));
            ps.setDate(2, Date.valueOf(returnDate));
            ps.setInt(3, user.getId());
            ps.setInt(4, Integer.parseInt(books.getId()));
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void returnOnAction() {
        String query = "update user_books set borrow = 0, borrow_date = null, due_date = null where idusers = ? and idbooks = ?";
        try (Connection connection = db.connect()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, user.getId());
            ps.setInt(2, Integer.parseInt(books.getId()));
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void favouriteOnAction() {
        String query = "update user_books set is_favourite = 1 where idusers = ? and idbooks = ?";
        try (Connection connection = db.connect()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, user.getId());
            ps.setInt(2, Integer.parseInt(books.getId()));
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unfavouriteOnAction() {
        String query = "update user_books set is_favourite = 0 where idusers = ? and idbooks = ?";
        try (Connection connection = db.connect()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, user.getId());
            ps.setInt(2, Integer.parseInt(books.getId()));
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isFromApi() {
        return books.isFromAPI();
    }

    public boolean isBorrowed() {
        String query = "select borrow from user_books where idusers = ? and idbooks = ?";
        try (Connection connection = db.connect()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, user.getId());
            ps.setInt(2, Integer.parseInt(books.getId()));
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getBoolean("borrow");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean isFavourite() {
        String query = "select is_favourite from user_books where idusers = ? and idbooks = ?";
        try (Connection connection = db.connect()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, user.getId());
            ps.setInt(2, Integer.parseInt(books.getId()));
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return rs.getBoolean("is_favourite");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public void Check() {
        String query = "insert into user_books (idusers, idbooks, is_watched) SELECT ?, ?, ? WHERE NOT EXISTS (SELECT 1 FROM user_books WHERE idusers = ? AND idbooks = ?)";
        try (Connection connection = db.connect()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, user.getId());
            ps.setInt(2, Integer.parseInt(books.getId()));
            ps.setInt(3, 1);
            ps.setInt(4, user.getId());
            ps.setInt(5, Integer.parseInt(books.getId()));
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void backToSearchOnAction() throws IOException {
        currentPane.setVisible(false);
    }
}