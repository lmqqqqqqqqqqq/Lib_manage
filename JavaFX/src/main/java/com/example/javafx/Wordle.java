package com.example.javafx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Wordle {
    public Label headerLabel;
    @FXML private AnchorPane playScreen;
    @FXML private AnchorPane gameScreen;
    public Button homeButton;
    @FXML private TextField inputField;
    @FXML private GridPane guessGrid;
    @FXML private Label messageLabel;
    @FXML private Button submitButton;
    public Button restartButton;
    private WordleGame game;
    private int streakScore = 0;
    @FXML private Label streakScoreLabel;
    private int highScore = 0;
    @FXML private Label highScoreLabel;

    public void initialize() {
        startNewGame();
    }

    private void startNewGame() {
        game = new WordleGame();
        guessGrid.getChildren().clear();
        messageLabel.setText("Enter your guess:");
        inputField.clear();
        submitButton.setDisable(false);
    }

    private void updateStreakHighScore() {
        if (streakScore > highScore) {
            highScore = streakScore;
        }
    }

    private void updateHighScoreLabel() {
        highScoreLabel.setText("High Streak: " + highScore);
    }

    @FXML
    public void handleGuess() {
        if (game.isGameOver()) {
            messageLabel.setText("Game Over! You've used all attempts. The word was " + game.getTargetWord());
            return;
        }

        String guess = inputField.getText().toUpperCase();

        if (guess.length() != game.getTargetWord().length()) {
            messageLabel.setText("Guess must be " + game.getTargetWord().length() + " letters.");
            return;
        }

        if (game.isValidWord(guess)) {
            messageLabel.setText("Invalid word! Please enter a valid word.");
            return;
        }

        String hint = game.guess(guess);
        updateGuessGrid(guess, hint);

        if (game.isCorrectGuess(guess)) {
            streakScore++;
            updateStreakScoreLabel();
            updateStreakHighScore();
            messageLabel.setText("Congratulations! You've guessed the word!");
            submitButton.setDisable(true);
        } else if (game.isGameOver()) {
            streakScore = 0;
            updateStreakScoreLabel();
            updateStreakHighScore();
            messageLabel.setText("Game Over! You've used all attempts. The word was " + game.getTargetWord());
            submitButton.setDisable(true);
        } else {
            messageLabel.setText("Try again.");
        }

        inputField.clear();
    }

    private void updateStreakScoreLabel() {
        streakScoreLabel.setText(STR."Streak: \{streakScore}");
    }

    @FXML
    public void restartGame() {
        startNewGame();
        messageLabel.setText("Game restarted. Streak: " + streakScore);
        updateStreakScoreLabel();
    }

    private void updateGuessGrid(String guess, String hint) {
        int row = game.getAttempts() - 1;
        for (int col = 0; col < guess.length(); col++) {
            Label letterLabel = new Label(String.valueOf(guess.charAt(col)));

            letterLabel.getStyleClass().add(getHintStyle(hint.charAt(col)));

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

    @FXML
    public void submitEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handleGuess();
        }
    }

    @FXML
    public void startGame() {
        playScreen.setVisible(false);
        playScreen.setManaged(false);

        gameScreen.setVisible(true);
        gameScreen.setManaged(true);
    }

    @FXML
    public void goHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainScene.fxml"));
            Scene mainScene = new Scene(loader.load());
            Stage stage = (Stage) gameScreen.getScene().getWindow();
            stage.setScene(mainScene);
        } catch (Exception e) {
            e.printStackTrace();
            messageLabel.setText("Error loading home screen.");
        }
    }
}
