package com.groupid.boxpuzzle.app;

import com.groupid.boxpuzzle.game.BoxPuzzle;

/**
 * Main entry point for the Box Top Side Matching Puzzle App.
 * This class contains only the main method as per requirements.
 * All game logic is delegated to the BoxPuzzle class.
 */
public class BoxPuzzleApp {
    
    /**
     * Main method - application entry point.
     * Creates a new BoxPuzzle instance and starts the game.
     * This is the ONLY logic in main() as per requirements.
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Create a new BoxPuzzle instance and invoke startGame()
        BoxPuzzle game = new BoxPuzzle();
        game.startGame();
    }
}
