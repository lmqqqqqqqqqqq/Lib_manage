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
    private Label allert;
    @FXML
    private AnchorPane infoPane;
    @FXML
    private ImageView favourite;
    private AnchorPane currentPane;

    private Books books;
    static User user = LoginController.user;

    DatabaseConnect connect = new DatabaseConnect();
    public void initialize(Books book, AnchorPane currentPane) {
        user = LoginController.user;
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

    public static void updateCoin(Connection conn, int amount) {
        user.setCoin(user.getCoin() + amount);
        try (PreparedStatement stm = conn.prepareStatement("update users set coin = ? where idusers = ?")) {
            stm.setInt(1, user.getCoin());
            stm.setInt(2, user.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void borrowOnAction() throws Exception {
        if(user.getCoin() < 100) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("System notifications");
            alert.setHeaderText(null);
            alert.setContentText("Your balance is insufficient to borrow this book\nYour Balance: " + user.getCoin() + "coins");
            alert.showAndWait();
        } else {
            LocalDate borrowDate = LocalDate.now();
            LocalDate returnDate = borrowDate.plusDays(10);
            updateCoin(connect.connect(), -100);
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
            alert.setContentText("Borrow book successfully!\nYour book will expire on " + returnDate + "\nYou have spent 100 coins        Your Balance: " + user.getCoin() + " coins");
            alert.showAndWait();

            borrowButton.setDisable(true);
            borrowButton.setVisible(false);
            returnButton.setDisable(false);
            returnButton.setVisible(true);
            getDay();
        }
    }
    int total;
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
                    allert.setText("Alert: The book has been expired for " + (int)ChronoUnit.DAYS.between(dueDate, date) + " days");
                    allert.setStyle("-fx-text-fill: red;");
                    returnButton.setDisable(false);
                    returnButton.setVisible(true);
                    GaussianBlur blur = new GaussianBlur(10);
                    infoPane.setEffect(blur);
                    infoPane.setMouseTransparent(true);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("System notifications");
                    alert.setHeaderText(null);
                    total = (int)ChronoUnit.DAYS.between(dueDate, date) * 100;
                    alert.setContentText("Expired book! please RETURN now!\nYou were fined for returning book late for "
                            + (int)ChronoUnit.DAYS.between(dueDate, date) + " days\nTotal fine amount: " + total + " coins");
                    alert.showAndWait();
                } else if (ChronoUnit.DAYS.between(dueDate, date) <= 5) {
                    allert.setText("Alert: " + ChronoUnit.DAYS.between(date, dueDate) + " days left before expiration");
                    allert.setStyle("-fx-text-fill: red;");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void returnOnAction() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("System notifications");
        alert.setHeaderText(null);
        if(LocalDate.parse(returndayLabel.getText()).isAfter(LocalDate.now())) {
            alert.setContentText("Return book successfully!");
            alert.showAndWait();
            String query = "update user_books set borrow = 0, borrow_date = null, due_date = null where idusers = ? and idbooks = ?";
            try (Connection connection = db.connect()) {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, user.getId());
                ps.setString(2, books.getId());
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
                if (total > user.getCoin()) {
                    alert.setContentText("Your balance is not enough to cover the penalty\nYor balance " + user.getCoin() + " coins          Your fine : " + total + " coins");
                    alert.showAndWait();
                } else {
                    user.setCoin(user.getCoin() - total);
                    alert.setContentText("You were fined for returning book late for " +
                            (int)ChronoUnit.DAYS.between(LocalDate.parse(returndayLabel.getText()), LocalDate.now()) + " days\nTotal fine amount: " + total + " coins\nYour Balance: " + user.getCoin() + " coins");
                    alert.showAndWait();
                    String query = "update user_books set borrow = 0, borrow_date = null, due_date = null where idusers = ? and idbooks = ?";
                    String query2 = "update users set coin = ? where idusers = ?";
                    try (Connection connection = db.connect()) {
                        PreparedStatement ps = connection.prepareStatement(query);
                        PreparedStatement ps2 = connection.prepareStatement(query2);
                        ps.setInt(1, user.getId());
                        ps.setString(2, books.getId());
                        ps.executeUpdate();
                        ps2.setInt(1, user.getCoin());
                        ps2.setInt(2, user.getId());
                        ps2.executeUpdate();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    returnButton.setDisable(true);
                    returnButton.setVisible(false);
                    borrowButton.setDisable(false);
                    borrowButton.setVisible(true);
                    borrowdayLabel.setVisible(false);
                    returndayLabel.setVisible(false);
                    borrowday.setVisible(false);
                    returnday.setVisible(false);
                    infoPane.setEffect(null);
                    infoPane.setMouseTransparent(false);
                    allert.setVisible(false);
                }
            }
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
            String query = "insert IGNORE into books (idbooks, title, author, created_date, image, description, genre, publisher, ISBN, language, rating) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
                System.out.println("Sucessssssssssssssssss");
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