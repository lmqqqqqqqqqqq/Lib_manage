package com.example.javafx;

import javafx.fxml.FXML;
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

    public static String id_book;
    public void setOutputData(String imagepath, String title, String author, String id) {
        this.image.setImage(new Image(imagepath));
        this.title.setText(title);
        this.author.setText(author);
        this.id.setText(id);
        id_book = id;
    }

    public void resultBookClicked() throws IOException {
        SceneSwitcher.switchBetweenPage(root, "bookDetails.fxml");
    }


}
