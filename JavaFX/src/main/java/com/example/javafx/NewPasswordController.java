package com.example.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    DatabaseConnect databaseConnect = new DatabaseConnect();
    public NewPasswordController() {
        this.databaseConnect = new DatabaseConnect();
    }

    public void passwordRelease () {
        boolean check = true;
        if (setPasswordField.getText().isEmpty()) {
            invalidPasswordLabel.setText("You need to enter a password");
            invalidPasswordLabel.setStyle("-fx-text-fill: red");
            check = false;
        } else if (setPasswordField.getText().length() < 6 || setPasswordField.getText().length() > 50) {
            invalidPasswordLabel.setText("Password should be between 6 and 50 characters");
            invalidPasswordLabel.setStyle("-fx-text-fill: red");
            check = false;
        } if (check) {
            invalidPasswordLabel.setText("Valid Password");
            invalidPasswordLabel.setStyle("-fx-text-fill: green");
        }
    }

    /**
     * check the confirm and using released to check throughout the process.
     */

    public void confirmReleased () {
        boolean check = true;
        if (!invalidPasswordLabel.getText().equals("Valid Password")) {
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
        } if(check) {
            invalidConfirmPasswordLabel.setText("Valid Password");
            invalidConfirmPasswordLabel.setStyle("-fx-text-fill: green");
        }
    }

    public void passwordUser() {
        try(Connection connection = databaseConnect.connect()) {
            String password1 = setPasswordField.getText();
            String username1 = RecoverController.Username;
            String query = "update users set password = ? where username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, password1);
            preparedStatement.setString(2, username1);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkValid() {
        if(invalidPasswordLabel.getText().equals("Valid Password") && invalidConfirmPasswordLabel.getText().equals("Valid Password")) {
            titleLabel.setText("Success!");
            titleLabel.setStyle("-fx-text-fill: green");
            passwordUser();
        } else {
            titleLabel.setText("Failed");
            titleLabel.setStyle("-fx-text-fill: red");
        }
    }

    public void setButtonClickedOnAction() {
        checkValid();
    }

    public void setPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            checkValid();
        }
    }

    public void backToLoginButtonOnAction() {
        Stage stage = (Stage) backToLoginButton.getScene().getWindow();
        SceneSwitcher.SwitchScene(stage, "Login.fxml");
    }

    public void backEnter () {
        backToLoginButton.setStyle("-fx-underline: true; -fx-background-color: rgba(0, 0, 0, 0.2); -fx-text-fill: yellow");
    }

    public void backExited () {
        backToLoginButton.setStyle("");
    }

    public void setEnter () {
        setButton.setStyle("-fx-underline: true; -fx-background-color: rgba(0, 0, 0, 0.2); -fx-text-fill: yellow");
    }

    public void setExited () {
        setButton.setStyle("");
    }

}
