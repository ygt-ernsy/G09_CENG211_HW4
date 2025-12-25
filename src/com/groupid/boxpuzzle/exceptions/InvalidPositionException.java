package com.groupid.boxpuzzle.exceptions;

/**
 * Exception thrown for invalid position formats or out-of-bounds positions.
 * This is a RuntimeException since invalid positions typically indicate programming errors.
 */
public class InvalidPositionException extends RuntimeException {
    
    /**
     * Constructor with input string that caused the exception.
     * @param input The invalid input string
     */
    public InvalidPositionException(String input) {
        super("Invalid position format: " + input);
    }

    /**
     * Constructor with row and column values that are out of bounds.
     * @param row The invalid row value
     * @param col The invalid column value
     */
    public InvalidPositionException(int row, int col) {
        super("Position out of bounds: row=" + row + ", col=" + col);
    }
}
