package model.tool;

import game.BoxGrid;
import model.Position;

/**
 * A tool that stamps 5 boxes in a plus shape to the target letter.
 * Stamps the center box and its 4 adjacent neighbors (up, down, left, right).
 */
public class PlusShapeStamp extends SpecialTool {

    /**
     * Constructor for PlusShapeStamp.
     */
    public PlusShapeStamp() {
        super("PlusShapeStamp");
    }

    /**
     * Stamps the center position and its 4 adjacent neighbors to the target letter.
     * If a neighbor doesn't exist (edge case), it is skipped.
     * Respects UnchangingBox immunity.
     * @param grid The game grid
     * @param targetLetter The target letter to stamp
     * @param centerPos The center position for the plus shape
     */
    @Override
    public void apply(BoxGrid grid, char targetLetter, Position centerPos) {
        grid.stampPlusShape(centerPos, targetLetter);
    }

    /**
     * Returns the prompt for selecting the center box.
     * @return The usage prompt
     */
    @Override
    public String getUsagePrompt() {
        return "Please enter the location of the center box:";
    }
}
