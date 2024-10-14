package com.example.javafx;

import javafx.fxml.FXML;

import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class MAIN_SCENE_control {


    @FXML
    private ImageView LOGO_gr;
    @FXML
    public void OnClick_LOGO() throws IOException {
        Stage currentStage = (Stage) LOGO_gr.getScene().getWindow();
        SceneSwitcher.SwitchScene(currentStage,"MAIN_SCENE.fxml");
    }

    @FXML
    private ImageView AVATAR_profile; // add them duong dan den profile (ve` sau)
    @FXML
    public void OnClick_AVATAR() throws IOException {
        Stage currentStage = (Stage) AVATAR_profile.getScene().getWindow();
        //SwitchScene.swScene(currentStage,"PROFILE.fxml");
    }
}
