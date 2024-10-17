package com.example.javafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcher {

    public static void SwitchScene(Stage currentStage, String string) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SceneSwitcher.class.getResource(string));
            Scene scene = new Scene(fxmlLoader.load());
            double currentHeight = currentStage.getHeight();
            double currentWidth = currentStage.getWidth();
            currentStage.setMinWidth(600);
            currentStage.setMinHeight(600);
            currentStage.setHeight(currentHeight);
            currentStage.setWidth(currentWidth);
            currentStage.setScene(scene);
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public  static void newScene(String string) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SceneSwitcher.class.getResource(string));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setMinWidth(400);
            stage.setMinHeight(400);
            stage.setHeight(400);
            stage.setWidth(500);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
