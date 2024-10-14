package com.example.javafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneSwitcher {

    public static void swingScene(Stage currentStage, String string) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(SceneSwitcher.class.getResource(string));
            Scene scene = new Scene(fxmlLoader.load());
            currentStage.setMinWidth(600);
            currentStage.setMinHeight(600);
            currentStage.setScene(scene);
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
