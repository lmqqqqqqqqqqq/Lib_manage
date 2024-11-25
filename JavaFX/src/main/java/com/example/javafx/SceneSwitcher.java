package com.example.javafx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class SceneSwitcher {

    private static final Map<String, Parent> cachedScenes = new HashMap<>();
    private static String currentPageName;
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
            System.out.println(e.getMessage());
        }
    }

    public static void SwitchScene(Stage currentStage, String string, boolean darkMode) {
        try {
            FXMLLoader fxmlLoader;
            if (!darkMode) {
                fxmlLoader = new FXMLLoader(SceneSwitcher.class.getResource(string));
            }
            else {
                fxmlLoader = new FXMLLoader(SceneSwitcher.class.getResource("Dark" + string));
            }
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
            System.out.println(e.getMessage());
        }
    }

    public static void switchPage(AnchorPane pane, String string, AnchorPane manager, boolean darkMode) throws IOException {
        FXMLLoader loader;
        currentPageName = string;
        if (!darkMode) {
            loader = new FXMLLoader(SceneSwitcher.class.getResource(string));
        }
        else {
            loader = new FXMLLoader(SceneSwitcher.class.getResource("Dark" + string));
        }
        Parent page = loader.load();

        pane.getChildren().clear();
        pane.getChildren().add(page);
        pane.getChildren().add(manager);

        manager.toFront();
    }

    public static String getCurrentPageName() {
        return currentPageName;
    }

    public static void switchPage(AnchorPane pane, String string, AnchorPane manager) throws IOException {

        FXMLLoader loader = new FXMLLoader(SceneSwitcher.class.getResource(string));
        Parent page = loader.load();

        pane.getChildren().clear();
        pane.getChildren().add(page);
        pane.getChildren().add(manager);

        manager.toFront();
    }

    public static void switchPage(AnchorPane pane, String string) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneSwitcher.class.getResource(string));
        Parent page = loader.load();

        pane.getChildren().clear();
        pane.getChildren().add(page);
    }

}
