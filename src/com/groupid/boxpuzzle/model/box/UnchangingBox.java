package com.groupid.boxpuzzle.model.box;

import com.groupid.boxpuzzle.model.BoxSurfaces;
import com.groupid.boxpuzzle.model.Position;
import com.groupid.boxpuzzle.util.RandomGenerator;

/**
 * Box whose letters cannot be changed by any SpecialTool.
 * Always contains a SpecialTool (guaranteed).
 * Generation probability: 10%
 */
public class UnchangingBox extends Box {
    // Tool generation probability constant
    private static final double INDIVIDUAL_TOOL_CHANCE = 0.20;  // 20% per tool type

    /**
     * Constructor for UnchangingBox.
     * Calls super() and then initializes content via template method.
     * @param surfaces The 6 surfaces with letters
     * @param position The position on the grid
     */
    public UnchangingBox(BoxSurfaces surfaces, Position position) {
        super(surfaces, position);
    }

    /**
     * Returns 'U' as the box type marker for UnchangingBox.
     * @return The character 'U'
     */
    @Override
    public char getBoxTypeMarker() {
        return 'U';
    }

    /**
     * Initializes the content of this UnchangingBox.
     * Always contains one of 5 SpecialTools (20% each).
     */
    @Override
    protected void initializeContent() {
        // UnchangingBox is guaranteed to contain a tool
        this.containedTool = RandomGenerator.generateToolForUnchangingBox();
    }

    /**
     * Override stampTopSide to do nothing.
     * UnchangingBox letters cannot be changed by any SpecialTool.
     * @param letter The letter to stamp (ignored)
     */
    @Override
    public void stampTopSide(char letter) {
        // Do nothing - UnchangingBox cannot be restamped
    }

    /**
     * Returns false since UnchangingBox cannot be stamped.
     * @return false
     */
    @Override
    public boolean canBeStamped() {
        return false;
    }
}
