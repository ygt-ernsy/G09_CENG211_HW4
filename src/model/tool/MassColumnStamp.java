package model.tool;

import game.BoxGrid;
import model.Position;

/**
 * A tool that stamps all boxes in an entire column to the target letter.
 * The column is determined by the target position's column value.
 */
public class MassColumnStamp extends SpecialTool {

    /**
     * Constructor for MassColumnStamp.
     */
    public MassColumnStamp() {
        super("MassColumnStamp");
    }

    /**
     * Stamps all 8 boxes in the specified column to the target letter.
     * Respects UnchangingBox immunity.
     * @param grid The game grid
     * @param targetLetter The target letter to stamp
     * @param targetPos Position whose column will be stamped
     */
    @Override
    public void apply(BoxGrid grid, char targetLetter, Position targetPos) {
        grid.stampColumn(targetPos.getColumn(), targetLetter);
    }

    /**
     * Returns the prompt for selecting a column.
     * @return The usage prompt
     */
    @Override
    public String getUsagePrompt() {
        return "Please select a column (1-8):";
    }
}
