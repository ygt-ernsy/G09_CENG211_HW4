package model.tool;

import exceptions.BoxAlreadyFixedException;
import game.BoxGrid;
import model.Position;
import model.box.Box;
import model.box.FixedBox;

/**
 * A tool that replaces a box with an identical FixedBox copy.
 * If the box has a tool inside, it is removed from the game.
 */
public class BoxFixer extends SpecialTool {

    /**
     * Constructor for BoxFixer.
     */
    public BoxFixer() {
        super("BoxFixer");
    }

    /**
     * Replaces the box at the target position with a FixedBox copy.
     * Any contained tool is removed from the game.
     * @param grid The game grid
     * @param targetLetter Not used for this tool
     * @param targetPos The position of the box to fix
     * @throws BoxAlreadyFixedException if the box is already a FixedBox
     */
    @Override
    public void apply(BoxGrid grid, char targetLetter, Position targetPos)
            throws BoxAlreadyFixedException {
        Box box = grid.getBox(targetPos);
        
        // Check if it's already a FixedBox
        if (box instanceof FixedBox) {
            throw new BoxAlreadyFixedException(targetPos);
        }
        
        // Create a new FixedBox with the same surfaces
        FixedBox fixedBox = new FixedBox(box.getSurfaces().copy(), targetPos);
        
        // Replace the original box with the FixedBox
        grid.setBox(targetPos, fixedBox);
    }

    /**
     * Returns the prompt for selecting a box to fix.
     * @return The usage prompt
     */
    @Override
    public String getUsagePrompt() {
        return "Please enter the location of the box to fix:";
    }
}
