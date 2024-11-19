package com.example.javafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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

    public static void setParentPane(AnchorPane pane) {
        root = pane;
    }
    private Books book;
    public void setOutputData(String imagepath, String title, String author, String id, Books books) {
        try {
            this.image.setImage(new Image(imagepath));
        } catch (Exception e) {
            Image defaultImage = new Image(ProfileController.class.getResource("/com/example/javafx/book.png").toExternalForm());
            image.setImage(defaultImage);
        }
        this.title.setText(title);
        this.author.setText(author);
        this.id.setText(id);
        this.book = books;
    }

    public void resultBookClicked() throws IOException {
        AnchorPane previousContent = new AnchorPane();
        previousContent.getChildren().addAll(root.getChildren());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("bookDetails.fxml"));
        AnchorPane newContent = loader.load();
        BookDetailsController controller = loader.getController();
        controller.setBook(book);
        root.getChildren().clear();
        root.getChildren().add(newContent);
    }


}
