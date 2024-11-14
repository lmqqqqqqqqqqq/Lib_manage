package com.example.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class yourBookController {
    @FXML
    private Label abc;

    public void initialize() {
        abc.setText("abc");
    }
}
