package com.example.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        try {
            Image icon = new Image(getClass().getResourceAsStream("/com/example/image/library.png"));
            stage.getIcons().add(icon);
        } catch (Exception e) {
            System.err.println("Không tìm thấy file icon: " + e.getMessage());
        }
        stage.setTitle("Our Library");
        FXMLLoader fxmlLoader =  new FXMLLoader(Main.class.getResource("Login.fxml"));
        stage.setMinWidth(600);
        stage.setMinHeight(600);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();

    }
    public static void main() {
        launch();
    }
}