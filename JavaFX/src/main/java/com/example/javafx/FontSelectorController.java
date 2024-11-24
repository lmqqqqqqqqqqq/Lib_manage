package com.example.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;

public class FontSelectorController {

    @FXML
    private ComboBox<String> fontComboBox;

    @FXML
    private ComboBox<Integer> fontSizeComboBox;

    @FXML
    private TextArea textArea;

    @FXML
    public void initialize() {
        // Load the system's available font families into the ComboBox
        fontComboBox.getItems().addAll(Font.getFamilies());
        fontComboBox.setValue("Arial"); // Set default font family in ComboBox

        // Set default font size options
        fontSizeComboBox.getItems().addAll(8, 10, 12, 14, 16, 18, 20, 24, 28, 32, 36, 40, 48, 56, 64, 72);
        fontSizeComboBox.setValue(14); // Set default font size in ComboBox

        // Set default font for the TextArea
        textArea.setFont(new Font("Arial", 14));
        textArea.setWrapText(true);

        fontComboBox.setOnAction(event -> {
            String selectedFont = fontComboBox.getValue();
            double currentFontSize = textArea.getFont().getSize();
            textArea.setFont(Font.font(selectedFont, currentFontSize));
        });

        // Update TextArea's font size when a new size is selected
        fontSizeComboBox.setOnAction(event -> {
            int selectedFontSize = fontSizeComboBox.getValue();
            String currentFontFamily = textArea.getFont().getFamily();
            textArea.setFont(Font.font(currentFontFamily, selectedFontSize));
        });
    }
}
