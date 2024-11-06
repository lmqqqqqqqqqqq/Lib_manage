package com.example.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

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
    private Label titleLabel;
    @FXML
    private Button backToLoginButton;
    @FXML
    private Button setButton;

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
            titleLabel.setText("Success!");
            titleLabel1.setText("");
            titleLabel.setStyle("-fx-text-fill: green");
            passwordUser();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success !");
            alert.setHeaderText(null);
            alert.setContentText("Password change successfully!");
            alert.showAndWait();
        } else {
            titleLabel.setText("Failed to change password!");
            titleLabel.setStyle("-fx-text-fill: red");
            titleLabel1.setText("");
        }
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
        } catch (Exception _) {
        }
    }

    /**
     * check the password and using released to check throughout the process.
     */
    public void passwordReleased() {
        boolean check = true;
        String password = setPasswordField.getText();
        if (password.isEmpty()) {
            invalidPasswordLabel.setText("You need to enter a password");
            invalidPasswordLabel.setStyle("-fx-text-fill: red");
            check = false;
        } else if (password.length() < 6 || password.length() > 50) {
            invalidPasswordLabel.setText("Password should be between 6 and 50 characters");
            invalidPasswordLabel.setStyle("-fx-text-fill: red");
            check = false;
        }
        if (check) {
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
                invalidPasswordLabel.setStyle("-fx-text-fill: green");
            }
            else if (checkA && (checkD || checkS) && password.length() > 7) {
                invalidPasswordLabel.setText("Medium Password");
                invalidPasswordLabel.setStyle("-fx-text-fill: yellow");
            }
            else {
                invalidPasswordLabel.setText("Week Password");
                invalidPasswordLabel.setStyle("-fx-text-fill: red");
            }
        }
    }

    /**
     * check the confirmation and using released to check throughout the process.
     */
    public void confirmReleased() {
        boolean check = true;
        if (!(invalidPasswordLabel.getText().equals("Medium Password") || invalidPasswordLabel.getText().equals("Strong Password"))) {
            invalidConfirmPasswordLabel.setText("Please enter a valid password");
            invalidConfirmPasswordLabel.setStyle("-fx-text-fill: red");
            check = false;
        } else if (confirmPasswordField.getText().isEmpty()) {
            invalidConfirmPasswordLabel.setText("You need to enter a confirm password");
            invalidConfirmPasswordLabel.setStyle("-fx-text-fill: red");
            check = false;
        } else if (!confirmPasswordField.getText().equals(setPasswordField.getText())) {
            invalidConfirmPasswordLabel.setText("Password does not match");
            invalidConfirmPasswordLabel.setStyle("-fx-text-fill: red");
            check = false;
        }
        if (check) {
            invalidConfirmPasswordLabel.setText("Valid Password");
            invalidConfirmPasswordLabel.setStyle("-fx-text-fill: green");
        }
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
