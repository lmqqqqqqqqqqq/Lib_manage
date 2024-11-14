package com.example.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class Wordle {
    @FXML private TextField inputField;
    @FXML private GridPane guessGrid;
    @FXML private Label messageLabel;
    @FXML private Button submitButton;

    private WordleGame game;

    public void initialize() {
        game = new WordleGame(); // For example, set the target word to "APPLE"
        messageLabel.setText("Enter your guess:");
        inputField.setOnAction(event -> handleGuess());
    }

    @FXML
    public void handleGuess() {
        String guess = inputField.getText().toUpperCase();

        // Check if the word length is correct
        if (guess.length() != game.getTargetWord().length()) {
            messageLabel.setText("Guess must be " + game.getTargetWord().length() + " letters.");
            return;
        }

        // Check if the word is valid
        if (game.isValidWord(guess)) {
            messageLabel.setText("Invalid word! Please enter a valid word.");
            return;
        }

        // Proceed with guessing if the word is valid
        String hint = game.guess(guess);
        updateGuessGrid(guess, hint);

        if (game.isCorrectGuess(guess)) {
            messageLabel.setText("Congratulations! You've guessed the word!");
            submitButton.setDisable(true);
        } else if (game.isGameOver()) {
            messageLabel.setText("Game Over! You've used all attempts.");
            submitButton.setDisable(true);
        } else {
            messageLabel.setText("Try again.");
        }

        inputField.clear();
    }

    private void updateGuessGrid(String guess, String hint) {
        int row = game.getAttempts() - 1; // Current attempt row
        for (int col = 0; col < guess.length(); col++) {
            Label letterLabel = new Label(String.valueOf(guess.charAt(col)));

            // Apply the appropriate style based on the hint character
            letterLabel.getStyleClass().add(getHintStyle(hint.charAt(col)));

            // Add the letter label to the guess grid at the correct position
            guessGrid.add(letterLabel, col, row);
        }
    }

    private String getHintStyle(char hintChar) {
        return switch (hintChar) {
            case 'G' -> "correct-position";
            case 'Y' -> "correct-letter";
            default -> "incorrect-letter";
        };
    }
}
