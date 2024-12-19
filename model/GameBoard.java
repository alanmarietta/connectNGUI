/**
 * GameBoard - this is where we handle the changing of each Location token. We change things based on user input here
 * displayBoard - this is where we print the current state of the board and display it to the user
 */
package edu.wm.cs.cs301.connectn.model;

public class GameBoard {
	private Location[][] board;			//do not change!
	private int winCondition; // # of pieces needed to win
	private int rows;
	private int columns;
	
	public GameBoard(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;
		
		// Create the initial 2D array based on # of rows and columns
		board = new Location[rows][columns];
				
        // Fill the board with new Location instances, initializing them with ' ' because each space is empty
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
            	board[i][j] = new Location(' ');
            }
		}
        // Set win condition based on the size of the board
        
        if (rows == 4 && columns == 5) { // Small board
            this.winCondition = 3;
            
        } else if (rows == 6 && columns == 7) { // Medium board
            this.winCondition = 4;
            
        } else if (rows == 8 && columns == 9) { // Large board
            this.winCondition = 5;
        }
	}
	
	// Determine if the column is full
	public boolean isColumnFull(int selectedColumn, int rows) {
    	int ColumnIndex = selectedColumn;
        for (int row = rows - 1; row >= 0; row--) {
            if (this.board[row][ColumnIndex].isEmpty()) {
                return true; // Found an empty spot, so the column is not full
            }
        }
        // Did not find any empty spots; the column is full
        return false;
    }
	
	// Method to place pieces on the board
	public boolean placePiece (int columnIndex, char piece) {
		for (int row = board.length - 1; row >= 0; row--) {
		        if (board[row][columnIndex].isEmpty()) {
		            board[row][columnIndex] = new Location(piece); // Place the piece here
		            return true; // Piece placed successfully	       
		        }
		    }
		return false;
	}

	// Method to remove pieces on the board (used for computerplayer algorithm)
	public boolean removePiece(int columnIndex) {
	    // Start from the top of the column
	    for (int row = 0; row < board.length; row++) {
	        // Check if the current location is not empty
	        if (!board[row][columnIndex].isEmpty()) {
	            // Found the topmost piece in the column, so remove it
	            board[row][columnIndex] = new Location(' ');
	            return true; // Piece removed successfully
	        }
	    }
	    return false;
	}
	
    // Method to check if the board is full
	public boolean isBoardFull() {
	    for (int row = 0; row < this.rows; row++) {
	        for (int col = 0; col < this.columns; col++) {
	            // If any location is empty, the board is not full
	            if (board[row][col].isEmpty()) {
	                return false;
	            }
	        }
	    }
	    // If no empty location was found, the board is full
	    return true;
	}
	
	// Method to check if there's a winner
    public boolean checkWin(char piece) {
        return checkHorizontalWin(piece, winCondition) || checkVerticalWin(piece, winCondition) || checkDiagonalWin(piece, winCondition);
    }
    
    public boolean checkHorizontalWin(char piece, int requiredConsecutive) {
        for (int row = 0; row < board.length; row++) {
            int consecutiveCount = 0;
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col].getToken() == piece) {
                    consecutiveCount++; // Increment count if the piece matches
                    if (consecutiveCount >= requiredConsecutive) {
                        return true;
                    }
                } else {
                    consecutiveCount = 0; // Reset count if the sequence is broken
                }
            }
        }
        return false;
    }

    public boolean checkVerticalWin(char piece, int requiredConsecutive) {
        for (int col = 0; col < columns; col++) { // Iterate through each column
            int consecutiveCount = 0;
            for (int row = 0; row < rows; row++) { // Iterate from the top of the column down
                if (board[row][col].getToken() == piece) {
                    consecutiveCount++; // Increment the count if the current piece matches the player's piece
                    if (consecutiveCount >= requiredConsecutive) {
                        return true;
                    }
                } else {
                    consecutiveCount = 0; // Reset count if the sequence is broken
                }
            }
        }
        return false;
    }

    public boolean checkDiagonalWin(char piece, int requiredConsecutive) {
        // Check for \ diagonals
        for (int row = 0; row <= rows - requiredConsecutive; row++) {
            for (int col = 0; col <= columns - requiredConsecutive; col++) {
                int count = 0;
                for (int i = 0; i < requiredConsecutive; i++) {
                    if (board[row + i][col + i].getToken() == piece) {
                        count++;
                        if (count == requiredConsecutive) return true;
                    } else {
                        break;
                    }
                }
            }
        }

        // Check for / diagonals
        for (int row = 0; row <= rows - requiredConsecutive; row++) {
            for (int col = requiredConsecutive - 1; col < columns; col++) {
                int count = 0;
                for (int i = 0; i < requiredConsecutive; i++) {
                    if (board[row + i][col - i].getToken() == piece) {
                        count++;
                        if (count == requiredConsecutive) return true;
                    } else {
                        break;
                    }
                }
            }
        }

        return false;
    }
    
    public char getTokenAt(int row, int column) {
        return board[row][column].getToken();
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }


}
