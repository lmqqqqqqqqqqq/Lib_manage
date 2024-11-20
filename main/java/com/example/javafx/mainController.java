package com.example.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.javafx.ProfileController.loadImage;

public class mainController {


    public Button wordleButton;
    @FXML
    private ImageView mainSceneAvatar;
    @FXML
    private AnchorPane manager;
    @FXML
    private AnchorPane managerBar;
    @FXML
    private AnchorPane intro;
    @FXML
    private AnchorPane ContentAnchorPane;
    @FXML
    private Label name;
    @FXML
    private ImageView introAvatar;
    User user = LoginController.user;


    public void initialize() throws IOException {
        loadImage(mainSceneAvatar, user.getAvatarLink());
        SceneSwitcher.switchPage(ContentAnchorPane, "homeScene.fxml", manager);
        loadImage(introAvatar, user.getAvatarLink());
        name.setText(user.getUsername());
        manager.setVisible(false);
        managerBar.setVisible(false);
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

    public void profileOnClick() {
        manager.setVisible(true);
        manager.setDisable(false);
        managerBar.setVisible(false);
        managerBar.setDisable(true);
        intro.setVisible(true);
        intro.setDisable(false);
        Animation.translateAnimation(intro);

    }
    public void managerOnAction() {
        manager.setVisible(true);
        manager.setDisable(false);
        managerBar.setVisible(true);
        managerBar.setDisable(false);
        intro.setVisible(false);
        intro.setDisable(true);
        Animation.translateAnimation(managerBar);
    }

    public void outSideManagerClick() {
        try {
            manager.setVisible(false);
            manager.setDisable(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setting() throws IOException {
        SceneSwitcher.switchBetweenPage(ContentAnchorPane, "profileScene.fxml");
    }

    public void logout() {
        Stage stage = (Stage) name.getScene().getWindow();
        SceneSwitcher.SwitchScene(stage, "Login.fxml");
    }

    public void addBookOnAction() throws IOException {
        SceneSwitcher.switchBetweenPage(ContentAnchorPane, "addBook.fxml");
    }

    @FXML
    private void loadWordleGame() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("wordle.fxml"));
            StackPane wordlePane = loader.load(); // Updated to StackPane

            ContentAnchorPane.getChildren().clear();
            ContentAnchorPane.getChildren().add(wordlePane);

            AnchorPane.setTopAnchor(wordlePane, 0.0);
            AnchorPane.setBottomAnchor(wordlePane, 0.0);
            AnchorPane.setLeftAnchor(wordlePane, 0.0);
            AnchorPane.setRightAnchor(wordlePane, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}





