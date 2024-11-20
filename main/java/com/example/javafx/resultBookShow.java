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

    public String id_book;
    public void setOutputData(String imagepath, String title, String author, String id) {
        this.image.setImage(new Image(imagepath));
        this.title.setText(title);
        this.author.setText(author);
        id_book = id;
    }

    public void resultBookClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("bookDetails.fxml"));
        AnchorPane newContent = loader.load();
        BookDetailsController controller = loader.getController();
        controller.setId(Integer.parseInt(id_book));
        root.getChildren().clear();
        root.getChildren().add(newContent);
    }


}
