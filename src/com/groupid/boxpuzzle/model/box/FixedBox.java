package com.groupid.boxpuzzle.model.box;

import com.groupid.boxpuzzle.exceptions.EmptyBoxException;
import com.groupid.boxpuzzle.model.BoxSurfaces;
import com.groupid.boxpuzzle.model.Direction;
import com.groupid.boxpuzzle.model.Position;
import com.groupid.boxpuzzle.model.tool.SpecialTool;

/**
 * Immovable box that blocks the domino effect and is always empty.
 * Cannot be rolled in any direction, and its top side stays the same.
 * Generation probability: 5%
 * Tool generation: Always empty (0%)
 */
public class FixedBox extends Box {

    /**
     * Constructor for FixedBox.
     * Sets opened = true since FixedBox is always shown as empty.
     * @param surfaces The 6 surfaces with letters
     * @param position The position on the grid
     */
    public FixedBox(BoxSurfaces surfaces, Position position) {
        super(surfaces, position);
        // FixedBox always shows as empty/opened
        this.opened = true;
    }

    /**
     * Returns 'X' as the box type marker for FixedBox.
     * @return The character 'X'
     */
    @Override
    public char getBoxTypeMarker() {
        return 'X';
    }

    /**
     * Does nothing - FixedBox is always empty.
     */
    @Override
    protected void initializeContent() {
        // FixedBox is always empty - no tool initialization
        this.containedTool = null;
    }

    /**
     * Override roll to do nothing.
     * FixedBox cannot be rolled in any direction.
     * @param direction The direction (ignored)
     */
    @Override
    public void roll(Direction direction) {
        // Do nothing - FixedBox cannot be rolled
    }

    /**
     * Returns false since FixedBox cannot be rolled.
     * @return false
     */
    @Override
    public boolean canRoll() {
        return false;
    }

    /**
     * Always throws EmptyBoxException since FixedBox is always empty.
     * @return Never returns (always throws)
     * @throws EmptyBoxException always
     */
    @Override
    public SpecialTool open() throws EmptyBoxException {
        throw new EmptyBoxException(position);
    }

    /**
     * Convenience method to check if this is a FixedBox without instanceof.
     * @return true
     */
    public boolean isFixed() {
        return true;
    }

    /**
     * Returns 'O' since FixedBox is always marked as empty.
     * @return The string "O"
     */
    @Override
    public String getContentMarker() {
        return "O";
    }
}
