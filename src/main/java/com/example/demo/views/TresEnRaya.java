package com.example.demo.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.component.notification.Notification;

@Route("tresenraya")
@PageTitle("Tic-Tac-Toe")

public class TresEnRaya extends VerticalLayout {

    private static final int BOARD_SIZE = 3;
    private Button[][] board = new Button[BOARD_SIZE][BOARD_SIZE];
    private String currentPlayer = "X";
    private boolean gameActive = true;

    public TresEnRaya() {
        // App Title
        H1 title = new H1("Tic-Tac-Toe");
        title.getStyle().set("text-align", "center"); // Center the title

        // Board container
        Div boardContainer = new Div();
        boardContainer.addClassName("board-container"); //Use of CSS Class
        boardContainer.getStyle().set("display", "grid");
        boardContainer.getStyle().set("grid-template-columns", "repeat(" + BOARD_SIZE + ", 100px)"); // CSS grid
        boardContainer.getStyle().set("gap", "5px");

        // Initialize the board
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                board[row][col] = createBoardButton(row, col);
                boardContainer.add(board[row][col]);
            }
        }

        // Add everything to the main layout
        setAlignItems(Alignment.CENTER);
        add(title, boardContainer);

    }

    private Button createBoardButton(int row, int col) {
        Button button = new Button();
        button.setWidth("100px");
        button.setHeight("100px");
        button.addClickListener(event -> handleButtonClick(row, col)); //Attach click listener

        button.getStyle().set("font-size", "3em");
        button.getStyle().set("color", "var(--lumo-primary-text-color)"); // Use primary text color
        button.getStyle().set("background-color", "var(--lumo-contrast-5pct)"); // A light color
        button.getStyle().set("border", "none");
        button.getStyle().set("cursor", "pointer");

        return button;
    }

    private void handleButtonClick(int row, int col) {
        if (!gameActive || !board[row][col].getText().isEmpty()) {
            return; // Ignore clicks if the game is over or the cell is already taken
        }

        board[row][col].setText(currentPlayer);  // Sets the X or O on the board
        board[row][col].setEnabled(false);  //Disable button after is clicked

        if (checkWin(row, col)) {
            showWinDialog();
            gameActive = false;
            return;
        }

        if (isBoardFull()) {
            showDrawDialog();
            gameActive = false;
            return;
        }

        currentPlayer = (currentPlayer.equals("X")) ? "O" : "X"; // Swicht player

    }

    private boolean checkWin(int lastRow, int lastCol) {
        String symbol = board[lastRow][lastCol].getText();

        // Check row
        boolean rowWin = true;
        for (int col = 0; col < BOARD_SIZE; col++) {
            if (!board[lastRow][col].getText().equals(symbol)) {
                rowWin = false;
                break;
            }
        }
        if (rowWin) return true;

        // Check column
        boolean colWin = true;
        for (int row = 0; row < BOARD_SIZE; row++) {
            if (!board[row][lastCol].getText().equals(symbol)) {
                colWin = false;
                break;
            }
        }
        if (colWin) return true;

        // Check diagonal (top-left to bottom-right)
        if (lastRow == lastCol) {
            boolean diag1Win = true;
            for (int i = 0; i < BOARD_SIZE; i++) {
                if (!board[i][i].getText().equals(symbol)) {
                    diag1Win = false;
                    break;
                }
            }
            if (diag1Win) return true;
        }

        // Check diagonal (top-right to bottom-left)
        if (lastRow + lastCol == BOARD_SIZE - 1) {
            boolean diag2Win = true;
            for (int i = 0; i < BOARD_SIZE; i++) {
                if (!board[i][BOARD_SIZE - 1 - i].getText().equals(symbol)) {
                    diag2Win = false;
                    break;
                }
            }
            if (diag2Win) return true;
        }

        return false;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void showWinDialog() {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("¡We have a Winner!");
        dialog.add("Player " + currentPlayer + " wins!");

        Button closeButton = new Button("Play Again", event -> resetGame()); //Reset the game
        dialog.getFooter().add(closeButton);
        dialog.open();
    }

    private void showDrawDialog() {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Draw!");
        dialog.add("The game is a draw!");

        Button closeButton = new Button("Play Again", event -> resetGame()); //Reset the game
        dialog.getFooter().add(closeButton);
        dialog.open();
    }

    private void resetGame() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                board[row][col].setText("");
                board[row][col].setEnabled(true); //Re-enable buttons for next game
            }
        }
        currentPlayer = "X";
        gameActive = true;
    }
}