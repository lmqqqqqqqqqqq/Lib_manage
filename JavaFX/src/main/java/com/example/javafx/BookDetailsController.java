package com.example.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
    private Button returnButton;
    @FXML
    private Label borrowdayLabel;
    @FXML
    private Label returndayLabel;
    @FXML
    private Label borrowday;
    @FXML
    private Label returnday;
    @FXML
    private Label alert;
    @FXML
    private AnchorPane infoPane;
    @FXML
    private ImageView favourite;
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
        if(books.getYear() == null) {
            createdDateLabel.setText("No date available");
        } else {
            createdDateLabel.setText(books.getYear());
        }
        genreLabel.setText(books.getGenre());
        languageLabel.setText(books.getLanguage());
        ratingLabel.setText(books.getRating());
        String imageUrl = books.getImageLinks();
        LoadImage.loadBookImage(imageUrl, bookImage);
        borrowButton.setDisable(true);
        borrowButton.setVisible(false);
        returnButton.setDisable(true);
        returnButton.setVisible(false);
        borrowdayLabel.setVisible(false);
        returndayLabel.setVisible(false);
        borrowday.setVisible(false);
        returnday.setVisible(false);
        addBookFromAPIToDatabase();
        Check();
        if (isBorrowed()) {
            borrowButton.setDisable(true);
            borrowButton.setVisible(false);
            returnButton.setDisable(false);
            getDay();
        } else {
            borrowButton.setDisable(false);
            borrowButton.setVisible(true);
            returnButton.setDisable(true);
            returnButton.setVisible(false);
            borrowdayLabel.setVisible(false);
            returndayLabel.setVisible(false);
            borrowday.setVisible(false);
            returnday.setVisible(false);
        }
        if (isFavourite()) {
            LoadImage.loadBookImage("/com/example/image/favourite.png", favourite);
        } else {
            LoadImage.loadBookImage("/com/example/image/unfavoritee.png", favourite);
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
            ps.setString(4, books.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("System notifications");
        alert.setHeaderText(null);
        alert.setContentText("Borrow book successfully!\nYour book will expire on " + returnDate);
        alert.showAndWait();

        borrowButton.setDisable(true);
        borrowButton.setVisible(false);
        returnButton.setDisable(false);
        returnButton.setVisible(true);
        getDay();
    }

    public void getDay() {
        returnButton.setVisible(true);
        borrowdayLabel.setVisible(true);
        returndayLabel.setVisible(true);
        borrowday.setVisible(true);
        returnday.setVisible(true);
        String query = "select borrow_date, due_date from user_books where idusers = ? and idbooks = ?";
        try (Connection connection = db.connect()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, user.getId());
            ps.setString(2, books.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                borrowdayLabel.setText(rs.getString("borrow_date"));
                returndayLabel.setText(rs.getString("due_date"));
                LocalDate date = LocalDate.now();
                LocalDate dueDate = LocalDate.parse(rs.getString("due_date"));
                if (date.isEqual(dueDate) || date.isAfter(dueDate)) {
                    alert.setText("Alert: The book is expired");
                    alert.setStyle("-fx-text-fill: red;");
                    returnButton.setDisable(false);
                    returnButton.setVisible(true);
                    GaussianBlur blur = new GaussianBlur(10);
                    infoPane.setEffect(blur);
                    infoPane.setMouseTransparent(true);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("System notifications");
                    alert.setHeaderText(null);
                    alert.setContentText("Expired books! please RETURN now!");
                    alert.showAndWait();
                } else if (ChronoUnit.DAYS.between(date, dueDate) <= 5) {
                    alert.setText("Alert: " + ChronoUnit.DAYS.between(date, dueDate) + " days left before expiration");
                    alert.setStyle("-fx-text-fill: red;");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void returnOnAction() {
        String query = "update user_books set borrow = 0, borrow_date = null, due_date = null where idusers = ? and idbooks = ?";
        try (Connection connection = db.connect()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, user.getId());
            ps.setString(2, books.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("System notifications");
        alert.setHeaderText(null);
        alert.setContentText("Return book successfully!");
        alert.showAndWait();
        returnButton.setDisable(true);
        returnButton.setVisible(false);
        borrowButton.setDisable(false);
        borrowButton.setVisible(true);
        borrowdayLabel.setVisible(false);
        returndayLabel.setVisible(false);
        borrowday.setVisible(false);
        returnday.setVisible(false);
    }

    public void favouriteOnAction() {
        if(isFavourite()) {
            String query = "update user_books set is_favourite = 0 where idusers = ? and idbooks = ?";
            try (Connection connection = db.connect()) {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, user.getId());
                ps.setString(2, books.getId());
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
            LoadImage.loadBookImage("/com/example/image/unfavoritee.png", favourite);
        } else {
            String query = "update user_books set is_favourite = 1 where idusers = ? and idbooks = ?";
            try (Connection connection = db.connect()) {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, user.getId());
                ps.setString(2, books.getId());
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
            LoadImage.loadBookImage("/com/example/image/favourite.png", favourite);
        }
    }

    public void addBookFromAPIToDatabase() {
        if(books.isFromAPI()) {
            String query = "insert IGNORE into books (idbooks, title, author, created_date, image, description, genre, publisher, ISBN, language, rating) SELECT ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?";
            try(Connection connection = db.connect()) {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, books.getId());
                ps.setString(2, books.getTitle());
                ps.setString(3, books.getAuthor());
                try {
                    ps.setDate(4, Date.valueOf(books.getYear()));
                } catch (Exception e) {
                    ps.setDate(4, null);
                }
                ps.setString(5, books.getImageLinks());
                ps.setString(6, books.getDescription());
                ps.setString(7, books.getGenre());
                ps.setString(8, books.getPublisher());
                ps.setString(9, books.getIsbn());
                ps.setString(10, books.getLanguage());
                ps.setString(11, books.getRating());
                ps.executeUpdate();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isBorrowed() {
        String query = "select borrow from user_books where idusers = ? and idbooks = ?";
        try (Connection connection = db.connect()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, user.getId());
            ps.setString(2, books.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
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
            ps.setString(2, books.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("is_favourite");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public void Check() {
        String query = "INSERT IGNORE INTO user_books (idusers, idbooks, currentTime) VALUES (?, ?, ?)";
        try (Connection connection = db.connect()) {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, user.getId());
            ps.setString(2, books.getId());
            ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        books.setViews(books.getViews() + 1);
        String query2 = "update books set views = ? where idbooks = ?";
        try (Connection connection = db.connect()) {
            PreparedStatement ps = connection.prepareStatement(query2);
            ps.setInt(1, books.getViews());
            ps.setString(2, books.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void backToSearchOnAction() throws IOException {
        currentPane.setVisible(false);
    }
}