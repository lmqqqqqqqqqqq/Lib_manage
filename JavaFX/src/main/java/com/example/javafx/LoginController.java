package com.example.javafx;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.*;

public class LoginController {
    @FXML
    private Button cancelButton;
    @FXML
    private Label InvalidLoginLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField enterPasswordField;
    @FXML
    private Button loginButton;
    @FXML
    private Label signUpLabelClicked;
    @FXML
    private Label forgetLabelClicked;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    /**
     * connect with database.
     */
    DatabaseConnect databaseConnect = new DatabaseConnect();

    /**
     * check if the login button being clicked and move to main's interface.
     */
    public void loginButtonClickedOnAction() {
        checkValid();
    }

    /**
     * same as the login button on action but with enter.
     * @param event enter.
     */
    public void loginButtonPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            checkValid();
        }
    }

    /**
     * checkValid function.
     */
    public void checkValid() {
        if (usernameTextField.getText().isBlank() && enterPasswordField.getText().isBlank()) {
            InvalidLoginLabel.setText("You need to enter a username and password");
            InvalidLoginLabel.setStyle("-fx-text-fill: red");
            usernameTextField.setStyle("-fx-border-color: red");
        }
        if (usernameTextField.getText().isBlank()) {
            InvalidLoginLabel.setText("You need to enter a username");
            InvalidLoginLabel.setStyle("-fx-text-fill: red");
            usernameTextField.setStyle("-fx-border-color: red");
        } else if(enterPasswordField.getText().isBlank()) {
            InvalidLoginLabel.setText("You need to enter a password");
            InvalidLoginLabel.setStyle("-fx-text-fill: red");
            enterPasswordField.setStyle("-fx-border-color: red");
        }
        else {
            User user = validLogin();
            if (user != null) {
                Stage stage = (Stage) loginButton.getScene().getWindow();
                SceneSwitcher.SwitchScene(stage, "mainScene.fxml");
            } else {
                InvalidLoginLabel.setText("Invalid password or username. Please try again !");
                InvalidLoginLabel.setStyle("-fx-text-fill: red");
            }
        }
    }

    /**
     * take information in the database to check the valid login and give information to user.
     * @return the information.
     */
    public User validLogin()  {

        //the input of username and password.
        String usernameInp = usernameTextField.getText();
        String passwordInp = enterPasswordField.getText();

        //use the query and preparedStatement to keep it safety.
        String query = "SELECT * FROM users WHERE username = ? and password = ?";

        //use connection() in the DataConnector class to link with the database.
        //use try catch to close the connection.
        try (Connection connection = databaseConnect.connect()) {
            //use prepared to take the input login's information.
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, usernameInp);
            preparedStatement.setString(2, passwordInp);
            //resultSet being used to check in the database and return true if it's exist.
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                int id = resultSet.getInt("idusers");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String firstname = resultSet.getString("first_name");
                String lastname = resultSet.getString("last_name");
                int dayOfBirth = resultSet.getInt("dayOfBirth");
                int monthOfBirth = resultSet.getInt("monthOfBirth");
                int yearOfBirth = resultSet.getInt("yearOfBirth");
                String recoveryCode = resultSet.getString("recoveryCode");
                return new User(id, firstname, lastname, username, password, dayOfBirth, monthOfBirth, yearOfBirth);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * check if the button is clicked and close the window then.
     */
    public void cancelButtonClickedOnAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * close the login window and open the register window.
     */
    public void signUpLabelClick() {
        Stage stage = (Stage) signUpLabelClicked.getScene().getWindow();
        SceneSwitcher.SwitchScene(stage, "SignUp.fxml");
    }

    /**
     * close the login window and open the checkForget window.
     */
    public void forgetLabelClick() {
        Stage stage = (Stage) forgetLabelClicked.getScene().getWindow();
        SceneSwitcher.SwitchScene(stage, "RecoverPassword.fxml");
    }

    /**
     * using css for the effect.
     */
    public void loginEnter() {
        loginButton.setStyle("-fx-underline: true; -fx-background-color: rgba(0, 0, 0, 0.2); -fx-text-fill: blue");
    }

    public void loginExited() {
        loginButton.setStyle("");
    }

    public void cancelEnter() {
        cancelButton.setStyle("-fx-underline: true; -fx-background-color: rgba(0, 0, 0, 0.2); -fx-text-fill: blue");
    }

    public void cancelExited() {
        cancelButton.setStyle("");
    }

    public void signUpMouseEnter() {
        signUpLabelClicked.setStyle("-fx-text-fill: red; -fx-underline: true;");
    }

    public void signUpMouseExited() {
        signUpLabelClicked.setStyle("-fx-text-fill: #9000ff; -fx-underline: false;");
    }

    public void forgetMouseEnter() {
        forgetLabelClicked.setStyle("-fx-text-fill: red; -fx-underline: true;");
    }

    public void forgetMouseExited() {
        forgetLabelClicked.setStyle("-fx-text-fill: #000000; -fx-underline: false;");
    }

    public void loginMouseEnter() {
        loginAnimation(true, usernameLabel );
    }

    public void loginMouseExited() {
        loginAnimation(false, usernameLabel );
    }

    public void passwordMouseEnter() {
        loginAnimation(true, passwordLabel );
    }

    public void passwordMouseExited() {
        loginAnimation(false, passwordLabel );
    }

    public void loginAnimation(boolean isEntered, Label label) {
        TranslateTransition animation = new TranslateTransition();
        animation.setNode(label);
        if (isEntered) {
            animation.setByY(-15);
        } else {
            animation.setByY(15);
        }
        animation.setDuration(Duration.seconds(0.05)); // 0.3s cho sự mượt mà
        animation.setCycleCount(1);
        animation.setAutoReverse(false); // Không quay lại khi hết thời gian
        animation.play();
    }

}