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

    private String avatarLink;

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
        if (validateFields()) {
            invalidBirthDateLabel.setText("Valid Birth Date");
            invalidBirthDateLabel.setStyle("-fx-text-fill: #4CAF50");
        }

        if (isValidUsername && isValidPassword && isValidConfirm && isValidFirstname && isValidCode && validateFields()) {
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

        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(1.5));

        FadeTransition fadeOutTransition = new FadeTransition();
        fadeOutTransition.setNode(label);  // Gán Label vào hiệu ứng
        fadeOutTransition.setDuration(Duration.seconds(2));  // Thời gian fade in
        fadeOutTransition.setFromValue(1);  // Từ độ trong suốt là 1 ( nhìn thấy)
        fadeOutTransition.setToValue(0);    // Đến độ trong suốt là 0 (khong nhìn thấy)

        SequentialTransition sequentialTransition = new SequentialTransition(fadeInTransition, pauseTransition, fadeOutTransition);
        if (label == successfulLabel) {
            sequentialTransition.setOnFinished(_ -> {
                Stage stage = (Stage) successfulLabel.getScene().getWindow();
                SceneSwitcher.SwitchScene(stage, "Login.fxml");
            });
        }
        sequentialTransition.play();
    }

    /**
     * export data into database.
     */
    public void registerUser() {
        String currentDate = currentDate();
        String usernameInput = setUsernameTextField.getText();
        String passwordInput = setPasswordField.getText();
        String firstNameInput = firstnameTextField.getText();
        String lastNameInput = lastnameTextField.getText();
        String recoveryCode = codeTextfield.getText();
        String day = dayCombo.getValue();
        String month = monthCombo.getValue();
        String year = yearCombo.getValue();


        String query = "insert into users (first_name, last_name, username, password, dayOfBirth, monthOfBirth, yearOfBirth, recoveryCode, avatar, currentDate) value (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
            preparedStatement.setString(9, avatarLink);
            preparedStatement.setString(10, currentDate);

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
     * using a list to wrap the boolean to take the value of checkUsername from Released.
     */
    public boolean[] checkUsername = {false};
    public boolean isValidUsername;

    public void userNameReleased() {
        Released.userNameReleased(setUsernameTextField, invalidUsernameLabel, checkUsername);
        isValidUsername = checkUsername[0];
        if (UsernameExisted()) {
            invalidUsernameLabel.setText("Username already exists");
            invalidUsernameLabel.setStyle("-fx-text-fill: red");
            setUsernameTextField.setStyle("-fx-border-color: red");
            isValidUsername = false;
        }
    }

    /**
     * using a list to wrap the boolean to take the value of checkPassword from Released.
     */
    public boolean[] checkPassword = {false};
    public boolean isValidPassword;

    public void passwordReleased() {
        Released.passwordReleased(setPasswordField, invalidPasswordLabel, checkPassword);
        isValidPassword = checkPassword[0];
    }

    /**
     * using a list to wrap the boolean to take the value of checkConfirm from Released.
     */
    public boolean[] checkConfirm = {false};
    public boolean isValidConfirm;

    public void confirmReleased() {
        Released.confirmReleased(confirmPasswordField, setPasswordField, invalidPasswordLabel, invalidConfirmPasswordLabel, checkConfirm);
        isValidConfirm = checkConfirm[0];
    }

    /**
     * using a list to wrap the boolean to take the value of checkFirstname from Released.
     */
    public boolean[] checkFirstname = {false};
    public boolean isValidFirstname;

    public void firstnameReleased() {
        Released.firstnameReleased(firstnameTextField, invalidFirstNameLabel, checkFirstname);
        isValidFirstname = checkFirstname[0];
    }

    /**
     * using a list to wrap the boolean to take the value of checkCode from Released.
     */
    public boolean[] checkCode = {false};
    public boolean isValidCode;

    public void recoveryReleased() {
        Released.recoveryReleased(codeTextfield, invalidRecover, checkCode);
        isValidCode = checkCode[0];
    }

    /**
     * initialize
     */
    public void initialize() {
        avatarLink = "/com/example/image/user.jpg";
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

        if(checkBirth(year, month, day)) {
            return true;
        } else {
            invalidBirthDateLabel.setText("birth date is not valid");
            invalidBirthDateLabel.setStyle("-fx-text-fill: red;");
            return false;
        }
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
     * dayIn.
     * @return dayIn.
     */
    public String currentDate() {
        LocalDate date = LocalDate.now();
        return date.toString();
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