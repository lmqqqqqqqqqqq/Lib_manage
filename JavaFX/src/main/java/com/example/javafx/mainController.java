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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


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
    @FXML
    private VBox boader;
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
            roleLabel.setStyle("-fx-text-fill: #009a8d;");
        } else {
            roleLabel.setText("( Admin )");
            roleLabel.setStyle("-fx-text-fill: #ff0059;");
        }
        waitingScene.setVisible(false);

        });
        pause.play();
    }

    public void homeOnClick() throws IOException {
        currentPage = "homeScene.fxml";
            SceneSwitcher.switchPage(ContentAnchorPane, currentPage, manager);
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
        String query = "select count(*) from user_books where idusers = ? and borrow = 1";
        String query1 = "select count(*) from user_books where idusers = ? and is_favourite = 1";
        try (Connection connection = DatabaseConnect.getconnect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
            preparedStatement.setInt(1, user.getId());
            preparedStatement1.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            if (resultSet.next()) {
                borrowAmount.setText(resultSet.getString(1));
            }
            if(resultSet1.next()) {
                favouriteAmount.setText(resultSet1.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        profileButton.setStyle(null);
        LoadImage.loadAvatarImage(mainSceneAvatar, user.getAvatarLink());
    }

    public void magOnAction() throws IOException {
        currentPage = "BorrowManagement.fxml";
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
        currentPage = "addBook.fxml";
        SceneSwitcher.switchPage(ContentAnchorPane, currentPage, manager, darkMode.get());
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
        currentPage = "userManage.fxml";
        SceneSwitcher.switchPage(ContentAnchorPane, currentPage, manager, darkMode.get());
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
            intro.setStyle("-fx-background-color: #2b2b2b;" +
                            " -fx-text-fill: white;");
            boader.setStyle("-fx-background-color: #403f3f;" +
                            "-fx-border-width: 3; ");
            darkModeBox.setStyle("-fx-text-fill: #dddddd");

        } else {
            managerBar.setStyle("-fx-background-color: #efc076;");
            intro.setStyle("-fx-background-color: #efc076;");
            boader.setStyle("-fx-background-color:  #ababab;" +
                    "-fx-border-color: #000000;" +
                    "-fx-border-width: 3; ");
            darkModeBox.setStyle("-fx-text-fill: #000000");
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
            return;
        }
        FXMLLoader loader;
        if (darkMode.get()) {
            loader = new FXMLLoader(getClass().getResource("Dark" + currentPage));
        } else {
            loader = new FXMLLoader(getClass().getResource(currentPage));
        }
        Parent page = loader.load();
        ContentAnchorPane.getChildren().clear();
        ContentAnchorPane.getChildren().add(page);
    }
}