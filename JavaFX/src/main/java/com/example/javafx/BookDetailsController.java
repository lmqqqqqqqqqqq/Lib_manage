package com.example.javafx;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class BookDetailsController {

    @FXML
    private AnchorPane BookDetailsScene;

    public void backToSearchOnAction() throws IOException {
        SceneSwitcher.switchBetweenPage(BookDetailsScene, "homeScene.fxml");
    }
}