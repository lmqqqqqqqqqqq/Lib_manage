package com.example.javafx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordleGame {
    private final String targetWord;
    private final int maxAttempts = 6;
    private int attempts;
    private List<String> guesses;
    private static final List<String> wordList = new ArrayList<>();

    public WordleGame() {
        loadWords(); // Load words from files if not already loaded
        this.targetWord = getRandomWord();
        this.guesses = new ArrayList<>();
        this.attempts = 0;
    }

    private static void loadWords() {
        if (!wordList.isEmpty()) return; // Prevent reloading if already loaded

        String[] files = {
                "wordle_historic_words.txt",
                "shuffled_real_wordles.txt",
                "official_allowed_guesses.txt",
                "combined_wordlist.txt"
        };

        for (String fileName : files) {
            try {
                URL resourceUrl = WordleGame.class.getResource(fileName);
                if (resourceUrl == null) {
                    System.out.println("File not found: " + fileName);
                    continue;
                }

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(resourceUrl.openStream()))) {
                    String word;
                    while ((word = reader.readLine()) != null) {
                        wordList.add(word.trim().toUpperCase());
                    }
                }
            } catch (IOException e) {
                System.out.println("Error loading words from file: " + fileName);
                e.printStackTrace();
            }
        }
    }

    // New method to check if a word is valid
    public boolean isValidWord(String word) {
        return !wordList.contains(word.toUpperCase());
    }

    private String getRandomWord() {
        Random random = new Random();
        return wordList.get(random.nextInt(wordList.size()));
    }

    public int getAttempts() {
        return attempts;
    }

    public String getTargetWord() {
        return targetWord;
    }

    public String guess(String word) {
        if (attempts >= maxAttempts) {
            return "Game over!";
        }

        word = word.toUpperCase();

        // Check if the word is valid
        if (isValidWord(word)) {
            return "Invalid word! Please try again with a valid word.";
        }

        guesses.add(word);
        attempts++;
        return generateHint(word);
    }

    private String generateHint(String guess) {
        StringBuilder hint = new StringBuilder();
        boolean[] matched = new boolean[targetWord.length()];

        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) == targetWord.charAt(i)) {
                hint.append("G");
                matched[i] = true;
            } else {
                hint.append(" ");
            }
        }

        for (int i = 0; i < guess.length(); i++) {
            if (hint.charAt(i) == 'G') continue;

            boolean found = false;
            for (int j = 0; j < targetWord.length(); j++) {
                if (!matched[j] && guess.charAt(i) == targetWord.charAt(j)) {
                    hint.setCharAt(i, 'Y');
                    matched[j] = true;
                    found = true;
                    break;
                }
            }
            if (!found) {
                hint.setCharAt(i, 'B');
            }
        }

        return hint.toString();
    }

    public boolean isGameOver() {
        return attempts >= maxAttempts || guesses.contains(targetWord);
    }

    public boolean isCorrectGuess(String guess) {
        return guess.equalsIgnoreCase(targetWord);
    }
}
