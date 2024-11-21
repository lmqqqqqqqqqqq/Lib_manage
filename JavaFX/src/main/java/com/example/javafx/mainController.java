package com.example.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.javafx.ProfileController.loadImage;

public class mainController {


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
    @FXML
    private Button homeButton;
    @FXML
    private Button yourBookButton;
    @FXML
    private Button searchButton;
    @FXML
    private Button managerButton;
    User user = LoginController.user;


    public void initialize() throws IOException {
        loadImage(mainSceneAvatar, user.getAvatarLink());
        SceneSwitcher.switchPage(ContentAnchorPane, "homeScene.fxml", manager);
        loadImage(introAvatar, user.getAvatarLink());
        name.setText(user.getUsername());
        outSideManagerClick();
        homeButton.setStyle("-fx-font-size: 25px;\n" +
                "    -fx-border-color: #0022ff;\n" +
                "    -fx-background-color: rgba(117, 186, 228, 0.78);\n" +
                "    -fx-border-width: 0px 0px 7px 0px; ");
        if (user instanceof Members) {
            managerButton.setVisible(false);
            managerButton.setDisable(true);
        }
    }

    public void homeOnClick() throws IOException {
        SceneSwitcher.switchPage(ContentAnchorPane, "homeScene.fxml", manager);
        initialize();
        outSideManagerClick();
        homeButton.setStyle("-fx-font-size: 25px;\n" +
                "    -fx-border-color: #0022ff;\n" +
                "    -fx-background-color: rgba(117, 186, 228, 0.78);\n" +
                "    -fx-border-width: 0px 0px 7px 0px; ");
        yourBookButton.setStyle(null);
        searchButton.setStyle(null);
        managerButton.setStyle(null);
    }

    public void yourBookOnClick() throws IOException {
        SceneSwitcher.switchPage(ContentAnchorPane, "yourBookScene.fxml", manager);
        outSideManagerClick();
        yourBookButton.setStyle("-fx-font-size: 25px;\n" +
                "    -fx-border-color: #0022ff;\n" +
                "    -fx-background-color: rgba(117, 186, 228, 0.78);\n" +
                "    -fx-border-width: 0px 0px 7px 0px; ");
        homeButton.setStyle(null);
        searchButton.setStyle(null);
        managerButton.setStyle(null);
    }

    public void advancedSearchOnClick() throws IOException {
        SceneSwitcher.switchPage(ContentAnchorPane, "advancedSearchScene.fxml", manager);
        outSideManagerClick();
        searchButton.setStyle("-fx-font-size: 25px;\n" +
                "    -fx-border-color: #0022ff;\n" +
                "    -fx-background-color: rgba(117, 186, 228, 0.78);\n" +
                "    -fx-border-width: 0px 0px 7px 0px; ");
        yourBookButton.setStyle(null);
        homeButton.setStyle(null);
        managerButton.setStyle(null);
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
        managerButton.setStyle("-fx-font-size: 25px;\n" +
                "    -fx-border-color: #0022ff;\n" +
                "    -fx-background-color: rgba(117, 186, 228, 0.78);\n" +
                "    -fx-border-width: 0px 0px 7px 0px; ");
    }

    public void outSideManagerClick() {
        try {
            manager.setVisible(false);
            manager.setDisable(true);
            managerButton.setStyle(null);
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
        SceneSwitcher.switchPage(ContentAnchorPane, "addBook.fxml", manager);
        outSideManagerClick();
    }
}





