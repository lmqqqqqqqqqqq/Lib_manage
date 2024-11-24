package com.example.javafx;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
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

    @FXML private CheckBox darkModeBox;
    public static BooleanProperty darkMode = new SimpleBooleanProperty(false);
    private static mainController instance;
    private String currentPage;

    public mainController() {
        instance = this;
    }

    public static mainController getInstance() {
        return instance;
    }

    public AnchorPane getContentAnchorPane() {
        return ContentAnchorPane;
    }

    public boolean isDarkMode() {
        return darkMode.get();
    }

    public void setDarkMode(boolean value) {
        darkMode.set(value);
    }

    public void initialize() throws IOException {
        waitingScene.setVisible(true);
        PauseTransition pause = new PauseTransition(Duration.seconds(0.1));
        pause.setOnFinished(event -> {
        darkModeBox.setSelected(darkMode.get());
        LoadImage.loadAvatarImage(mainSceneAvatar, user.getAvatarLink());
            try {
                SceneSwitcher.switchPage(ContentAnchorPane, "homeScene.fxml", manager);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            name.setText(user.getUsername());
            try {
                SceneSwitcher.switchPage(ContentAnchorPane, "homeScene.fxml", manager, darkMode.get());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            name.setText(user.getUsername());
        outSideManagerClick();
        darkMode.addListener((obs, oldMode, newMode) -> {
            try {
                // Reload the current page when darkMode changes
                String current = SceneSwitcher.getCurrentPageName();
                reloadCurrentPage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        setDarkMode();
        applyDarkMode(darkMode.get());
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
        currentPage = "yourBookScene.fxml";
        SceneSwitcher.switchPage(ContentAnchorPane, currentPage, manager, darkMode.get());
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
        currentPage = "advancedSearchScene.fxml";
        SceneSwitcher.switchPage(ContentAnchorPane, currentPage, manager, darkMode.get());
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
        SceneSwitcher.switchPage(ContentAnchorPane, "BorrowManagement.fxml", manager, darkMode.get());
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
        SceneSwitcher.switchPage(ContentAnchorPane, "addBook.fxml", manager, darkMode.get());
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
        SceneSwitcher.switchPage(ContentAnchorPane, "userManage.fxml", manager, darkMode.get());
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
        darkMode.set(darkModeBox.isSelected());
        applyDarkMode(darkMode.get());
        Scene currentScene = ContentAnchorPane.getScene();
    }
    private void applyDarkMode(boolean darkMode) {
        Scene scene = darkModeBox.getScene();
        if (darkModeBox.isSelected()) {
            managerBar.setStyle("-fx-background-color: #2b2b2b;");
        } else {
            managerBar.setStyle("-fx-background-color: #FFFFFF;");
        }
        if (scene != null) {
            String darkModeStyle = getClass().getResource("darkMainScene.css").toExternalForm();
            if (darkMode) {
                if (!scene.getStylesheets().contains(darkModeStyle)) {
                    scene.getStylesheets().add(darkModeStyle);
                }
            } else {
                scene.getStylesheets().remove(darkModeStyle);
            }
        }
    }

    public void reloadCurrentPage() throws IOException {
        if (currentPage == null || currentPage.isEmpty()) {
            return; // No page loaded yet
        }

        FXMLLoader loader;

        if (darkMode.get()) {
            loader = new FXMLLoader(getClass().getResource("Dark" + currentPage));
        } else {
            loader = new FXMLLoader(getClass().getResource(currentPage));
        }

        Parent page = loader.load();

        // Clear and set the new page
        ContentAnchorPane.getChildren().clear();
        ContentAnchorPane.getChildren().add(page);
    }

}