package com.example.javafx;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    private ComboBox<String> monthCombo;
    @FXML
    private ComboBox<String> dayCombo;
    @FXML
    private ComboBox<String> yearCombo;
    @FXML
    private Label invalidBirthDateLabel;
    @FXML
    private Button registerButton;
    @FXML
    private TextField codeTextfield;
    @FXML
    private Label invalidRecover;
    @FXML
    private Label invalidFirstNameLabel;
    @FXML
    private Label successfulLabel;
    @FXML
    private Label failedLabel;
    /**
     * connect with database.
     */
    DatabaseConnect databaseConnect = new DatabaseConnect();
    /**
     * check if the signup button being clicked.
     */


    public void registerButtonClickedOnAction() {
        checkValid();
    }

    public void registerPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            checkValid();
        }
    }

    /**
     * checkValid function.
     */
    public void checkValid() {

        userNameReleased();
        passwordReleased();
        confirmReleased();
        firstnameReleased();
        recoveryReleased();

        if (validateFields()) {
            invalidBirthDateLabel.setText("Valid Birth Date");
            invalidBirthDateLabel.setStyle("-fx-text-fill: #4CAF50");
        }

        if (invalidUsernameLabel.getText().equals("Valid username") && (invalidPasswordLabel.getText().equals("Medium Password")
                || invalidPasswordLabel.getText().equals("Strong Password"))
                && invalidConfirmPasswordLabel.getText().equals("Valid Password")
                && invalidRecover.getText().equals("Valid Code") && validateFields()
                && invalidFirstNameLabel.getText().equals("Valid")) {
            successfulLabel.setVisible(true);
            fadeAnimation(successfulLabel);
            registerUser();
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

        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));

        FadeTransition fadeOutTransition = new FadeTransition();
        fadeOutTransition.setNode(label);  // Gán Label vào hiệu ứng
        fadeOutTransition.setDuration(Duration.seconds(2));  // Thời gian fade in
        fadeOutTransition.setFromValue(1);  // Từ độ trong suốt là 1 ( nhìn thấy)
        fadeOutTransition.setToValue(0);    // Đến độ trong suốt là 0 (khong nhìn thấy)

        SequentialTransition sequentialTransition = new SequentialTransition(fadeInTransition, pauseTransition, fadeOutTransition);
        sequentialTransition.setOnFinished(_ -> {
            Stage stage = (Stage) successfulLabel.getScene().getWindow();
            SceneSwitcher.SwitchScene(stage, "Login.fxml");
        });
        sequentialTransition.play();
    }

    /**
     * export data into database.
     */
    public void registerUser() {
        String usernameInput = setUsernameTextField.getText();
        String passwordInput = setPasswordField.getText();
        String firstNameInput = firstnameTextField.getText();
        String lastNameInput = lastnameTextField.getText();
        String recoveryCode = codeTextfield.getText();
        String day = dayCombo.getValue();
        String month = monthCombo.getValue();
        String year = yearCombo.getValue();


        String query = "insert into users (first_name, last_name, username, password, dayOfBirth, monthOfBirth, yearOfBirth, recoveryCode) value (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = databaseConnect.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, firstNameInput);
            preparedStatement.setString(2, lastNameInput);
            preparedStatement.setString(3, usernameInput);
            preparedStatement.setString(4, passwordInput);
            preparedStatement.setInt(5, Integer.parseInt(day));
            preparedStatement.setInt(6, Integer.parseInt(month));
            preparedStatement.setInt(7, Integer.parseInt(year));
            preparedStatement.setString(8, recoveryCode);


            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * check If new username is existed or not.
     *
     * @return true if it's existed.
     */
    public boolean UsernameExisted() {
        String UsernameInput = setUsernameTextField.getText();
        String query = "SELECT * FROM users WHERE username = ?";

        try (Connection connection = databaseConnect.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, UsernameInput);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * check the username and using released to check throughout the process.
     */
    public void userNameReleased() {
        if (setUsernameTextField.getText().isEmpty()) {
            invalidUsernameLabel.setText("You need to enter a username");
            invalidUsernameLabel.setStyle("-fx-text-fill: red");
            setUsernameTextField.setStyle("-fx-border-color: red");
        } else if (setUsernameTextField.getText().length() < 6 || setUsernameTextField.getText().length() > 15) {
            invalidUsernameLabel.setText("Username must be between 6 and 15 characters");
            invalidUsernameLabel.setStyle("-fx-text-fill: red");
            setUsernameTextField.setStyle("-fx-border-color: red");
        } else if (!pass(setUsernameTextField.getText())) {
            invalidUsernameLabel.setText("Username can not contains special characters except '@, '-', '_'.");
            invalidUsernameLabel.setStyle("-fx-text-fill: red");
            setUsernameTextField.setStyle("-fx-border-color: red");
        } else if (UsernameExisted()) {
            invalidUsernameLabel.setText("Username already exists");
            invalidUsernameLabel.setStyle("-fx-text-fill: red");
            setUsernameTextField.setStyle("-fx-border-color: red");
        } else {
            invalidUsernameLabel.setText("Valid username");
            invalidUsernameLabel.setStyle("-fx-text-fill: #4CAF50");
            setUsernameTextField.setStyle("-fx-border-color: #4CAF50");
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

    /**
     * check the firstname and using released to check throughout the process.
     */
    public void firstnameReleased() {
        if (firstnameTextField.getText().isEmpty()) {
            invalidFirstNameLabel.setText("Please enter your first name");
            invalidFirstNameLabel.setStyle("-fx-text-fill: red");
            firstnameTextField.setStyle("-fx-border-color: red");
        } else {
            invalidFirstNameLabel.setText("Valid");
            invalidFirstNameLabel.setStyle("-fx-text-fill: #4CAF50");
            firstnameTextField.setStyle("-fx-border-color: #4CAF50");
        }
    }

    /**
     * check the code and using released to check throughout the process.
     */
    public void recoveryReleased() {
        if (codeTextfield.getText().isEmpty()) {
            invalidRecover.setText("Please fill in your recover code");
            invalidRecover.setStyle("-fx-text-fill: red");
            codeTextfield.setStyle("-fx-border-color: red");
        } else {
            invalidRecover.setText("Valid Code");
            invalidRecover.setStyle("-fx-text-fill: #4CAF50");
            codeTextfield.setStyle("-fx-border-color: #4CAF50");
        }
    }

    /**
     * initialize
     */
    public void initialize() {
        successfulLabel.setVisible(false);
        failedLabel.setVisible(false);
        ObservableList<String> months = FXCollections.observableArrayList();
        for (int i = 1; i <= 12; i++) {
            months.add(Integer.toString(i));
        }
        monthCombo.setItems(months);

        ObservableList<String> days = FXCollections.observableArrayList();
        for (int i = 1; i <= 31; i++) {
            days.add(Integer.toString(i));
        }
        dayCombo.setItems(days);


        ObservableList<String> years = FXCollections.observableArrayList();
        for (int i = 1900; i <= 2024; i++) {
            years.add(Integer.toString(i));
        }
        yearCombo.setItems(years);

    }

    /**
     * check if the date is valid.
     *
     * @return true if it's valid.
     */
    public boolean checkBirth(int year, int month, int day) {
        if (year > LocalDate.now().getYear()) {
            return false;
        } else if (year == LocalDate.now().getYear()) {
            if (month > LocalDate.now().getMonthValue()) {
                return false;
            } else if (month == LocalDate.now().getMonthValue()) {
                return day <= LocalDate.now().getDayOfMonth();
            }
        } else if (year < LocalDate.now().getYear()) {
            return true;
        }
        return true;
    }

    /**
     * check if the user fill by themselves is valid or not.
     *
     * @return true if it's.
     */
    public boolean validateFields() {
        String dayInput = dayCombo.getValue();
        String monthInput = monthCombo.getValue();
        String yearInput = yearCombo.getValue();

        if (monthInput == null || monthInput.isBlank() || dayInput == null || dayInput.isBlank() || yearInput == null || yearInput.isBlank()) {
            invalidBirthDateLabel.setText("Please finish your birth date.");
            invalidBirthDateLabel.setStyle("-fx-text-fill: red;");
            return false;
        }

        int month;
        try {
            month = Integer.parseInt(monthInput);
        } catch (NumberFormatException e) {
            invalidBirthDateLabel.setText("Month must be a number");
            invalidBirthDateLabel.setStyle("-fx-text-fill: red;");
            return false;
        }
        int day;
        try {
            day = Integer.parseInt(dayInput);
        } catch (NumberFormatException e) {
            invalidBirthDateLabel.setText("Day of birth must be a number");
            invalidBirthDateLabel.setStyle("-fx-text-fill: red;");
            return false;
        }
        int year;
        try {
            year = Integer.parseInt(yearInput);
        } catch (NumberFormatException e) {
            invalidBirthDateLabel.setText("Year must be a number");
            invalidBirthDateLabel.setStyle("-fx-text-fill: red;");
            return false;
        }
        if (Integer.parseInt(monthInput) < 1 || Integer.parseInt(monthInput) > 12) {
            invalidBirthDateLabel.setText("Month must be a number between 1 and 12.");
            invalidBirthDateLabel.setStyle("-fx-text-fill: red;");
            return false;
        }
        if (Integer.parseInt(dayInput) < 1 && Integer.parseInt(monthInput) > 31) {
            invalidBirthDateLabel.setText("please finish your date of birth.");
            invalidBirthDateLabel.setStyle("-fx-text-fill: red;");
            return false;
        }
        if (Integer.parseInt(yearInput) < 1900 || Integer.parseInt(yearInput) > 2024) {
            invalidBirthDateLabel.setText("please fill in your real year of birth.");
            invalidBirthDateLabel.setStyle("-fx-text-fill: red;");
            return false;
        }
        if (Integer.parseInt(monthInput) == 2) {
            if (Integer.parseInt(yearInput) % 4 == 0 && Integer.parseInt(yearInput) % 100 != 0 || Integer.parseInt(yearInput) % 400 == 0) {
                if (Integer.parseInt(dayInput) > 29) {
                    invalidBirthDateLabel.setText("please finish your birth date.");
                    invalidBirthDateLabel.setStyle("-fx-text-fill: red;");
                    return false;
                }
            } else {
                if (Integer.parseInt(dayInput) > 28) {
                    invalidBirthDateLabel.setText("please finish your birth date.");
                    invalidBirthDateLabel.setStyle("-fx-text-fill: red;");
                    return false;
                }
            }
        } else if (Integer.parseInt(monthInput) == 4 || Integer.parseInt(monthInput) == 6 || Integer.parseInt(monthInput) == 9 || Integer.parseInt(monthInput) == 11) {
            if (Integer.parseInt(dayInput) > 30) {
                invalidBirthDateLabel.setText("please finish your birth date.");
                invalidBirthDateLabel.setStyle("-fx-text-fill: red;");
                return false;
            }
        }

        return checkBirth(year, month, day);
    }


    /**
     * when sign up being pressed, window close.
     */
    public void cancelSignupButtonClickedOnAction() {
        Stage stage = (Stage) cancelSignupButton.getScene().getWindow();
        stage.close();
    }

    /**
     * back to the login's interface.
     */
    public void backToLoginButtonOnAction() {
        Stage stage = (Stage) backToLoginButton.getScene().getWindow();
        SceneSwitcher.SwitchScene(stage, "Login.fxml");
    }

    /**
     * @param s is the username need to be checked if valid or not.
     * @return true if it's valid.
     */
    public boolean pass(String s) {
        for (char it : s.toCharArray()) {
            if (!Character.isLetterOrDigit(it)) {
                if (it != '@' && it != '_' && it != '-')
                    return false;
            }
        }
        return true;
    }

    /**
     * using css for the effect.
     */
    public void registerEnter() {
        registerButton.setStyle("-fx-underline: true; -fx-background-color: rgba(0, 0, 0, 0.2); -fx-text-fill: #4CAF50");
    }

    public void registerExited() {
        registerButton.setStyle("");
    }

    public void cancelEnter() {
        cancelSignupButton.setStyle("-fx-underline: true; -fx-background-color: rgba(0, 0, 0, 0.2); -fx-text-fill: red");
    }

    public void cancelExited() {
        cancelSignupButton.setStyle("");
    }

    public void backEnter() {
        backToLoginButton.setStyle("-fx-underline: true; -fx-background-color: rgba(0, 0, 0, 0.2); -fx-text-fill: yellow");
    }

    public void backExited() {
        backToLoginButton.setStyle("");
    }

}