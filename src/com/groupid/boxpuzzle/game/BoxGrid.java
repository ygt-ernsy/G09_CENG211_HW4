package com.groupid.boxpuzzle.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.groupid.boxpuzzle.exceptions.UnmovableFixedBoxException;
import com.groupid.boxpuzzle.model.Direction;
import com.groupid.boxpuzzle.model.Position;
import com.groupid.boxpuzzle.model.box.Box;
import com.groupid.boxpuzzle.model.box.FixedBox;
import com.groupid.boxpuzzle.util.RandomGenerator;

/*
 * ANSWER TO COLLECTIONS QUESTION:
 *
 * We chose ArrayList<ArrayList<Box>> as the data structure for the BoxGrid for the
 * following reasons:
 *
 * 1. INDEXED ACCESS: The game requires frequent access to boxes by row and column
 *    indices (e.g., R2-C4). ArrayList provides O(1) random access via get(index),
 *    which is essential for efficient grid operations.
 *
 * 2. DYNAMIC SIZING DURING INITIALIZATION: While the grid is fixed at 8x8, using
 *    ArrayList allows flexible initialization without pre-declaring array dimensions
 *    at compile time, making the code more adaptable.
 *
 * 3. ITERATION CAPABILITIES: ArrayList implements Iterable, allowing easy iteration
 *    over rows and columns using enhanced for-loops, which simplifies operations
 *    like counting target letters or displaying the grid.
 *
 * 4. MODIFICATION SUPPORT: When a BoxFixer tool is used, we need to replace a Box
 *    with a FixedBox copy. ArrayList's set(index, element) method provides O(1)
 *    replacement, unlike LinkedList which would require O(n) traversal.
 *
 * 5. COLLECTIONS FRAMEWORK INTEGRATION: ArrayList integrates seamlessly with
 *    Collections utility methods for shuffling, sorting, or other operations that
 *    might be useful for game extensions.
 *
 * Alternative Considered: 2D array (Box[][]) would also provide O(1) access but
 * lacks the flexibility and utility methods of the Collections framework.
 */

/**
 * Manages the 8×8 grid of boxes for the puzzle game.
 * Handles rolling mechanics, domino effect, and stamping operations.
 * Uses ArrayList<ArrayList<Box>> as the primary data structure.
 */
public class BoxGrid {
    private ArrayList<ArrayList<Box>> grid;  // 2D grid storage using Collections
    private static final int GRID_SIZE = 8;  // Grid dimensions constant
    private Set<Position> lastRolledPositions;  // Tracks boxes rolled in current turn

    /**
     * Constructor for BoxGrid.
     * Initializes an 8×8 grid with randomly generated boxes.
     */
    public BoxGrid() {
        this.grid = new ArrayList<>();
        this.lastRolledPositions = new HashSet<>();
        initializeGrid();
    }

    /**
     * Populates the grid with boxes based on probability distribution:
     * 85% RegularBox, 10% UnchangingBox, 5% FixedBox
     */
    private void initializeGrid() {
        for (int row = 0; row < GRID_SIZE; row++) {
            ArrayList<Box> rowList = new ArrayList<>();
            for (int col = 0; col < GRID_SIZE; col++) {
                Position pos = new Position(row, col);
                Box box = RandomGenerator.generateRandomBox(pos);
                rowList.add(box);
            }
            grid.add(rowList);
        }
    }

    /**
     * Returns the box at the specified position.
     * @param pos The position to get the box from
     * @return The box at the position
     */
    public Box getBox(Position pos) {
        return grid.get(pos.getRow()).get(pos.getColumn());
    }

    /**
     * Returns the box at the specified row and column indices.
     * @param row The row index (0-based)
     * @param col The column index (0-based)
     * @return The box at the position
     */
    public Box getBox(int row, int col) {
        return grid.get(row).get(col);
    }

    /**
     * Sets the box at the specified position.
     * Used by BoxFixer to replace a box with a FixedBox.
     * @param pos The position to set
     * @param box The new box
     */
    public void setBox(Position pos, Box box) {
        grid.get(pos.getRow()).set(pos.getColumn(), box);
    }

    /**
     * Initiates a domino-effect roll from an edge position.
     * @param edgePos The edge position to start rolling from
     * @param direction The direction to roll
     * @throws UnmovableFixedBoxException if the edge box is a FixedBox
     */
    public void rollFromEdge(Position edgePos, Direction direction) 
            throws UnmovableFixedBoxException {
        // Clear previous rolled positions
        lastRolledPositions.clear();
        
        Box edgeBox = getBox(edgePos);
        
        // Check if edge box is a FixedBox
        if (edgeBox instanceof FixedBox) {
            throw new UnmovableFixedBoxException(edgePos);
        }
        
        // Perform the domino roll
        performDominoRoll(edgePos, direction);
    }

    /**
     * Performs the domino roll from a starting position in the given direction.
     * Rolls boxes until grid boundary is reached or a FixedBox is encountered.
     * @param startPos The starting position
     * @param direction The direction to roll
     */
    private void performDominoRoll(Position startPos, Direction direction) {
        Position currentPos = startPos;
        
        // Roll boxes until we hit a boundary or a FixedBox
        while (currentPos != null) {
            Box box = getBox(currentPos);
            
            // Check if current box is a FixedBox (stops propagation)
            if (box instanceof FixedBox) {
                break;
            }
            
            // Roll the box
            box.roll(direction);
            lastRolledPositions.add(currentPos);
            
            // Move to next position in the direction
            currentPos = currentPos.move(direction);
        }
    }

    /**
     * Returns the set of positions that were rolled in the last operation.
     * @return Set of rolled positions
     */
    public Set<Position> getLastRolledPositions() {
        return new HashSet<>(lastRolledPositions);
    }

    /**
     * Checks if a position is on the grid edge.
     * @param pos The position to check
     * @return true if on edge (row 0, row 7, col 0, or col 7)
     */
    public boolean isEdgePosition(Position pos) {
        int row = pos.getRow();
        int col = pos.getColumn();
        return row == 0 || row == GRID_SIZE - 1 || col == 0 || col == GRID_SIZE - 1;
    }

    /**
     * Checks if a position is at a grid corner.
     * @param pos The position to check
     * @return true if at corner
     */
    public boolean isCornerPosition(Position pos) {
        int row = pos.getRow();
        int col = pos.getColumn();
        return (row == 0 || row == GRID_SIZE - 1) && (col == 0 || col == GRID_SIZE - 1);
    }

    /**
     * Counts boxes with the target letter on their top side.
     * @param targetLetter The letter to count
     * @return The count of matching boxes
     */
    public int countTargetLetter(char targetLetter) {
        int count = 0;
        for (ArrayList<Box> row : grid) {
            for (Box box : row) {
                if (box.getTopSide() == targetLetter) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Checks if at least one edge box is not a FixedBox.
     * @return true if any movable edge box exists
     */
    public boolean hasAnyMovableEdgeBox() {
        // Check top and bottom rows
        for (int col = 0; col < GRID_SIZE; col++) {
            if (!(grid.get(0).get(col) instanceof FixedBox)) return true;
            if (!(grid.get(GRID_SIZE - 1).get(col) instanceof FixedBox)) return true;
        }
        
        // Check left and right columns (excluding corners already checked)
        for (int row = 1; row < GRID_SIZE - 1; row++) {
            if (!(grid.get(row).get(0) instanceof FixedBox)) return true;
            if (!(grid.get(row).get(GRID_SIZE - 1) instanceof FixedBox)) return true;
        }
        
        return false;
    }

    /**
     * Stamps a single box's top side with the specified letter.
     * Respects UnchangingBox immunity.
     * @param pos The position of the box to stamp
     * @param letter The letter to stamp
     */
    public void stampBox(Position pos, char letter) {
        Box box = getBox(pos);
        box.stampTopSide(letter);  // UnchangingBox overrides to do nothing
    }

    /**
     * Stamps all boxes in a row with the specified letter.
     * Respects UnchangingBox immunity.
     * @param row The row index (0-based)
     * @param letter The letter to stamp
     */
    public void stampRow(int row, char letter) {
        for (int col = 0; col < GRID_SIZE; col++) {
            Box box = grid.get(row).get(col);
            box.stampTopSide(letter);
        }
    }

    /**
     * Stamps all boxes in a column with the specified letter.
     * Respects UnchangingBox immunity.
     * @param col The column index (0-based)
     * @param letter The letter to stamp
     */
    public void stampColumn(int col, char letter) {
        for (int row = 0; row < GRID_SIZE; row++) {
            Box box = grid.get(row).get(col);
            box.stampTopSide(letter);
        }
    }

    /**
     * Stamps boxes in a plus shape centered at the given position.
     * Stamps center and its 4 neighbors (if they exist within bounds).
     * Respects UnchangingBox immunity.
     * @param center The center position
     * @param letter The letter to stamp
     */
    public void stampPlusShape(Position center, char letter) {
        int row = center.getRow();
        int col = center.getColumn();
        
        // Stamp center
        stampBox(center, letter);
        
        // Stamp upper neighbor (if exists)
        if (row > 0) {
            stampBox(new Position(row - 1, col), letter);
        }
        
        // Stamp lower neighbor (if exists)
        if (row < GRID_SIZE - 1) {
            stampBox(new Position(row + 1, col), letter);
        }
        
        // Stamp left neighbor (if exists)
        if (col > 0) {
            stampBox(new Position(row, col - 1), letter);
        }
        
        // Stamp right neighbor (if exists)
        if (col < GRID_SIZE - 1) {
            stampBox(new Position(row, col + 1), letter);
        }
    }

    /**
     * Returns the grid size constant.
     * @return The grid size (8)
     */
    public int getGridSize() {
        return GRID_SIZE;
    }
}
