package com.example.javafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class resultBookShow {
    @FXML
    private ImageView image;
    @FXML
    private Label title;
    @FXML
    private Label author;
    @FXML
    private Label id;
    @FXML
    private static AnchorPane root;
    @FXML
    private Label rating;

    public static void setParentPane(AnchorPane pane) {
        root = pane;
    }
    private Books book;
    public void setOutputData(String imagepath, String title, String author, String id, String rating, Books books) {
        LoadImage.loadBookImage(imagepath, image);
        this.title.setText(title);
        this.author.setText(author);
        this.id.setText(id);
        this.book = books;
        this.rating.setText(rating);
    }

    public void resultBookClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("bookDetails.fxml"));
        AnchorPane newContent = loader.load();
        BookDetailsController controller = loader.getController();
        controller.initialize(book, newContent);
        root.getChildren().add(newContent);
    }


}
