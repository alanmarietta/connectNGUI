/**
 * Implements logic for AI turns. Checks for winning moves to ensure that the AI always makes the correct move if there is a winning opportunity.
 */
package edu.wm.cs.cs301.connectn.model;
import java.util.Random;

import edu.wm.cs.cs301.connectn.controller.Player;


public class ComputerPlayer implements Player {
	private int columns;
	private int rows;
	private GameBoard board;
	private char computerPiece;
	
	// Constructor
	public ComputerPlayer(int columns, int rows, GameBoard board, char computerPiece) {
		this.columns = columns;
		this.rows = rows;
		this.board = board;
		this.computerPiece = computerPiece;
	}

	@Override
	public String takeTurn() {
		if (!checkForNoWinningMove()) {
			return "Computer turn done. Good game!";
	    } else {
	        // No winning move found, so choose a random non-full column
	        Random rand = new Random();
	        int column;
	        do {
	            column = rand.nextInt(this.columns); // Random column index, 0-based
	        } while (!board.isColumnFull(column, this.rows)); // Repeat if the column is full
	        
	        // Once a non-full column is found, place the piece
	        board.placePiece(column, computerPiece);
	        return "Computer turn done - placed piece in column " + (column + 1);
	    }
	}
	
	public boolean checkForNoWinningMove() {
	    for (int col = 0; col < this.columns ; col++) {
	        if (board.isColumnFull(col, this.rows)) {
	            // Simulate placing the piece in the column
	            board.placePiece(col, this.computerPiece);
	            if (board.checkWin(this.computerPiece)) {
	                return false; // A winning move is found, so return false
	            } else {
	                // Undo the simulation move
	                board.removePiece(col); 
	            }
	        }
	    }
	    return true; // No winning move found, so it's "safe" to continue the game
	}

}
