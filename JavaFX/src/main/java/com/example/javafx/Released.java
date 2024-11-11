package com.example.javafx;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Released {
    /**
     * @param s is the username need to be checked if valid or not.
     * @return true if it's valid.
     */
    public static boolean pass(String s) {
        for (char it : s.toCharArray()) {
            if (!Character.isLetterOrDigit(it)) {
                if (it != '@' && it != '_' && it != '-')
                    return false;
            }
        }
        return true;
    }

    public static void userNameReleased(TextField setUsernameTextField, Label invalidUsernameLabel, boolean[] checkUsername) {
        if (setUsernameTextField.getText().isEmpty()) {
            invalidUsernameLabel.setText("You need to enter a username");
            invalidUsernameLabel.setStyle("-fx-text-fill: red");
            setUsernameTextField.setStyle("-fx-border-color: red");
            checkUsername[0] = false;
        } else if (setUsernameTextField.getText().length() < 6 || setUsernameTextField.getText().length() > 15) {
            invalidUsernameLabel.setText("Username must be between 6 and 15 characters");
            invalidUsernameLabel.setStyle("-fx-text-fill: red");
            setUsernameTextField.setStyle("-fx-border-color: red");
            checkUsername[0] = false;
        } else if (!pass(setUsernameTextField.getText())) {
            invalidUsernameLabel.setText("Username can not contains special characters except '@, '-', '_'.");
            invalidUsernameLabel.setStyle("-fx-text-fill: red");
            setUsernameTextField.setStyle("-fx-border-color: red");
            checkUsername[0] = false;
        } else {
            invalidUsernameLabel.setText("Valid username");
            invalidUsernameLabel.setStyle("-fx-text-fill: #4CAF50");
            setUsernameTextField.setStyle("-fx-border-color: #4CAF50");
            checkUsername[0] = true;
        }
    }

    /**
     * check the password and using released to check throughout the process.
     */
    public static void passwordReleased(PasswordField passwordField, Label label, boolean[] checkPassword) {
        String password = passwordField.getText();
        if (password.isEmpty()) {
            label.setText("You need to enter a password");
            label.setStyle("-fx-text-fill: red");
            passwordField.setStyle("-fx-border-color: red");
            checkPassword[0] = false;
        } else if (password.length() < 6 || password.length() > 50) {
            label.setText("Password should be between 6 and 50 characters");
            label.setStyle("-fx-text-fill: red");
            passwordField.setStyle("-fx-border-color: red");
            checkPassword[0] = false;
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
                label.setText("Strong Password");
                label.setStyle("-fx-text-fill: #4CAF50");
                passwordField.setStyle("-fx-border-color: #4CAF50;");
                checkPassword[0] = true;
            }
            else if (checkA && (checkD || checkS) && password.length() > 7) {
                label.setText("Medium Password");
                label.setStyle("-fx-text-fill: #c3c30b");
                passwordField.setStyle("-fx-border-color: #c3c30b;");
                checkPassword[0] = true;
            }
            else {
                label.setText("Week Password");
                label.setStyle("-fx-text-fill: red");
                passwordField.setStyle("-fx-border-color: red;");
                checkPassword[0] = false;
            }
        }
    }

    public static void confirmReleased(PasswordField confirmPasswordField, PasswordField setPasswordField,
                                Label invalidPasswordLabel, Label invalidConfirmPasswordLabel, boolean[] checkConfirm) {
        if (!(invalidPasswordLabel.getText().equals("Medium Password") || invalidPasswordLabel.getText().equals("Strong Password"))) {
            invalidConfirmPasswordLabel.setText("Please enter a valid password");
            invalidConfirmPasswordLabel.setStyle("-fx-text-fill: red");
            confirmPasswordField.setStyle("-fx-border-color: red");
            checkConfirm[0] = false;
        } else if (confirmPasswordField.getText().isEmpty()) {
            invalidConfirmPasswordLabel.setText("You need to enter a confirm password");
            invalidConfirmPasswordLabel.setStyle("-fx-text-fill: red");
            confirmPasswordField.setStyle("-fx-border-color: red");
            checkConfirm[0] = false;
        } else if (!confirmPasswordField.getText().equals(setPasswordField.getText())) {
            invalidConfirmPasswordLabel.setText("Password does not match");
            invalidConfirmPasswordLabel.setStyle("-fx-text-fill: red");
            confirmPasswordField.setStyle("-fx-border-color: red");
            checkConfirm[0] = false;
        } else {
            invalidConfirmPasswordLabel.setText("Valid Password");
            invalidConfirmPasswordLabel.setStyle("-fx-text-fill: #4CAF50");
            confirmPasswordField.setStyle("-fx-border-color: #4CAF50");
            checkConfirm[0] = true;
        }
    }

    /**
     * check the firstname and using released to check throughout the process.
     */
    public static void firstnameReleased(TextField firstnameTextField, Label invalidFirstNameLabel, boolean[] checkFirstname) {
        if (firstnameTextField.getText().isEmpty()) {
            invalidFirstNameLabel.setText("Please enter your first name");
            invalidFirstNameLabel.setStyle("-fx-text-fill: red");
            firstnameTextField.setStyle("-fx-border-color: red");
            checkFirstname[0] = false;
        } else {
            invalidFirstNameLabel.setText("Valid");
            invalidFirstNameLabel.setStyle("-fx-text-fill: #4CAF50");
            firstnameTextField.setStyle("-fx-border-color: #4CAF50");
            checkFirstname[0] = true;
        }
    }

    /**
     * check the code and using released to check throughout the process.
     */
    public static void recoveryReleased(TextField codeTextfield, Label invalidRecover, boolean[] checkCode) {
        if (codeTextfield.getText().isEmpty()) {
            invalidRecover.setText("Please fill in your recover code");
            invalidRecover.setStyle("-fx-text-fill: red");
            codeTextfield.setStyle("-fx-border-color: red");
            checkCode[0] = false;
        } else {
            invalidRecover.setText("Valid Code");
            invalidRecover.setStyle("-fx-text-fill: #4CAF50");
            codeTextfield.setStyle("-fx-border-color: #4CAF50");
            checkCode[0] = true;
        }
    }





}
