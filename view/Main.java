/* Main.java:
 * - Creates an instance of the game
 * - Controls "play-again" loop
 * - Creates a new instance of the game each time the user creates a new game */

// Display best score with names and scores (# of turns) for each variation of the game

package edu.wm.cs.cs301.connectn.view;
import edu.wm.cs.cs301.connectn.controller.ConnectNGame;
import edu.wm.cs.cs301.connectn.model.GameBoard;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class Main {
	
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Welcome to Connect N Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);

        JLabel welcomeLabel = new JLabel("<html><div style='text-align: center; width: 100%; padding: 20px;'>" +
                "<h1>Welcome to Connect N!</h1>" +
                "Connect N is a variation of the classic tabletop game, Connect 4.<br/>" +
                "<h1>Instructions</h1>" +
                "This game is played by dropping pieces into columns of your choice.<br/>" +
                "To win, you have to get N (3 for small, 4 for medium, and 5 for large) of your pieces in a diagonal, horizontal, or vertical line.</div></html>");

        JComboBox<String> sizeSelector = new JComboBox<>(new String[]{"Small (4x5)", "Medium (6x7)", "Large (8x9)"});
        sizeSelector.setSelectedIndex(1); // Set 'Medium' as the default selection
        sizeSelector.setMaximumSize(sizeSelector.getPreferredSize()); // Keep the dropdown small

        JButton okButton = new JButton("Start Game");
        okButton.addActionListener(e -> {
            int rows = 6, columns = 7; // Defaults to medium
            if ("Small (4x5)".equals(sizeSelector.getSelectedItem())) {
                rows = 4;
                columns = 5;
            } else if ("Large (8x9)".equals(sizeSelector.getSelectedItem())) {
                rows = 8;
                columns = 9;
            }
            
            ConnectNGame game = new ConnectNGame(rows, columns);
            game.start(); // Start the game
           
            frame.dispose();  // Close the welcome window
        });

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0; // Column 0
        gbc.gridy = 0; // Row 0
        gbc.anchor = GridBagConstraints.NORTHWEST; // Anchor to the northwest corner (top-left)
        mainPanel.add(sizeSelector, gbc);

        gbc.gridx = 0; // Column 0
        gbc.gridy = 1; // Row 1
        gbc.fill = GridBagConstraints.HORIZONTAL; // Fill horizontally
        gbc.weightx = 1.0; // Give extra horizontal space
        gbc.anchor = GridBagConstraints.CENTER; // Center horizontally in the cell
        mainPanel.add(welcomeLabel, gbc);

        gbc.gridx = 0; // Column 0
        gbc.gridy = 2; // Row 2
        gbc.anchor = GridBagConstraints.CENTER; // Center the button
        mainPanel.add(okButton, gbc);

        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);
    }
    
	public static void main(String[] args) {
		// Display welcome message with instructions, user chooses what size board they want to play with
		 javax.swing.SwingUtilities.invokeLater(Main::createAndShowGUI);
			
		}
	}