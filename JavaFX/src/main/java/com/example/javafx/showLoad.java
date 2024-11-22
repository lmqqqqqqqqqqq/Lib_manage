package com.example.javafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;

import javax.swing.text.html.ListView;
import java.io.IOException;
import java.util.List;

public class showLoad {
    @FXML
    public static void intoBox(HBox target, List<Books> result) throws IOException {
        for (Books b : result) {
            FXMLLoader loader = new FXMLLoader(showLoad.class.getResource("/com/example/javafx/resultBookShow.fxml"));
            AnchorPane bookPane = loader.load();
            resultBookShow controller = loader.getController();
            controller.setOutputData(b.getImageLinks(), b.getTitle(), b.getAuthor(), b.getId(), b);
            target.getChildren().add(bookPane);

        }
    }

    public static void intoBox(HBox target, List<Books> result, int height) throws IOException {
        for (Books b : result) {
            FXMLLoader loader = new FXMLLoader(showLoad.class.getResource("/com/example/javafx/resultBookShow.fxml"));
            AnchorPane bookPane = loader.load();
            bookPane.setPrefHeight(height);
            resultBookShow controller = loader.getController();
            controller.setOutputData(b.getImageLinks(), b.getTitle(), b.getAuthor(), b.getId(), b);
            target.getChildren().add(bookPane);

        }
    }


    @FXML
    public static void intoBox(TilePane target, List<Books> result) throws IOException {
        for (Books b : result) {
            FXMLLoader loader = new FXMLLoader(showLoad.class.getResource("/com/example/javafx/resultBookShow.fxml"));
            AnchorPane bookPane = loader.load();
            resultBookShow controller = loader.getController();
            controller.setOutputData(b.getImageLinks(), b.getTitle(), b.getAuthor(), b.getId(), b);
            target.getChildren().add(bookPane);
        }
    }

}
