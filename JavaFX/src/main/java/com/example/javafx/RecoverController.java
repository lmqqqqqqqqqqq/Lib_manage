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

public class RecoverController {
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

    public static String Username;

    private DatabaseConnect databaseConnect;
    public RecoverController(){
        this.databaseConnect = new DatabaseConnect();
    }

    public void backToLoginButtonOnAction() {
        Stage stage = (Stage)backToLoginButton.getScene().getWindow();
        SceneSwitcher.SwitchScene(stage,"Login.fxml");
    }

    public void recoverButtonClickedOnAction() {
        checkValid();
    }

    public void recoverPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            checkValid();
        }
    }

    public void checkValid () {
        if (usernameTextField.getText().isBlank() || codeTextfield.getText().isBlank()) {
            InvalidLoginLabel.setText("You need to enter a username and code");
            InvalidLoginLabel.setStyle("-fx-text-fill: red");
        } else {
            if(validRecover()) {
                Stage stage = (Stage)recoverButton.getScene().getWindow();
                SceneSwitcher.SwitchScene(stage,"NewPassword.fxml");
            } else {
                InvalidLoginLabel.setText("Invalid code or username. Please try again !");
                InvalidLoginLabel.setStyle("-fx-text-fill: red");
            }
        }
    }

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
            if(resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void recoverEnter () {
        recoverButton.setStyle("-fx-underline: true; -fx-background-color: rgba(0, 0, 0, 0.2); -fx-text-fill: green");
    }

    public void recoverExited () {
        recoverButton.setStyle("");
    }

    public void backEnter () {
        backToLoginButton.setStyle("-fx-underline: true; -fx-background-color: rgba(0, 0, 0, 0.2); -fx-text-fill: yellow");
    }

    public void backExited () {
        backToLoginButton.setStyle("");
    }


}
