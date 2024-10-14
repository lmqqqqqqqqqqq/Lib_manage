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
    private ComboBox monthCombo;
    @FXML
    private ComboBox dayCombo;
    @FXML
    private ComboBox yearCombo;
    @FXML
    private Label invalidBirthDateLabel;

    /**
     * initialize the comboBox
     */
    public void initialize() {
        ObservableList<Integer> months = FXCollections.observableArrayList();
        for (int i=1 ; i<=12 ; i++) {
            months.add(i);
        }
        monthCombo.setItems(months);

        ObservableList<Integer> days = FXCollections.observableArrayList();
        for (int i=1 ; i<=31 ; i++) {
            days.add(i);
        }
        dayCombo.setItems(days);

        ObservableList<Integer> years = FXCollections.observableArrayList();
        for(int i=1900 ; i<=2024 ; i++) {
            years.add(i);
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
     * check the condition of username, password, confirm password, if valid information, export data to database.
     */
    public void registerButtonClickedOnAction() {
        boolean isValid = true; //valid registration check.

        // check username
        if (setUsernameTextField.getText().isEmpty()) {
            invalidUsernameLabel.setText("You need to enter a username");
            invalidUsernameLabel.setStyle("-fx-text-fill: red");
            isValid = false;
        } else if (setUsernameTextField.getText().length() < 6 || setUsernameTextField.getText().length() > 15) {
            invalidUsernameLabel.setText("Username must be between 6 and 15 characters");
            invalidUsernameLabel.setStyle("-fx-text-fill: red");
            isValid = false;
        } else if(!pass(setUsernameTextField.getText())) {
            invalidUsernameLabel.setText("Username can not contains special characters except '@, '-', '_'.");
            invalidUsernameLabel.setStyle("-fx-text-fill: red");
        }
        else {
            invalidUsernameLabel.setText("Valid username");
            invalidUsernameLabel.setStyle("-fx-text-fill: green");
        }

        // check password.
        if (setPasswordField.getText().isEmpty()) {
            invalidPasswordLabel.setText("You need to enter a password");
            invalidPasswordLabel.setStyle("-fx-text-fill: red");
            isValid = false;
        } else if (setPasswordField.getText().length() < 6 || setPasswordField.getText().length() > 50) {
            invalidPasswordLabel.setText("Password should be between 6 and 50 characters");
            invalidPasswordLabel.setStyle("-fx-text-fill: red");
            isValid = false;
        } else {
            invalidPasswordLabel.setText("Valid password");
            invalidPasswordLabel.setStyle("-fx-text-fill: green");
        }

        // confirm password check.
        if (!invalidPasswordLabel.getText().equals("Valid password")) {
            invalidConfirmPasswordLabel.setText("Please enter a valid password");
            invalidConfirmPasswordLabel.setStyle("-fx-text-fill: red");
        } else if (confirmPasswordField.getText().isEmpty()) {
            invalidConfirmPasswordLabel.setText("You need to enter a confirm password");
            invalidConfirmPasswordLabel.setStyle("-fx-text-fill: red");
        } else if (!confirmPasswordField.getText().equals(setPasswordField.getText())) {
            invalidConfirmPasswordLabel.setText("Password does not match");
            invalidConfirmPasswordLabel.setStyle("-fx-text-fill: red");
            isValid = false;
        } else {
            invalidConfirmPasswordLabel.setText("Valid Password");
            invalidConfirmPasswordLabel.setStyle("-fx-text-fill: green");
        }

        //birthDate check

        if (validateFields()) {
            invalidBirthDateLabel.setText("Valid Birth Date");
        } else {
            isValid = false;
        }

        if (isValid) {
            if(!UsernameExisted()) {
                titleLabel.setText("Registration successful");
                registerUser();
            } else {
                titleLabel.setText("Registration failed!");
                invalidUsernameLabel.setText("Username already exists");
                invalidUsernameLabel.setStyle("-fx-text-fill: red");
            }
        } else {
            titleLabel.setText("Registration failed!");
            titleLabel.setStyle("-fx-text-fill: red");
        }
    }

    /**
     * export data into database.
     */
    public void registerUser() {
        String usernameInput = setUsernameTextField.getText();
        String passwordInput = setPasswordField.getText();
        String firstNameInput = firstnameTextField.getText();
        String lastNameInput = lastnameTextField.getText();
        Integer day = (Integer) dayCombo.getValue();
        Integer month = (Integer) monthCombo.getValue();
        Integer year = (Integer) yearCombo.getValue();
        StringBuilder m = new StringBuilder();

        String query = "insert into users (first_name, last_name, username, password, birthDate, monthDate, yearDate) value (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = databaseConnect.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, firstNameInput);
            preparedStatement.setString(2, lastNameInput);
            preparedStatement.setString(3, usernameInput);
            preparedStatement.setString(4, passwordInput);
            preparedStatement.setInt(5, day);
            preparedStatement.setInt(6, month);
            preparedStatement.setInt(7, year);


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
