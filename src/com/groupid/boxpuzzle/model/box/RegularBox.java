package com.groupid.boxpuzzle.model.box;

import com.groupid.boxpuzzle.model.BoxSurfaces;
import com.groupid.boxpuzzle.model.Position;
import com.groupid.boxpuzzle.util.RandomGenerator;

/**
 * Standard box implementation with 75% chance of containing a SpecialTool.
 * Generation probability: 85%
 * This is the most common box type in the game.
 */
public class RegularBox extends Box {
    // Tool generation probability constants
    private static final double TOOL_PROBABILITY = 0.75;          // 75% chance of tool
    private static final double INDIVIDUAL_TOOL_CHANCE = 0.15;    // 15% per tool type

    /**
     * Constructor for RegularBox.
     * Calls super() and then initializes content via template method.
     * @param surfaces The 6 surfaces with letters
     * @param position The position on the grid
     */
    public RegularBox(BoxSurfaces surfaces, Position position) {
        super(surfaces, position);
    }

    /**
     * Returns 'R' as the box type marker for RegularBox.
     * @return The character 'R'
     */
    @Override
    public char getBoxTypeMarker() {
        return 'R';
    }

    /**
     * Initializes the content of this RegularBox.
     * 75% chance of containing one of 5 SpecialTools (15% each).
     * 25% chance of being empty.
     */
    @Override
    protected void initializeContent() {
        // Use RandomGenerator to get a tool (or null if empty)
        this.containedTool = RandomGenerator.generateToolForRegularBox();
    }
}
