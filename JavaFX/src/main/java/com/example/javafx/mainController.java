package com.example.javafx;

import javafx.beans.property.BooleanProperty;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;


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
    public AnchorPane ContentAnchorPane;
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
    @FXML
    private Label over;
    User user = LoginController.user;

    BooleanProperty darkMode = DarkModeController.darkMode;
    private static mainController instance;
    public static String currentPage;

    public mainController() {
        instance = this;
    }

    public static mainController getInstance() {
        return instance;
    }

    public AnchorPane getManagerBar() {
        return managerBar;
    }

    public AnchorPane getIntro() {
        return intro;
    }

    public VBox getBoader() {
        return boader;
    }

    public AnchorPane getContentAnchorPane() {
        return ContentAnchorPane;
    }

    public void initialize() throws IOException {
        manager.setVisible(false);
        waitingScene.setVisible(true);
        PauseTransition pause = new PauseTransition(Duration.seconds(0.1));
        pause.setOnFinished(event -> {
            LoadImage.loadAvatarImage(mainSceneAvatar, user.getAvatarLink());
            try {
                SceneSwitcher.switchPage(ContentAnchorPane, "homeScene.fxml", manager, darkMode.get());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            name.setText(user.getUsername());
            outSideManagerClick();
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

    public void profileOnClick()  {
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
        String query2 = "select count(*) from user_books where idusers = ? and due_date <= ?";
        try (Connection connection = DatabaseConnect.getconnect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            PreparedStatement preparedStatement1 = connection.prepareStatement(query1);
            PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
            preparedStatement.setInt(1, user.getId());
            preparedStatement1.setInt(1, user.getId());
            preparedStatement2.setInt(1, user.getId());
            preparedStatement2.setDate(2, Date.valueOf(LocalDate.now()));
            ResultSet resultSet = preparedStatement.executeQuery();
            ResultSet resultSet1 = preparedStatement1.executeQuery();
            ResultSet resultSet2 = preparedStatement2.executeQuery();
            if (resultSet.next()) {
                borrowAmount.setText(resultSet.getString(1));
            }
            if(resultSet1.next()) {
                favouriteAmount.setText(resultSet1.getString(1));
            }
            if(resultSet2.next()) {
                over.setText(resultSet2.getString(1));
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
        currentPage = "profileScene.fxml";
        SceneSwitcher.switchPage(ContentAnchorPane, currentPage, manager, darkMode.get());
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
                DarkModeController.darkMode.set(false);
                SceneSwitcher.SwitchScene(stage, "Login.fxml");
            }
        });
        user = null;
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
}