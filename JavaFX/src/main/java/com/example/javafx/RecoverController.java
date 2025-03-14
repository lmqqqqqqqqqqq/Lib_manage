package com.example.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class    RecoverController {
    @FXML
    private Label InvalidLoginLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField codeTextfield;
    @FXML
    private Button backToLoginButton;
    @FXML
    private Button recoverButton;

    /**
     * the username use for NewPasswordController.
     */
    public static String Username;

    /**
     * Connect with the database.
     */
    DatabaseConnect databaseConnect = new DatabaseConnect();

    /**
     * check if the recover button being clicked and move to NewPasswordController.
     */
    public void recoverButtonClickedOnAction() {
        checkValid();
    }

    /**
     * same as recover button on action but with enter.
     *
     * @param event enter button being pressed.
     */
    public void recoverPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            checkValid();
        }
    }

    /**
     * checkValid function.
     */
    public void checkValid() {
        if (usernameTextField.getText().isBlank() && codeTextfield.getText().isBlank()) {
            InvalidLoginLabel.setText("You need to enter your username and recovery code");
            InvalidLoginLabel.setStyle("-fx-text-fill: red");
            usernameTextField.setStyle("-fx-border-color: red");
            codeTextfield.setStyle("-fx-border-color: red");
        } else if(usernameTextField.getText().isBlank())  {
            InvalidLoginLabel.setText("You need to enter your username");
            InvalidLoginLabel.setStyle("-fx-text-fill: red");
            usernameTextField.setStyle("-fx-border-color: red");
        } else if(codeTextfield.getText().isBlank())  {
            InvalidLoginLabel.setText("You need to enter your recovery code");
            InvalidLoginLabel.setStyle("-fx-text-fill: red");
            codeTextfield.setStyle("-fx-border-color: red");
        }
        else {
            if (validRecover()) {
                Stage stage = (Stage) recoverButton.getScene().getWindow();
                SceneSwitcher.SwitchScene(stage, "NewPassword.fxml");
            } else {
                InvalidLoginLabel.setText("Invalid code or username. Please try again !");
                InvalidLoginLabel.setStyle("-fx-text-fill: red");
                codeTextfield.setStyle("-fx-border-color: red");
                usernameTextField.setStyle("-fx-border-color: red");
            }
        }
    }

    /**
     * check if the username and code is true or not.
     * @return true if it;s right.
     */
    public boolean validRecover() {
        String username = usernameTextField.getText();
        String code = codeTextfield.getText();
        Username = username;

        String query = "Select * from users where username = ? and recoveryCode = ?";
        try (Connection connection = databaseConnect.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, code);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * back to the login's interface.
     */
    public void backToLoginButtonOnAction() {
        Stage stage = (Stage) backToLoginButton.getScene().getWindow();
        SceneSwitcher.SwitchScene(stage, "Login.fxml");
    }

    /**
     * using css for the effect
     */
    public void recoverEnter() {
        recoverButton.setStyle("-fx-underline: true; -fx-background-color: rgba(0, 0, 0, 0.2); -fx-text-fill: green");
    }

    public void recoverExited() {
        recoverButton.setStyle("");
    }

    public void backEnter() {
        backToLoginButton.setStyle("-fx-underline: true; -fx-background-color: rgba(0, 0, 0, 0.2); -fx-text-fill: yellow");
    }

    public void backExited() {
        backToLoginButton.setStyle("");
    }


}