package com.example.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class HomeController {
    @FXML
    private Label welcomeText;
    @FXML
    private AnchorPane suggestPane;

    User user = LoginController.user;


    public void initialize() {
        welcomeText.setText("Welcome user " + user.getLastname() + " " + user.getFirstname() + "! It's been " + numberOfDay() + " since the first time!" );
    }

    public String numberOfDay() {
        LocalDate date = LocalDate.now();
        LocalDate signUpDate = LocalDate.parse(user.getDayIn());
        long days = ChronoUnit.DAYS.between(signUpDate, date);
        return days + " days";
    }

    public void searchOnAction() throws IOException {
        try {
            SceneSwitcher.switchBetweenPage(suggestPane, "findingBOOK.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
