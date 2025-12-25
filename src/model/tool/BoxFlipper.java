package model.tool;

import exceptions.UnmovableFixedBoxException;
import game.BoxGrid;
import model.Position;
import model.box.Box;
import model.box.FixedBox;

/**
 * A tool that flips a box upside down.
 * The chosen box's top side becomes its new bottom side, and vice versa.
 */
public class BoxFlipper extends SpecialTool {

    /**
     * Constructor for BoxFlipper.
     */
    public BoxFlipper() {
        super("BoxFlipper");
    }

    /**
     * Flips the box at the target position upside down.
     * @param grid The game grid
     * @param targetLetter Not used for this tool
     * @param targetPos The position of the box to flip
     * @throws UnmovableFixedBoxException if the box is a FixedBox
     */
    @Override
    public void apply(BoxGrid grid, char targetLetter, Position targetPos)
            throws UnmovableFixedBoxException {
        Box box = grid.getBox(targetPos);
        
        // Check if it's a FixedBox
        if (box instanceof FixedBox) {
            throw new UnmovableFixedBoxException(targetPos);
        }
        
        // Flip the box (swap top and bottom)
        box.getSurfaces().flip();
    }

    /**
     * Returns the prompt for selecting a box to flip.
     * @return The usage prompt
     */
    @Override
    public String getUsagePrompt() {
        return "Please enter the location of the box to flip:";
    }
}
