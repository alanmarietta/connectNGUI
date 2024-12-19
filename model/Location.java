/**
 * - Location.java
 * 		- Represents a single space on the board
 * 		- Keep track of a player's token if present
 */
package edu.wm.cs.cs301.connectn.model;

public class Location {
    private Character symbol;

    // Constructor
    public Location(Character initial_symbol) {
        this.symbol = initial_symbol; // Initialize as whatever symbol we pass to the constructor
    }

    // isEmpty() - Returns true if the space is empty
    public boolean isEmpty() {
    	if (this.symbol == ' ') {
    		return true;
    	} else {
    		return false;
    	}
    }

    // getToken() - returns the symbol of the player token present in the location
    public Character getToken() {
        return this.symbol;
    }

    // equals(Location) - checks to see if a location holds the same token as another location
    @Override
    public boolean equals(Object other) {
        // Return false if either is empty
        if ((this.isEmpty() == true) || other.isEmpty() == true) {
        	return false;
        // Return true if they are the same
        } else if (this.getToken() == other.getToken()) {
        	return true;
        }
	}
}
