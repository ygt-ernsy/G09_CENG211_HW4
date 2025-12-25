package com.groupid.boxpuzzle.model.tool;

import com.groupid.boxpuzzle.game.BoxGrid;
import com.groupid.boxpuzzle.model.Position;

/**
 * A tool that stamps all boxes in an entire row to the target letter.
 * The row is determined by the target position's row value.
 */
public class MassRowStamp extends SpecialTool {

    /**
     * Constructor for MassRowStamp.
     */
    public MassRowStamp() {
        super("MassRowStamp");
    }

    /**
     * Stamps all 8 boxes in the specified row to the target letter.
     * Respects UnchangingBox immunity.
     * @param grid The game grid
     * @param targetLetter The target letter to stamp
     * @param targetPos Position whose row will be stamped
     */
    @Override
    public void apply(BoxGrid grid, char targetLetter, Position targetPos) {
        grid.stampRow(targetPos.getRow(), targetLetter);
    }

    /**
     * Returns the prompt for selecting a row.
     * @return The usage prompt
     */
    @Override
    public String getUsagePrompt() {
        return "Please select a row (1-8):";
    }
}
