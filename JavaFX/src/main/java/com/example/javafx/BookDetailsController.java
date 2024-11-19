package com.example.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BookDetailsController {
    DatabaseConnect db = new DatabaseConnect();

    @FXML
    private AnchorPane BookDetailsScene;
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
    public void backToSearchOnAction() throws IOException {
        SceneSwitcher.switchBetweenPage(BookDetailsScene, "AdvancedSearchScene.fxml");
    }
    public void setBook(Books books) {
        bookNameLabel.setText(books.getTitle());
        authorLabel.setText(books.getAuthor());
        publisherLabel.setText(books.getPublisher());
        ISBNLabel.setText(books.getIsbn());
        idLabel.setText(books.getId());
        descriptionText.setText(books.getDescription());
        createdDateLabel.setText(books.getYear());
        genreLabel.setText(books.getGenre());
        String imageUrl = books.getImageLinks();
        Image image = new Image(imageUrl);
        bookImage.setImage(image);
    }
}