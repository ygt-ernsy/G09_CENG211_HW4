package util;

import game.BoxGrid;
import model.BoxSurfaces;
import model.box.Box;

/**
 * Utility class for formatting the grid display for console output.
 * Provides methods to format the entire grid, individual boxes, and cube diagrams.
 */
public class GridDisplayFormatter {
    private static final int GRID_SIZE = 8;
    private static final String HORIZONTAL_LINE = " -----------------------------------------------------------------";
    private static final String CELL_FORMAT = " %s |";

    /**
     * Returns a formatted string representation of the entire grid.
     * Includes column headers (C1-C8) and row labels (R1-R8).
     * @param grid The BoxGrid to format
     * @return The formatted grid string
     */
    public static String formatGrid(BoxGrid grid) {
        StringBuilder sb = new StringBuilder();
        
        // Column headers
        sb.append("       C1      C2      C3      C4      C5      C6      C7      C8\n");
        sb.append(HORIZONTAL_LINE).append("\n");
        
        // Grid rows
        for (int row = 0; row < GRID_SIZE; row++) {
            sb.append("R").append(row + 1).append(" |");
            
            for (int col = 0; col < GRID_SIZE; col++) {
                Box box = grid.getBox(row, col);
                sb.append(String.format(CELL_FORMAT, formatBox(box)));
            }
            
            sb.append("\n");
            sb.append(HORIZONTAL_LINE).append("\n");
        }
        
        return sb.toString();
    }

    /**
     * Returns a formatted representation of a single box.
     * Format: "TYPE-LETTER-CONTENT" (e.g., "R-E-M")
     * @param box The box to format
     * @return The formatted box string
     */
    public static String formatBox(Box box) {
        return box.toString();
    }

    /**
     * Returns an ASCII cube diagram showing all 6 surfaces of a box.
     * The middle position corresponds to the top side.
     * @param surfaces The BoxSurfaces to display
     * @return The formatted cube diagram
     */
    public static String formatCubeDiagram(BoxSurfaces surfaces) {
        return surfaces.toCubeDiagram();
    }

    /**
     * Returns a simplified horizontal line for the grid.
     * @return The horizontal line string
     */
    public static String getHorizontalLine() {
        return HORIZONTAL_LINE;
    }
}
