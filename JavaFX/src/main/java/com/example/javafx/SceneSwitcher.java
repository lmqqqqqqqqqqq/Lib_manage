package com.example.javafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
}
