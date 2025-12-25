package com.groupid.boxpuzzle.model;

/**
 * Enum representing the four cardinal directions for rolling boxes.
 * Each direction has row and column deltas that indicate movement on the grid.
 */
public enum Direction {
    UP(0, -1),      // Row decreases (moving up in the grid)
    DOWN(0, 1),     // Row increases (moving down in the grid)
    LEFT(-1, 0),    // Column decreases (moving left in the grid)
    RIGHT(1, 0);    // Column increases (moving right in the grid)

    private final int colDelta;  // Change in column index when moving
    private final int rowDelta;  // Change in row index when moving

    /**
     * Constructor for Direction enum.
     * @param colDelta The change in column index
     * @param rowDelta The change in row index
     */
    Direction(int colDelta, int rowDelta) {
        this.colDelta = colDelta;
        this.rowDelta = rowDelta;
    }

    /**
     * Returns the row movement delta for this direction.
     * @return The row delta value
     */
    public int getRowDelta() {
        return rowDelta;
    }

    /**
     * Returns the column movement delta for this direction.
     * @return The column delta value
     */
    public int getColDelta() {
        return colDelta;
    }

    /**
     * Returns the opposite direction.
     * UP ↔ DOWN, LEFT ↔ RIGHT
     * @return The opposite direction
     */
    public Direction getOpposite() {
        switch (this) {
            case UP: return DOWN;
            case DOWN: return UP;
            case LEFT: return RIGHT;
            case RIGHT: return LEFT;
            default: return this;
        }
    }

    /**
     * Determines valid roll direction(s) from an edge position.
     * Corner positions have two valid directions, edge positions have one.
     * @param pos The edge position
     * @param gridSize The size of the grid (typically 8)
     * @return Array of valid directions for rolling from this position
     */
    public static Direction[] fromEdgePosition(Position pos, int gridSize) {
        int row = pos.getRow();
        int col = pos.getColumn();
        
        // Check if it's a corner position (has two valid directions)
        boolean isTop = (row == 0);
        boolean isBottom = (row == gridSize - 1);
        boolean isLeft = (col == 0);
        boolean isRight = (col == gridSize - 1);
        
        // Corner positions - two directions available
        if (isTop && isLeft) {
            return new Direction[]{RIGHT, DOWN};
        } else if (isTop && isRight) {
            return new Direction[]{LEFT, DOWN};
        } else if (isBottom && isLeft) {
            return new Direction[]{RIGHT, UP};
        } else if (isBottom && isRight) {
            return new Direction[]{LEFT, UP};
        }
        
        // Edge positions (not corners) - one direction available
        if (isTop) {
            return new Direction[]{DOWN};
        } else if (isBottom) {
            return new Direction[]{UP};
        } else if (isLeft) {
            return new Direction[]{RIGHT};
        } else if (isRight) {
            return new Direction[]{LEFT};
        }
        
        // Not an edge position - return empty array
        return new Direction[]{};
    }
}
