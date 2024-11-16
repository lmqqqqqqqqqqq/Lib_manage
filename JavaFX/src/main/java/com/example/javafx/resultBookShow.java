package com.example.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class resultBookShow {
    @FXML
    private ImageView image;
    @FXML
    private Label title;
    @FXML
    private Label author;

    public void setOutputData(String imagepath, String title, String author) {
        this.image.setImage(new Image(imagepath));
        this.title.setText(title);
        this.author.setText(author);
    }


}
