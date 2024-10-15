package com.example.javafx;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.sql.*;
import java.time.LocalDate;

public class SignUpController {

    @FXML
    private Button cancelSignupButton;
    @FXML
    private TextField setUsernameTextField;
    @FXML
    private PasswordField setPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label invalidUsernameLabel;
    @FXML
    private Label invalidPasswordLabel;
    @FXML
    private Label invalidConfirmPasswordLabel;
    @FXML
    private TextField firstnameTextField;
    @FXML
    private TextField lastnameTextField;
    @FXML
    private Button backToLoginButton;
    @FXML
    private Label titleLabel;
    @FXML
    private ComboBox<String> monthCombo;
    @FXML
    private ComboBox<String> dayCombo;
    @FXML
    private ComboBox<String> yearCombo;
    @FXML
    private Label invalidBirthDateLabel;
    @FXML
    private Button registerButton;

    /**
     * initialize the comboBox
     */
    public void initialize() {
        ObservableList<String> months = FXCollections.observableArrayList();
        for (int i=1 ; i<=12 ; i++) {
            months.add(Integer.toString(i));
        }
        monthCombo.setItems(months);

        ObservableList<String> days = FXCollections.observableArrayList();
        for(int i=1 ; i<=31 ; i++) {
            days.add(Integer.toString(i));
        }
        dayCombo.setItems(days);


        ObservableList<String> years = FXCollections.observableArrayList();
        for(int i=1900 ; i<=2024 ; i++) {
            years.add(Integer.toString(i));
        }
        yearCombo.setItems(years);

    }

    /**
     * check if the date is valid.
     * @return true if it's valid.
     */
    public boolean checkbirth (int year, int month, int day) {
        if(year > LocalDate.now().getYear()) {
            return false;
        } else if (year == LocalDate.now().getYear()) {
            if(month > LocalDate.now().getMonthValue()) {
                return false;
            } else if (month == LocalDate.now().getMonthValue()) {
                if(day > LocalDate.now().getDayOfMonth()) {
                    return false;
                }
            }
        } else if (year < LocalDate.now().getYear()) {
            return true;
        }
        return true;
    }

    /**
     * check if the user fill by themselves is valid or not.
     * @return true if it's.
     */
    public boolean validateFields() {
        String dayInput = (String) dayCombo.getValue();
        String monthInput = (String) monthCombo.getValue();
        String yearInput = (String) yearCombo.getValue();

        if (monthInput == null || monthInput.isBlank() || dayInput == null || dayInput.isBlank() || yearInput == null || yearInput.isBlank()) {
            invalidBirthDateLabel.setText("Please finish your birth date.");
            return false;
        }

        int month;
        try {
            month = Integer.parseInt(monthInput);
        } catch (NumberFormatException e) {
            invalidBirthDateLabel.setText("Month must be a number");
            return false;
        }
        int day;
        try {
            day = Integer.parseInt(dayInput);
        } catch (NumberFormatException e) {
            invalidBirthDateLabel.setText("Day of birth must be a number");
            return false;
        }
        int year;
        try {
            year = Integer.parseInt(yearInput);
        } catch (NumberFormatException e) {
            invalidBirthDateLabel.setText("Year must be a number");
            return false;
        }
        if (Integer.parseInt(monthInput) < 1 || Integer.parseInt(monthInput) > 12) {
            invalidBirthDateLabel.setText("please finish your birth date.");
            return false;
        }
        if (Integer.parseInt(dayInput) < 1 && Integer.parseInt(monthInput) > 31) {
            invalidBirthDateLabel.setText("please finish your birth date.");
            return false;
        }
        if(Integer.parseInt(yearInput) < 1900 || Integer.parseInt(yearInput) > 2024) {
            invalidBirthDateLabel.setText("please fill in your real year of birth.");
            return false;
        }
        if(Integer.parseInt(monthInput) == 2) {
            if (Integer.parseInt(yearInput) % 4 == 0 && Integer.parseInt(yearInput) % 100 != 0 || Integer.parseInt(yearInput) % 400 == 0) {
                if(Integer.parseInt(dayInput) > 29) {
                    invalidBirthDateLabel.setText("please finish your birth date.");
                    return false;
                }
            } else {
                if(Integer.parseInt(dayInput) > 28) {
                    invalidBirthDateLabel.setText("please finish your birth date.");
                    return false;
                }
            }
        } else if (Integer.parseInt(monthInput) == 4 || Integer.parseInt(monthInput) == 6 || Integer.parseInt(monthInput) == 9 || Integer.parseInt(monthInput) == 11) {
            if(Integer.parseInt(dayInput) > 30) {
                invalidBirthDateLabel.setText("please finish your birth date.");
                return false;
            }
        }

        if(checkbirth(year, month, day)) {
            return true;
        }
        return false;
    }

    /**
     * connect with database.
     */
    private DatabaseConnect databaseConnect;
    public  SignUpController(){
        this.databaseConnect = new DatabaseConnect();
    }

    /**
     * when sign up being pressed, window close.
     */
    public void cancelSignupButtonClickedOnAction() {
        Stage stage = (Stage) cancelSignupButton.getScene().getWindow();
        stage.close();
    }

    /**
     *
     * @param s is the password need to be checked if valid or not.
     * @return true if it's valid.
     */
    public boolean pass(String s) {
        for(char it : s.toCharArray()) {
            if(!Character.isLetterOrDigit(it)) {
                if(it != '@' && it != '_' && it != '-')
                    return false;
            }
        }
        return true;
    }

    /**
     * check the username and using released to check throughout the process.
     */
    @FXML
    public  void userNameReleased () {
        boolean check = true;
        if (setUsernameTextField.getText().isEmpty()) {
            invalidUsernameLabel.setText("You need to enter a username");
            invalidUsernameLabel.setStyle("-fx-text-fill: red");
            check = false;
        } else if (setUsernameTextField.getText().length() < 6 || setUsernameTextField.getText().length() > 15) {
            invalidUsernameLabel.setText("Username must be between 6 and 15 characters");
            invalidUsernameLabel.setStyle("-fx-text-fill: red");
            check = false;
        } else if(!pass(setUsernameTextField.getText())) {
            invalidUsernameLabel.setText("Username can not contains special characters except '@, '-', '_'.");
            invalidUsernameLabel.setStyle("-fx-text-fill: red");
            check = false;
        }
        else if(UsernameExisted()) {
            invalidUsernameLabel.setText("Username already exists");
            invalidUsernameLabel.setStyle("-fx-text-fill: red");
            check = false;
        } if(check) {
            invalidUsernameLabel.setText("Valid username");
            invalidUsernameLabel.setStyle("-fx-text-fill: green");
        }
    }


    /**
     * check the password and using released to check throughout the process.
     */

    @FXML
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


    public void registerButtonClickedOnAction() {
        /**
         * check birth's date.
         */
        userNameReleased();
        passwordRelease();
        confirmReleased();
        if (validateFields()) {
            invalidBirthDateLabel.setText("Valid Birth Date");
            invalidBirthDateLabel.setStyle("-fx-text-fill: green");
        }

        if (invalidUsernameLabel.getText().equals("Valid username") && invalidPasswordLabel.getText().equals("Valid Password")
                && invalidConfirmPasswordLabel.getText().equals("Valid Password") && validateFields()) {
            titleLabel.setText("Registration successful");
            registerUser();
        } else {
            titleLabel.setText("Registration failed!");
            titleLabel.setStyle("-fx-text-fill: red");
        }
    }

    public void registerEnter () {
        registerButton.setStyle("-fx-underline: true; -fx-background-color: rgba(0, 0, 0, 0.2); -fx-text-fill: green");
    }

    public void registerExited () {
        registerButton.setStyle("");
    }
    public void cancelEnter () {
        cancelSignupButton.setStyle("-fx-underline: true; -fx-background-color: rgba(0, 0, 0, 0.2); -fx-text-fill: red");
    }

    public void cancelExited () {
        cancelSignupButton.setStyle("");
    }

    public void backEnter () {
        backToLoginButton.setStyle("-fx-underline: true; -fx-background-color: rgba(0, 0, 0, 0.2); -fx-text-fill: yellow");
    }

    public void backExited () {
        backToLoginButton.setStyle("");
    }


    /**
     * export data into database.
     */
    public void registerUser() {
        String usernameInput = setUsernameTextField.getText();
        String passwordInput = setPasswordField.getText();
        String firstNameInput = firstnameTextField.getText();
        String lastNameInput = lastnameTextField.getText();
        String day = (String) dayCombo.getValue();
        String month = (String) monthCombo.getValue();
        String year = (String) yearCombo.getValue();

        String query = "insert into users (first_name, last_name, username, password, birthDate, monthDate, yearDate) value (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = databaseConnect.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, firstNameInput);
            preparedStatement.setString(2, lastNameInput);
            preparedStatement.setString(3, usernameInput);
            preparedStatement.setString(4, passwordInput);
            preparedStatement.setInt(5, Integer.parseInt(day));
            preparedStatement.setInt(6, Integer.parseInt(month));
            preparedStatement.setInt(7, Integer.parseInt(year));


            preparedStatement.executeUpdate();
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * check If new username is existed or not.
     * @return true if it's existed.
     */
    public boolean UsernameExisted() {
        String UsernameInput = setUsernameTextField.getText();
        String query = "SELECT * FROM users WHERE username = ?";

        try(Connection connection = databaseConnect.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, UsernameInput);
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

    /**
     * back to the login's interface.
     */
    public void backToLoginButtonOnAction() {
        Stage stage = (Stage)backToLoginButton.getScene().getWindow();
        SceneSwitcher.SwitchScene(stage,"Login.fxml");
    }
}
