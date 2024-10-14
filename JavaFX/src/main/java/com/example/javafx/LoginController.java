package com.example.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
    private Button signUpButton;

    /**
     * connect with database.
     */
    private DatabaseConnect databaseConnect;
    public  LoginController(){
        this.databaseConnect = new DatabaseConnect();
    }

    /**
     *check if the login button being clicked and check in the database by the validLogin().
     */
    public  void loginButtonClickedOnAction() {
        if (usernameTextField.getText().isBlank() || enterPasswordField.getText().isBlank()) {
            InvalidLoginLabel.setText("You need to enter a username and password");
            InvalidLoginLabel.setStyle("-fx-text-fill: red");
        } else {
            if(validLogin()) {
                InvalidLoginLabel.setText("Redirecting!");
                InvalidLoginLabel.setStyle("-fx-text-fill: green");
            } else {
                InvalidLoginLabel.setText("Invalid password or username. Please try again !");
                InvalidLoginLabel.setStyle("-fx-text-fill: red");
            }
        }

    }

    /**
     * check if the button is clicked and close the window then.
     */
    

    /**
     * close the login window and open the register window.
     */
    public void signUpButtonClickedOnAction() {
        Stage stage = (Stage) signUpButton.getScene().getWindow();
        SceneSwitcher.swingScene(stage,"SignUp.FXML");
    }
    /**
     * use the database's information and check if it's exist or not.
     * @return true if the login information is correct .
     */
    public boolean validLogin() {

        //the input of username and password.
        String usernameInp = usernameTextField.getText();
        String passwordInp = enterPasswordField.getText();

        //use the query and preparedStatement to keep it safety.
        String query = "SELECT * FROM users WHERE username = ? and password = ?";

        //use connection() in the Dtconnector class to link with the database.
        //use try catch to close the connection.
        try (Connection connection = databaseConnect.connect()) {
            //use prepared to take the input login's information.
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, usernameInp);
            preparedStatement.setString(2, passwordInp);
            //resultset being used to check in the database and return true if it's exist.
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}