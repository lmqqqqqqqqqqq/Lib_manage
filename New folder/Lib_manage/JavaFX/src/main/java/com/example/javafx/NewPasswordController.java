package com.example.javafx;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class NewPasswordController {
    @FXML
    public Label titleLabel1;
    @FXML
    private PasswordField setPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label invalidPasswordLabel;
    @FXML
    private Label invalidConfirmPasswordLabel;
    @FXML
    private Button backToLoginButton;
    @FXML
    private Button setButton;
    @FXML
    private Label successfulLabel;
    @FXML
    private Label failedLabel;
    /**
     * connect with database.
     */
    DatabaseConnect databaseConnect = new DatabaseConnect();

    /**
     * check if the set button being clicked and update the database.
     */
    public void setButtonClickedOnAction() {
        checkValid();
    }

    /**
     * same as the set button on action but with enter.
     * @param event enter being pressed.
     */
    public void setPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            checkValid();
        }
    }

    /**
     * checkValid function.
     */
    public void checkValid() {
        if (isValidPassword && isValidConfirm) {
            successfulLabel.setVisible(true);
            fadeAnimation(successfulLabel);
            passwordUser();
        } else {
            failedLabel.setVisible(true);
            fadeAnimation(failedLabel);
        }
    }

    /**
     * fade animation.
     */
    public void fadeAnimation(Label label) {
        FadeTransition fadeInTransition = new FadeTransition();
        fadeInTransition.setNode(label);  // Gán Label vào hiệu ứng
        fadeInTransition.setDuration(Duration.seconds(2));  // Thời gian fade in
        fadeInTransition.setFromValue(0);  // Từ độ trong suốt là 0 (không nhìn thấy)
        fadeInTransition.setToValue(1);    // Đến độ trong suốt là 1 (hoàn toàn nhìn thấy)

        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1.5));

        FadeTransition fadeOutTransition = new FadeTransition();
        fadeOutTransition.setNode(label);  // Gán Label vào hiệu ứng
        fadeOutTransition.setDuration(Duration.seconds(2));  // Thời gian fade in
        fadeOutTransition.setFromValue(1);  // Từ độ trong suốt là 1 ( nhìn thấy)
        fadeOutTransition.setToValue(0);    // Đến độ trong suốt là 0 (khong nhìn thấy)

        SequentialTransition sequentialTransition = new SequentialTransition(fadeInTransition, pauseTransition, fadeOutTransition);
        if(label == successfulLabel) {
            sequentialTransition.setOnFinished(_ -> {
                Stage stage = (Stage) successfulLabel.getScene().getWindow();
                SceneSwitcher.SwitchScene(stage, "Login.fxml");
            });
        }
        sequentialTransition.play();
    }

    /**
     * update the new password in the database using the username form recoverController.
     */
    public void passwordUser() {
        try (Connection connection = databaseConnect.connect()) {
            String password1 = setPasswordField.getText();
            String username1 = RecoverController.Username;
            String query = "update users set password = ? where username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, password1);
            preparedStatement.setString(2, username1);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * using a list to wrap the boolean to take the value of checkPassword from Released.
     */
    public boolean[] checkPassword = {false};
    public boolean isValidPassword;

    public void passwordReleased() {
        Released.passwordReleased(setPasswordField, invalidPasswordLabel, checkPassword);
        isValidPassword = checkPassword[0];
    }

    /**
     * using a list to wrap the boolean to take the value of checkConfirm from Released.
     */
    public boolean[] checkConfirm = {false};
    public boolean isValidConfirm;

    public void confirmReleased() {
        Released.confirmReleased(confirmPasswordField, setPasswordField, invalidPasswordLabel, invalidConfirmPasswordLabel, checkConfirm);
        isValidConfirm = checkConfirm[0];
    }

    public void initialize() {
        successfulLabel.setVisible(false);
        failedLabel.setVisible(false);
    }

    /**
     * back to the login's interface.
     */
    public void backToLoginButtonOnAction() {
        Stage stage = (Stage) backToLoginButton.getScene().getWindow();
        SceneSwitcher.SwitchScene(stage, "Login.fxml");
    }

    /**
     * using css for the effect.
     */
    public void backEnter() {
        backToLoginButton.setStyle("-fx-underline: true; -fx-background-color: rgba(0, 0, 0, 0.2); -fx-text-fill: yellow");
    }

    public void backExited() {
        backToLoginButton.setStyle("");
    }

    public void setEnter() {
        setButton.setStyle("-fx-underline: true; -fx-background-color: rgba(0, 0, 0, 0.2); -fx-text-fill: green");
    }

    public void setExited() {
        setButton.setStyle("");
    }

}