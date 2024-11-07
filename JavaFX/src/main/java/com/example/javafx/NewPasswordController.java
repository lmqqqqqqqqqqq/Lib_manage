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
        confirmReleased();
        passwordReleased();

        if ((invalidPasswordLabel.getText().equals("Medium Password") || invalidPasswordLabel.getText().equals("Strong Password"))
                && invalidConfirmPasswordLabel.getText().equals("Valid Password")) {
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

        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(4));

        FadeTransition fadeOutTransition = new FadeTransition();
        fadeOutTransition.setNode(label);  // Gán Label vào hiệu ứng
        fadeOutTransition.setDuration(Duration.seconds(2));  // Thời gian fade in
        fadeOutTransition.setFromValue(1);  // Từ độ trong suốt là 1 ( nhìn thấy)
        fadeOutTransition.setToValue(0);    // Đến độ trong suốt là 0 (khong nhìn thấy)

        SequentialTransition sequentialTransition = new SequentialTransition(fadeInTransition, fadeOutTransition);
        sequentialTransition.setOnFinished(_ -> {
            Stage stage = (Stage) successfulLabel.getScene().getWindow();
            SceneSwitcher.SwitchScene(stage, "Login.fxml");
        });
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
     * check the password and using released to check throughout the process.
     */
    public void passwordReleased() {
        String password = setPasswordField.getText();
        if (password.isEmpty()) {
            invalidPasswordLabel.setText("You need to enter a password");
            invalidPasswordLabel.setStyle("-fx-text-fill: red");
            setPasswordField.setStyle("-fx-border-color: red");
        } else if (password.length() < 6 || password.length() > 50) {
            invalidPasswordLabel.setText("Password should be between 6 and 50 characters");
            invalidPasswordLabel.setStyle("-fx-text-fill: red");
            setPasswordField.setStyle("-fx-border-color: red");
        } else {
            boolean checkS = false;
            boolean checkD = false;
            boolean checkA = false;
            boolean checkU = false;
            for (int i = 0; i < password.length(); i++) {
                if(Character.isDigit(password.charAt(i))) {
                    checkD = true;
                } else if (Character.isLowerCase(password.charAt(i))) {
                    checkA = true;
                } else if (!Character.isDigit(password.charAt(i)) && !Character.isLetter(password.charAt(i))) {
                    checkS = true;
                } else if (Character.isUpperCase(password.charAt(i))) {
                    checkU = true;
                }
            }
            if (checkS && checkD && checkA && checkU && password.length() > 11) {
                invalidPasswordLabel.setText("Strong Password");
                invalidPasswordLabel.setStyle("-fx-text-fill: #4CAF50");
                setPasswordField.setStyle("-fx-border-color: #4CAF50;");
            }
            else if (checkA && (checkD || checkS) && password.length() > 7) {
                invalidPasswordLabel.setText("Medium Password");
                invalidPasswordLabel.setStyle("-fx-text-fill: #c3c30b");
                setPasswordField.setStyle("-fx-border-color: #c3c30b;");
            }
            else {
                invalidPasswordLabel.setText("Week Password");
                invalidPasswordLabel.setStyle("-fx-text-fill: red");
                setPasswordField.setStyle("-fx-border-color: red;");
            }
        }
    }

    /**
     * check the confirmation and using released to check throughout the process.
     */
    public void confirmReleased() {
        if (!(invalidPasswordLabel.getText().equals("Medium Password") || invalidPasswordLabel.getText().equals("Strong Password"))) {
            invalidConfirmPasswordLabel.setText("Please enter a valid password");
            invalidConfirmPasswordLabel.setStyle("-fx-text-fill: red");
            confirmPasswordField.setStyle("-fx-border-color: red");
        } else if (confirmPasswordField.getText().isEmpty()) {
            invalidConfirmPasswordLabel.setText("You need to enter a confirm password");
            invalidConfirmPasswordLabel.setStyle("-fx-text-fill: red");
            confirmPasswordField.setStyle("-fx-border-color: red");
        } else if (!confirmPasswordField.getText().equals(setPasswordField.getText())) {
            invalidConfirmPasswordLabel.setText("Password does not match");
            invalidConfirmPasswordLabel.setStyle("-fx-text-fill: red");
            confirmPasswordField.setStyle("-fx-border-color: red");
        } else {
            invalidConfirmPasswordLabel.setText("Valid Password");
            invalidConfirmPasswordLabel.setStyle("-fx-text-fill: #4CAF50");
            confirmPasswordField.setStyle("-fx-border-color: #4CAF50");
        }
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