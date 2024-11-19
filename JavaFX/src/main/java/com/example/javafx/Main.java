package com.example.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Login.fxml"));
        stage.setMinWidth(600);
        stage.setMinHeight(600);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
//        try {
//            // Khởi tạo controller và gọi phương thức từ controller
//            ConnectAPI controller = new ConnectAPI();
//            controller.saveBookToDatabase();
//
//            System.out.println("Process completed.");
//        } catch (Exception e) {
//            System.out.println("Error occurred: " + e.getMessage());
//            e.printStackTrace();
//        }
    }


    public static void main() {
        launch();
    }
}