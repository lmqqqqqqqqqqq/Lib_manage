package com.example.javafx;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;


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
    @FXML
    private Label borrowAmount;
    @FXML
    private Label favouriteAmount;
    @FXML
    private AnchorPane waitingScene;
    User user = LoginController.user;

    @FXML private CheckBox darkMode;


    public void initialize() throws IOException {
        waitingScene.setVisible(true);
        PauseTransition pause = new PauseTransition(Duration.seconds(0.1));
        pause.setOnFinished(event -> {
        LoadImage.loadAvatarImage(mainSceneAvatar, user.getAvatarLink());
            try {
                SceneSwitcher.switchPage(ContentAnchorPane, "homeScene.fxml", manager);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            name.setText(user.getUsername());
        outSideManagerClick();
        setDarkMode();
        homeButton.setStyle("-fx-background-radius: 30;\n" +
                "    -fx-border-radius: 30;\n" +
                "    -fx-background-color: #FFB433;;\n" +
                "    -fx-border-color: #000000;" +
                "    -fx-border-width: 3;");
        if (user instanceof Members) {
            managerButton.setVisible(false);
            managerButton.setDisable(true);
            roleLabel.setText("( Member )");
        } else {
            roleLabel.setText("( Admin )");
        }
        if (darkMode == null) {
            System.out.println("darkMode CheckBox is null. Check FXML bindings.");
        } else {
            System.out.println("darkMode CheckBox is initialized.");
        }
            waitingScene.setVisible(false);

        });
        pause.play();
    }

    public void homeOnClick() throws IOException {
            SceneSwitcher.switchPage(ContentAnchorPane, "homeScene.fxml", manager);
            initialize();
            outSideManagerClick();
            homeButton.setStyle("-fx-background-radius: 30;\n" +
                    "    -fx-border-radius: 30;\n" +
                    "    -fx-background-color: #FFB433;;\n" +
                    "    -fx-border-color: #000000;" +
                    "    -fx-border-width: 3; ");
            yourBookButton.setStyle(null);
            searchButton.setStyle(null);
            managerButton.setStyle(null);
            profileButton.setStyle(null);
//            LoadImage.loadAvatarImage(mainSceneAvatar, user.getAvatarLink());
//            nameLabel.setText(user.getFirstname() + " " + user.getLastname());

    }
    public void yourBookOnClick() throws IOException {
        SceneSwitcher.switchPage(ContentAnchorPane, "yourBookScene.fxml", manager);
        outSideManagerClick();
        yourBookButton.setStyle("-fx-background-radius: 30;\n" +
                "    -fx-border-radius: 30;\n" +
                "    -fx-background-color: #FFB433;;\n" +
                "    -fx-border-color: #000000;" +
                "    -fx-border-width: 3; ");
        homeButton.setStyle(null);
        searchButton.setStyle(null);
        managerButton.setStyle(null);
        profileButton.setStyle(null);
        LoadImage.loadAvatarImage(mainSceneAvatar, user.getAvatarLink());
    }

    public void advancedSearchOnClick() throws IOException {
        SceneSwitcher.switchPage(ContentAnchorPane, "advancedSearchScene.fxml", manager);
        outSideManagerClick();
        searchButton.setStyle("-fx-background-radius: 30;\n" +
                "    -fx-border-radius: 30;\n" +
                "    -fx-background-color: #FFB433;;\n" +
                "    -fx-border-color: #000000;" +
                "    -fx-border-width: 3; ");
        yourBookButton.setStyle(null);
        homeButton.setStyle(null);
        managerButton.setStyle(null);
        profileButton.setStyle(null);
        LoadImage.loadAvatarImage(mainSceneAvatar, user.getAvatarLink());
    }

    public void profileOnClick() {
        manager.setVisible(true);
        manager.setDisable(false);
        managerBar.setVisible(false);
        managerBar.setDisable(true);
        intro.setVisible(true);
        intro.setDisable(false);
        Animation.translateAnimation(intro);
        LoadImage.loadAvatarImage(introAvatar, user.getAvatarLink());
        profileButton.setStyle("-fx-background-radius: 30;\n" +
                "    -fx-border-radius: 30;\n" +
                "    -fx-background-color: #FFB433;;\n" +
                "    -fx-border-color: #000000;" +
                "    -fx-border-width: 3; ");
        yourBookButton.setStyle(null);
        homeButton.setStyle(null);
        managerButton.setStyle(null);
        searchButton.setStyle(null);
        if(yourBookController.borrowedBookAmount == 0) {
            borrowAmount.setText("No books");
        }else {
            borrowAmount.setText(String.valueOf(yourBookController.borrowedBookAmount));
        }
        if(yourBookController.favoriteBookAmount == 0) {
            favouriteAmount.setText("No books");
        } else {
            favouriteAmount.setText(String.valueOf(yourBookController.favoriteBookAmount));
        }
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
                "    -fx-background-color: #FFB433;;\n" +
                "    -fx-border-color: #000000;" +
                "    -fx-border-width: 3; ");
        LoadImage.loadAvatarImage(mainSceneAvatar, user.getAvatarLink());
    }

    public void magOnAction() throws IOException {
        SceneSwitcher.switchPage(ContentAnchorPane, "BorrowManagement.fxml", manager);
        outSideManagerClick();
        homeButton.setStyle(null);
        searchButton.setStyle(null);
        yourBookButton.setStyle(null);
        managerButton.setStyle("-fx-background-radius: 30;\n" +
                "    -fx-border-radius: 30;\n" +
                "    -fx-background-color: #FFB433;;\n" +
                "    -fx-border-color: #000000;" +
                "    -fx-border-width: 3; ");
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
                "    -fx-background-color: #FFB433;;\n" +
                "    -fx-border-color: #000000;" +
                "    -fx-border-width: 3; ");
    }

    public void userManageOnAction() throws IOException {
        SceneSwitcher.switchPage(ContentAnchorPane, "userManage.fxml", manager);
        outSideManagerClick();
        homeButton.setStyle(null);
        searchButton.setStyle(null);
        yourBookButton.setStyle(null);
        managerButton.setStyle("-fx-background-radius: 30;\n" +
                "    -fx-border-radius: 30;\n" +
                "    -fx-background-color: #FFB433;;\n" +
                "    -fx-border-color: #000000;" +
                "    -fx-border-width: 3; ");
    }

    public void setDarkMode() {
        if (ContentAnchorPane.getScene() == null) {
            ContentAnchorPane.sceneProperty().addListener((observable, oldScene, newScene) -> {
                if (newScene != null) {
                    applyStylesToAllAnchorPanes();
                }
            });
        } else {
            applyStylesToAllAnchorPanes();
        }
    }

    private void applyStylesToAllAnchorPanes() {
        Scene currentScene = ContentAnchorPane.getScene();

        if (darkMode.isSelected()) {
            currentScene.getStylesheets().clear();
            currentScene.getStylesheets().add(getClass().getResource("dark-theme.css").toExternalForm());
        } else {
            currentScene.getStylesheets().clear();
            currentScene.getStylesheets().add(getClass().getResource("light-theme.css").toExternalForm());
        }

        for (Node node : currentScene.getRoot().lookupAll(".anchor-pane")) {
            if (node instanceof AnchorPane) {
                if (darkMode.isSelected()) {
                    node.setStyle("-fx-background-color: #2c2f33;");
                } else {
                    node.setStyle("-fx-background-color: #ffffff;");
                }
            }
        }
    }
}





