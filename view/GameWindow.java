package edu.wm.cs.cs301.connectn.view;

import edu.wm.cs.cs301.connectn.model.GameBoard;
import edu.wm.cs.cs301.connectn.controller.ConnectNGame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameWindow extends JFrame {
    private GameBoard gameBoard;
    private GamePanel gamePanel;
    private ConnectNGame gameController;
    private JLabel turnLabel;
    private JPanel columnPanel;  // This will hold the column buttons

    public GameWindow(GameBoard gameBoard, ConnectNGame gameController) {
        this.gameBoard = gameBoard;
        this.gameController = gameController;
        this.gamePanel = new GamePanel(gameBoard);
        setupUI();
        setVisible(true);
    }

    private void setupUI() {
        setTitle("Connect N Game Board");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        addDifficultySelector();
        addGamePanel();
        addStatusAndControlPanel();
        pack();
        setLocationRelativeTo(null);
    }

    private void addDifficultySelector() {
        String[] difficulties = {"Small (4x5)", "Medium (6x7)", "Large (8x9)"};
        JComboBox<String> difficultySelector = new JComboBox<>(difficulties);
        difficultySelector.setSelectedIndex(determineInitialDifficultyIndex()); // Default to medium
        difficultySelector.addActionListener(e -> gameController.changeDifficulty(difficultySelector.getSelectedIndex()));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(difficultySelector);

        JButton exitButton = new JButton("EXIT");
        exitButton.addActionListener(e -> System.exit(0));
        topPanel.add(exitButton);

        add(topPanel, BorderLayout.NORTH);
    }

    private void addGamePanel() {
        add(gamePanel, BorderLayout.CENTER);
    }

    private void addStatusAndControlPanel() {
        JPanel southPanel = new JPanel(new BorderLayout());
        turnLabel = new JLabel("Turns: 0");
        turnLabel.setHorizontalAlignment(JLabel.CENTER);
        southPanel.add(turnLabel, BorderLayout.NORTH);

        columnPanel = new JPanel();
        updateColumnButtons(); // Initially create column buttons
        southPanel.add(columnPanel, BorderLayout.SOUTH);

        add(southPanel, BorderLayout.SOUTH);
    }

    public void updateColumnButtons() {
        columnPanel.removeAll(); // Remove all existing buttons
        columnPanel.setLayout(new GridLayout(1, gameBoard.getColumns()));
        for (int i = 0; i < gameBoard.getColumns(); i++) {
            int column = i;
            JButton columnButton = new JButton(String.valueOf(column + 1));
            columnButton.addActionListener(e -> {
                if (gameController.isHumanTurn() && gameBoard.placePiece(column, 'X')) {
                    gameController.handleHumanMove(column);
                    repaintGame();
                }
            });
            columnPanel.add(columnButton);
        }
        columnPanel.revalidate();
        columnPanel.repaint();
    }

    public void updateBoard(GameBoard board) {
        this.gameBoard = board;
        this.gamePanel.setGameBoard(board);
        updateColumnButtons();
        repackWindow();
        repaintGame();
    }

    public void repaintGame() {
        gamePanel.updateGameBoard(gameBoard);
        repaint();
    }

    public void updateTurnCount(int turnCount) {
        turnLabel.setText("Turns: " + turnCount);
    }
    
    public void repackWindow() {
        pack();
        setLocationRelativeTo(null);
    }
    
    private int determineInitialDifficultyIndex() {
        int columns = gameBoard.getColumns();
        if (columns == 5) return 0;  // Small
        else if (columns == 7) return 1;  // Medium
        else if (columns == 9) return 2;  // Large
        return 1; // Default to Medium if unsure
    }

}
