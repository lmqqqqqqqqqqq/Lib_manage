package com.example.javafx;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class mainController {


    @FXML
    private ImageView mainSceneAvatar;
    @FXML
    private AnchorPane manager;
    @FXML
    private AnchorPane managerBar;
    @FXML
    private StackPane ContentAnchorPane;
    @FXML
    private Circle circle;

    User user = LoginController.user;





    public void initialize() throws IOException {
        ProfileController.loadImage(mainSceneAvatar, user.getAvatarLink());
        SceneSwitcher.switchPage(ContentAnchorPane, "homeScene.fxml", manager);
    }

    public void homeOnClick() throws IOException {
        SceneSwitcher.switchPage(ContentAnchorPane, "homeScene.fxml", manager);
        initialize();
        manager.setDisable(true);
        manager.setVisible(false);
    }

    public void yourBookOnClick() throws IOException {
        SceneSwitcher.switchPage(ContentAnchorPane, "yourBookScene.fxml", manager);
        manager.setDisable(true);
        manager.setVisible(false);
    }

    public void advancedSearchOnClick() throws IOException {
        SceneSwitcher.switchPage(ContentAnchorPane, "advancedSearchScene.fxml", manager);
        manager.setDisable(true);
        manager.setVisible(false);
    }

    public void profileOnClick() throws IOException {
        SceneSwitcher.switchPage(ContentAnchorPane, "profileScene.fxml", manager);
        manager.setDisable(true);
        manager.setVisible(false);
    }
    public void managerOnAction() {
        manager.setVisible(true);
        manager.setDisable(false);
        Animation.translateAnimation(managerBar);
    }

    public void outSideManagerClick() {
        manager.setVisible(false);
        manager.setDisable(true);
    }


}





