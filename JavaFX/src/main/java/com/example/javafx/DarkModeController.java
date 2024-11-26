package com.example.javafx;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class DarkModeController {

    public static final BooleanProperty darkMode = new SimpleBooleanProperty(false);

    public static void setDarkMode(Scene scene) {
        if (scene == null) return;

        darkMode.set(!darkMode.get());
        applyDarkMode(scene);
    }

    public static void applyDarkMode(Scene scene) {
        if (scene == null) return;

        String darkModeStyle = DarkModeController.class.getResource("darkMainScene.css").toExternalForm();
        if (darkMode.get()) {
            if (!scene.getStylesheets().contains(darkModeStyle)) {
                scene.getStylesheets().add(darkModeStyle);
            }
        } else {
            scene.getStylesheets().remove(darkModeStyle);
        }
    }

    public static void setDarkModeStyle(AnchorPane managerBar, AnchorPane intro, VBox boader, boolean isDarkMode) {
        if (isDarkMode) {
            managerBar.setStyle("-fx-background-color: #2b2b2b;");
            intro.setStyle("-fx-background-color: #2b2b2b; -fx-text-fill: white;");
            boader.setStyle("-fx-background-color: #403f3f; -fx-border-width: 3;");
        } else {
            managerBar.setStyle("-fx-background-color: #efc076;");
            intro.setStyle("-fx-background-color: #efc076;");
            boader.setStyle("-fx-background-color: #ababab; -fx-border-color: #000000; -fx-border-width: 3;");
        }
    }

    public static void reloadCurrentPage(AnchorPane contentAnchorPane, String currentPage) throws IOException {
        if (currentPage == null || currentPage.isEmpty()) {
            return;
        }

        FXMLLoader loader;
        if (darkMode.get()) {
            loader = new FXMLLoader(DarkModeController.class.getResource("Dark" + currentPage));
        } else {
            loader = new FXMLLoader(DarkModeController.class.getResource(currentPage));
        }
        Parent page = loader.load();
        contentAnchorPane.getChildren().clear();
        contentAnchorPane.getChildren().add(page);
    }
}