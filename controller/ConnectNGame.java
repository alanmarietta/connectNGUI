package edu.wm.cs.cs301.connectn.controller;

import edu.wm.cs.cs301.connectn.model.ComputerPlayer;
import edu.wm.cs.cs301.connectn.model.GameBoard;
import edu.wm.cs.cs301.connectn.view.GameWindow;
import edu.wm.cs.cs301.connectn.model.LeaderboardManager;

import javax.swing.*;
import java.awt.GridLayout;


public class ConnectNGame {
    private int rows;
    private int columns;
    private GameBoard board;
    private ComputerPlayer computer;
    private GameWindow gameWindow;
    private boolean humanTurn = true;
    private int turnCount = 0;
    private static LeaderboardManager leaderboardManager = new LeaderboardManager();
    private String gameMode;
    
    // Constructor
    public ConnectNGame(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.board = new GameBoard(rows, columns);
        this.computer = new ComputerPlayer(columns, rows, board, 'O');
        this.gameWindow = new GameWindow(board, this);
        this.gameWindow.setVisible(true);
        leaderboardManager.displayLeaderboard();
        determineGameMode();
    }

    private void determineGameMode() {
        if (rows == 4 && columns == 5) {
            gameMode = "small";
        } else if (rows == 6 && columns == 7) {
            gameMode = "medium";
        } else if (rows == 8 && columns == 9) {
            gameMode = "large";
        } else {
            gameMode = "unknown";
        }
    }
    
    // Start the game by displaying the game window
    public void start() {
        gameWindow.setVisible(true);
    }

    // Human move handling
    public void handleHumanMove(int column) {
    	turnCount++;
    	gameWindow.updateTurnCount(turnCount);
        if (board.checkWin('X')) {
        	gameWindow.updateBoard(board);
        	leaderboardManager.updateScore(gameMode, turnCount);
            endGame("Human wins!");
        } else if (board.isBoardFull()) {
        	gameWindow.updateBoard(board);
            endGame("Tie game.");
        } else {
            humanTurn = false; // Switch to computer's turn
            computerMove();
        }
    }

    // Computer move handling
    private void computerMove() {
        String result = computer.takeTurn(); // Execute computer's turn
        gameWindow.repaintGame(); // Refresh UI after computer's move
        if (board.checkWin('O')) {
        	gameWindow.updateBoard(board);
            endGame("Computer wins!");
        } else if (board.isBoardFull()) {
        	gameWindow.updateBoard(board);
            endGame("Tie game.");
        } else {
            humanTurn = true; // Switch back to human turn
        }
    }

    private void endGame(String message) {
    
        // Create a panel to display the end game message
        JPanel panel = new JPanel(new GridLayout(0, 1));
        JLabel label = new JLabel(message, JLabel.CENTER);
        panel.add(label);

        // Options for the buttons
        String[] options = {"Play Again", "Exit"};

        int response = JOptionPane.showOptionDialog(
            gameWindow,
            panel,
            "Game Over",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            options[0]
        );

        if (response == JOptionPane.YES_OPTION) {
        	leaderboardManager.saveScores();
        	leaderboardManager.displayLeaderboard();
            restartGame(); // Restart the game if "Play Again" is chosen
        } else {
        	leaderboardManager.saveScores();
        	leaderboardManager.displayLeaderboard();
            System.exit(0); // Exit the game if "Exit" is chosen
        }
    }
    
    private void restartGame() {
        // Reset the game board to its initial state
        board = new GameBoard(rows, columns);
        computer = new ComputerPlayer(columns, rows, board, 'O');
        turnCount = 0;
        humanTurn = true;

        // Update the GameWindow UI
        gameWindow.updateBoard(board);
        gameWindow.updateTurnCount(turnCount);
    }
    
    public boolean isHumanTurn() {
        return humanTurn;
    }
    
    public void changeDifficulty(int index) {
        switch (index) {
            case 0: rows = 4; columns = 5; gameMode = "small"; break;
            case 1: rows = 6; columns = 7; gameMode = "medium"; break;
            case 2: rows = 8; columns = 9; gameMode = "large"; break;
        }
        board = new GameBoard(rows, columns); // Reset the game board
        computer = new ComputerPlayer(columns, rows, board, 'O'); // Reset the AI
        gameWindow.updateBoard(board);
        resetTurnCount();
    }

    public void resetTurnCount() {
        int turnCount = 0;
        gameWindow.updateTurnCount(turnCount);
    }

}
