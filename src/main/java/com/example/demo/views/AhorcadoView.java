package com.example.demo.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.component.notification.Notification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Route("ahorcado")
@PageTitle("Hangman")
public class AhorcadoView extends VerticalLayout {

    private final String[] words = {"vaadin", "spring", "java", "hangman", "developer"};
    private String secretWord;
    private char[] guessedLetters;
    private int incorrectGuesses = 0;
    private final int MAX_INCORRECT_GUESSES = 6; // Define the maximum number of incorrect guesses

    private Label wordDisplayLabel;
    private TextField letterInput;
    private Button guessButton;
    private Label incorrectGuessesLabel;

    public AhorcadoView() {
        startGame();

        // UI components
        H1 title = new H1("Ahorcado");
        wordDisplayLabel = new Label(getWordDisplay());
        letterInput = new TextField();
        letterInput.setPlaceholder("Enter a letter");
        letterInput.setWidth("100px");

        guessButton = new Button("Guess");
        guessButton.addClickListener(event -> handleGuess());

        incorrectGuessesLabel = new Label("Incorrect guesses: 0 / " + MAX_INCORRECT_GUESSES);  // Display max incorrect guesses

        //Layout

        HorizontalLayout inputLayout = new HorizontalLayout(letterInput, guessButton);
        inputLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);

        Div hangmanDisplay = createHangmanDisplay(); //Visual Hangman (Basic)

        setAlignItems(Alignment.CENTER);
        add(title, wordDisplayLabel, inputLayout, incorrectGuessesLabel, hangmanDisplay);
    }

    private void startGame() {
        Random random = new Random();
        secretWord = words[random.nextInt(words.length)];
        guessedLetters = new char[secretWord.length()];
        Arrays.fill(guessedLetters, '_');
        incorrectGuesses = 0;
    }

    private String getWordDisplay() {
        StringBuilder display = new StringBuilder();
        for (char letter : guessedLetters) {
            display.append(letter).append(" ");
        }
        return display.toString();
    }

    private void handleGuess() {
        String guess = letterInput.getValue().toLowerCase();

        if (guess.length() != 1 || !Character.isLetter(guess.charAt(0))) {
            Notification.show("Please enter a single letter.");
            return;
        }

        char letter = guess.charAt(0);

        if (secretWord.contains(String.valueOf(letter))) {
            updateGuessedLetters(letter);
            wordDisplayLabel.setText(getWordDisplay());
            if (checkWin()) {
                showWinDialog();
            }
        } else {
            incorrectGuesses++;
            incorrectGuessesLabel.setText("Incorrect guesses: " + incorrectGuesses + " / " + MAX_INCORRECT_GUESSES);
            updateHangmanDisplay();
            if (incorrectGuesses >= MAX_INCORRECT_GUESSES) {
                showLoseDialog();
            }
        }

        letterInput.clear();
    }

    private void updateGuessedLetters(char letter) {
        for (int i = 0; i < secretWord.length(); i++) {
            if (secretWord.charAt(i) == letter) {
                guessedLetters[i] = letter;
            }
        }
    }

    private boolean checkWin() {
        return secretWord.equals(String.valueOf(guessedLetters));
    }

    private Div createHangmanDisplay() {
        Div hangman = new Div();
        hangman.setText("-----\n|   |\n    |\n    |\n    |\n=====");
        hangman.getStyle().set("font-family", "monospace");
        hangman.getStyle().set("white-space", "pre"); // Preserve line breaks
        return hangman;
    }

     private void updateHangmanDisplay() {
        Div hangmanDisplay = (Div)getComponentAt(4); // Hack, need to access in a better way
        switch (incorrectGuesses) {
            case 1:
                hangmanDisplay.setText("-----\n|   |\nO   |\n    |\n    |\n=====");
                break;
            case 2:
                hangmanDisplay.setText("-----\n|   |\nO   |\n|   |\n    |\n=====");
                break;
            case 3:
                hangmanDisplay.setText("-----\n|   |\nO   |\n/|   |\n    |\n=====");
                break;
            case 4:
                hangmanDisplay.setText("-----\n|   |\nO   |\n/|\\  |\n    |\n=====");
                break;
            case 5:
                hangmanDisplay.setText("-----\n|   |\nO   |\n/|\\  |\n/   |\n=====");
                break;
            case 6:
                hangmanDisplay.setText("-----\n|   |\nO   |\n/|\\  |\n/ \\  |\n=====");
                break;
        }
    }

    private void showWinDialog() {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Congratulations!");
        dialog.add("You guessed the word: " + secretWord);

        Button playAgainButton = new Button("Play Again", event -> resetGame());
        dialog.getFooter().add(playAgainButton);
        dialog.open();
    }

    private void showLoseDialog() {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("You Lost!");
        dialog.add("The word was: " + secretWord);

        Button playAgainButton = new Button("Play Again", event -> resetGame());
        dialog.getFooter().add(playAgainButton);
        dialog.open();
    }

    private void resetGame() {
        startGame();
        wordDisplayLabel.setText(getWordDisplay());
        incorrectGuessesLabel.setText("Incorrect guesses: 0 / " + MAX_INCORRECT_GUESSES);  // Reset max guesses
        updateHangmanDisplay();
        letterInput.clear();

        Div hangmanDisplay = (Div)getComponentAt(4); // Hack, need to access in a better way
        hangmanDisplay.setText("-----\n|   |\n    |\n    |\n    |\n=====");
    }
}