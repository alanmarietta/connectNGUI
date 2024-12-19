package edu.wm.cs.cs301.connectn.view;

import edu.wm.cs.cs301.connectn.model.GameBoard;
import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private GameBoard gameBoard;

    public GamePanel(GameBoard board) {
        this.gameBoard = board;
        setPreferredSize(new Dimension(300, 300)); // Set a preferred size for the panel
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Get the dimensions for each cell based on the size of the panel and the number of rows/columns
        int cellWidth = getWidth() / gameBoard.getColumns();
        int cellHeight = getHeight() / gameBoard.getRows();

        // Draw the game board based on the current state
        for (int i = 0; i < gameBoard.getRows(); i++) {
            for (int j = 0; j < gameBoard.getColumns(); j++) {
                int x = j * cellWidth;
                int y = i * cellHeight;
                char token = gameBoard.getTokenAt(i, j);  // Dynamically get the token for each location
                if (token == 'X') {
                    g.setColor(Color.RED);
                    drawX(g, x, y, cellWidth, cellHeight);
                } else if (token == 'O') {
                    g.setColor(Color.BLUE);
                    g.fillOval(x + 2, y + 2, cellWidth - 4, cellHeight - 4);
                }
                g.setColor(Color.BLACK);
                g.drawRect(x, y, cellWidth, cellHeight);  // Draw the cell borders
            }
        }
    }

    private void drawX(Graphics g, int x, int y, int width, int height) {
        int padding = 10; // Padding from cell borders
        g.drawLine(x + padding, y + padding, x + width - padding, y + height - padding);
        g.drawLine(x + width - padding, y + padding, x + padding, y + height - padding);
    }

    public void updateGameBoard(GameBoard board) {
        this.gameBoard = board;
        repaint();  // Repaint to show updated board
    }
    
    public void setGameBoard(GameBoard board) {
        this.gameBoard = board;
        this.repaint();  // Ensure the panel is repainted to show the updated board
    }
}