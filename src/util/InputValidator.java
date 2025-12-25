package util;

import model.Direction;
import model.Position;

/**
 * Utility class for validating user input for positions, directions, and menu choices.
 * Contains static methods for various validation operations.
 */
public class InputValidator {
    private static final int GRID_SIZE = 8;

    /**
     * Validates if the input string matches the position format.
     * Accepts "R#-C#" or "#-#" format (case-insensitive).
     * @param input The input string to validate
     * @return true if the format is valid
     */
    public static boolean isValidPositionFormat(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }
        
        String trimmed = input.trim().toUpperCase();
        
        // Check for "R#-C#" format
        if (trimmed.matches("R[1-8]-?C[1-8]")) {
            return true;
        }
        
        // Check for "#-#" format
        if (trimmed.matches("[1-8]-[1-8]")) {
            return true;
        }
        
        return false;
    }

    /**
     * Validates if the row number is within valid range (1-8).
     * @param row The row number (1-based)
     * @return true if valid
     */
    public static boolean isValidRowNumber(int row) {
        return row >= 1 && row <= GRID_SIZE;
    }

    /**
     * Validates if the column number is within valid range (1-8).
     * @param col The column number (1-based)
     * @return true if valid
     */
    public static boolean isValidColumnNumber(int col) {
        return col >= 1 && col <= GRID_SIZE;
    }

    /**
     * Checks if a position is on the grid edge.
     * @param pos The position to check
     * @return true if on the edge
     */
    public static boolean isEdgePosition(Position pos) {
        int row = pos.getRow();
        int col = pos.getColumn();
        
        // Edge positions are on row 0, row 7, col 0, or col 7
        return row == 0 || row == GRID_SIZE - 1 || col == 0 || col == GRID_SIZE - 1;
    }

    /**
     * Checks if a position is at a grid corner.
     * @param pos The position to check
     * @return true if at a corner
     */
    public static boolean isCornerPosition(Position pos) {
        int row = pos.getRow();
        int col = pos.getColumn();
        
        // Corner positions
        return (row == 0 || row == GRID_SIZE - 1) && (col == 0 || col == GRID_SIZE - 1);
    }

    /**
     * Returns valid roll directions for a given edge position.
     * @param pos The edge position
     * @return Array of valid directions
     */
    public static Direction[] getValidDirectionsForEdge(Position pos) {
        return Direction.fromEdgePosition(pos, GRID_SIZE);
    }

    /**
     * Validates if a string represents a valid row number.
     * @param input The input string
     * @return true if valid row number (1-8)
     */
    public static boolean isValidRowInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }
        try {
            int row = Integer.parseInt(input.trim());
            return isValidRowNumber(row);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validates if a string represents a valid column number.
     * @param input The input string
     * @return true if valid column number (1-8)
     */
    public static boolean isValidColumnInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }
        try {
            int col = Integer.parseInt(input.trim());
            return isValidColumnNumber(col);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Validates if input is a valid yes/no choice (1 or 2).
     * @param input The input string
     * @return true if "1" or "2"
     */
    public static boolean isValidYesNoChoice(String input) {
        if (input == null) return false;
        String trimmed = input.trim();
        return trimmed.equals("1") || trimmed.equals("2");
    }

    /**
     * Validates if input is a valid direction choice (1 or 2).
     * @param input The input string
     * @return true if "1" or "2"
     */
    public static boolean isValidDirectionChoice(String input) {
        return isValidYesNoChoice(input);
    }
}
