package com.groupid.boxpuzzle.model.tool;

import com.groupid.boxpuzzle.exceptions.BoxAlreadyFixedException;
import com.groupid.boxpuzzle.exceptions.UnmovableFixedBoxException;
import com.groupid.boxpuzzle.game.BoxGrid;
import com.groupid.boxpuzzle.model.Position;

/**
 * Abstract base class for all special tools in the game.
 * Tools are found inside boxes and can be used to manipulate the grid.
 */
public abstract class SpecialTool {
    protected String toolName;  // Display name of the tool

    /**
     * Constructor for SpecialTool.
     * @param toolName The display name of this tool
     */
    public SpecialTool(String toolName) {
        this.toolName = toolName;
    }

    /**
     * Returns the tool's display name.
     * @return The tool name
     */
    public String getToolName() {
        return toolName;
    }

    /**
     * Applies the tool's effect to the grid.
     * Parameters vary in relevance based on tool type.
     * @param grid The game grid to modify
     * @param targetLetter The target letter for stamping tools
     * @param targetPos The target position for the tool's effect
     * @throws UnmovableFixedBoxException if trying to move a FixedBox
     * @throws BoxAlreadyFixedException if trying to fix an already-fixed box
     */
    public abstract void apply(BoxGrid grid, char targetLetter, Position targetPos)
        throws UnmovableFixedBoxException, BoxAlreadyFixedException;

    /**
     * Returns the prompt text for user input when using this tool.
     * @return The usage prompt string
     */
    public abstract String getUsagePrompt();

    /**
     * Returns the tool name for display.
     * @return The tool name
     */
    @Override
    public String toString() {
        return toolName;
    }
}
