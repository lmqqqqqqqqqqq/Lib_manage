package com.example.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    @FXML
    private Label roleLabel;
    @FXML
    private Button profileButton;
    User user = LoginController.user;


    public void initialize() throws IOException {
        loadImage(mainSceneAvatar, user.getAvatarLink());
        SceneSwitcher.switchPage(ContentAnchorPane, "homeScene.fxml", manager);
        name.setText(user.getUsername());
        outSideManagerClick();
        homeButton.setStyle("-fx-background-radius: 30;\n" +
                "    -fx-border-radius: 30;\n" +
                "    -fx-background-color: rgb(94, 154, 94);\n" +
                "    -fx-border-color: #ffff10;\n" +
                "    -fx-border-width: 1px 1px 1px 1px; ");
        if (user instanceof Members) {
            managerButton.setVisible(false);
            managerButton.setDisable(true);
            roleLabel.setText("( Member )");
        } else {
            roleLabel.setText("( Admin )");
        }
    }

    public void homeOnClick() throws IOException {
        SceneSwitcher.switchPage(ContentAnchorPane, "homeScene.fxml", manager);
        initialize();
        outSideManagerClick();
        homeButton.setStyle("-fx-background-radius: 30;\n" +
                "    -fx-border-radius: 30;\n" +
                "    -fx-background-color: rgb(94, 154, 94);\n" +
                "    -fx-border-color: #ffff10;\n" +
                "    -fx-border-width: 1px 1px 1px 1px; ");
        yourBookButton.setStyle(null);
        searchButton.setStyle(null);
        managerButton.setStyle(null);
        profileButton.setStyle(null);
        loadImage(mainSceneAvatar, user.getAvatarLink());
    }

    public void yourBookOnClick() throws IOException {
        SceneSwitcher.switchPage(ContentAnchorPane, "yourBookScene.fxml", manager);
        outSideManagerClick();
        yourBookButton.setStyle("-fx-background-radius: 30;\n" +
                "    -fx-border-radius: 30;\n" +
                "    -fx-background-color: rgb(94, 154, 94);\n" +
                "    -fx-border-color: #ffff10;\n" +
                "    -fx-border-width: 1px 1px 1px 1px; ");
        homeButton.setStyle(null);
        searchButton.setStyle(null);
        managerButton.setStyle(null);
        profileButton.setStyle(null);
        loadImage(mainSceneAvatar, user.getAvatarLink());
    }

    public void advancedSearchOnClick() throws IOException {
        SceneSwitcher.switchPage(ContentAnchorPane, "advancedSearchScene.fxml", manager);
        outSideManagerClick();
        searchButton.setStyle("-fx-background-radius: 30;\n" +
                "    -fx-border-radius: 30;\n" +
                "    -fx-background-color: rgb(94, 154, 94);\n" +
                "    -fx-border-color: #ffff10;\n" +
                "    -fx-border-width: 1px 1px 1px 1px; ");
        yourBookButton.setStyle(null);
        homeButton.setStyle(null);
        managerButton.setStyle(null);
        profileButton.setStyle(null);
        loadImage(mainSceneAvatar, user.getAvatarLink());
    }

    public void profileOnClick() {
        manager.setVisible(true);
        manager.setDisable(false);
        managerBar.setVisible(false);
        managerBar.setDisable(true);
        intro.setVisible(true);
        intro.setDisable(false);
        Animation.translateAnimation(intro);
        loadImage(introAvatar, user.getAvatarLink());
        profileButton.setStyle("-fx-background-radius: 30;\n" +
                "    -fx-border-radius: 30;\n" +
                "    -fx-background-color: rgb(94, 154, 94);\n" +
                "    -fx-border-color: #ffff10;\n" +
                "    -fx-border-width: 1px 1px 1px 1px; ");
        yourBookButton.setStyle(null);
        homeButton.setStyle(null);
        managerButton.setStyle(null);
        searchButton.setStyle(null);
    }
    public void managerOnAction() {
        manager.setVisible(true);
        manager.setDisable(false);
        managerBar.setVisible(true);
        managerBar.setDisable(false);
        intro.setVisible(false);
        intro.setDisable(true);
        Animation.translateAnimation(managerBar);
        managerButton.setStyle("-fx-background-radius: 30;\n" +
                "    -fx-border-radius: 30;\n" +
                "    -fx-background-color: rgb(94, 154, 94);\n" +
                "    -fx-border-color: #ffff10;\n" +
                "    -fx-border-width: 1px 1px 1px 1px; ");
        loadImage(mainSceneAvatar, user.getAvatarLink());
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
        SceneSwitcher.switchPage(ContentAnchorPane, "profileScene.fxml");
    }

    public void logout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("System notifications");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");
        ButtonType yes = new ButtonType("Yes");
        ButtonType cancel = new ButtonType("Cancel");
        alert.getButtonTypes().setAll(yes, cancel);
        alert.showAndWait().ifPresent(response -> {
            if (response.equals(yes)) {
                Stage stage = (Stage) name.getScene().getWindow();
                SceneSwitcher.SwitchScene(stage, "Login.fxml");
            }
        });
    }

    public void addBookOnAction() throws IOException {
        SceneSwitcher.switchPage(ContentAnchorPane, "addBook.fxml", manager);
        outSideManagerClick();
        homeButton.setStyle(null);
        searchButton.setStyle(null);
        yourBookButton.setStyle(null);
        managerButton.setStyle("-fx-background-radius: 30;\n" +
                "    -fx-border-radius: 30;\n" +
                "    -fx-background-color: rgb(94, 154, 94);\n" +
                "    -fx-border-color: #ffff10;\n" +
                "    -fx-border-width: 1px 1px 1px 1px; ");
    }

    public void userManageOnAction() throws IOException {
        SceneSwitcher.switchPage(ContentAnchorPane, "userManage.fxml", manager);
        outSideManagerClick();
        homeButton.setStyle(null);
        searchButton.setStyle(null);
        yourBookButton.setStyle(null);
        managerButton.setStyle("-fx-background-radius: 30;\n" +
                "    -fx-border-radius: 30;\n" +
                "    -fx-background-color: rgb(94, 154, 94);\n" +
                "    -fx-border-color: #ffff10;\n" +
                "    -fx-border-width: 1px 1px 1px 1px; ");
    }
}





