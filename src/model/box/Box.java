package model.box;

import exceptions.EmptyBoxException;
import interfaces.Openable;
import interfaces.Rollable;
import interfaces.Stampable;
import model.BoxSurfaces;
import model.Direction;
import model.Position;
import model.tool.SpecialTool;

/**
 * Abstract base class for all box types in the game.
 * Implements Rollable, Stampable, and Openable interfaces.
 * Extended by RegularBox, UnchangingBox, and FixedBox.
 */
public abstract class Box implements Rollable, Stampable, Openable {
    protected BoxSurfaces surfaces;      // The 6 surfaces with letters
    protected SpecialTool containedTool; // Tool inside the box (nullable)
    protected boolean opened;            // Whether box has been opened
    protected Position position;         // Current position on grid

    /**
     * Constructor for Box.
     * @param surfaces The 6 surfaces with letters
     * @param position The position on the grid
     */
    public Box(BoxSurfaces surfaces, Position position) {
        this.surfaces = surfaces;
        this.position = position;
        this.opened = false;
        this.containedTool = null;
        initializeContent();
    }

    /**
     * Returns the type marker for display ('R', 'U', or 'X').
     * @return The box type marker character
     */
    public abstract char getBoxTypeMarker();

    /**
     * Template method for subclass-specific initialization.
     * Called during construction to set up contained tools.
     */
    protected abstract void initializeContent();

    /**
     * Returns the letter on the top surface.
     * @return The top surface letter
     */
    public char getTopSide() {
        return surfaces.getTop();
    }

    /**
     * Default roll implementation: rotates surfaces based on direction.
     * Can be overridden by subclasses (FixedBox overrides to do nothing).
     * @param direction The direction to roll
     */
    @Override
    public void roll(Direction direction) {
        surfaces.rollInDirection(direction);
    }

    /**
     * Default implementation: box can roll.
     * Overridden by FixedBox to return false.
     * @return true if the box can roll
     */
    @Override
    public boolean canRoll() {
        return true;
    }

    /**
     * Returns all 6 surfaces for viewing.
     * @return The BoxSurfaces object
     */
    public BoxSurfaces getSurfaces() {
        return surfaces;
    }

    /**
     * Checks if the box has already been opened.
     * @return true if the box was already opened
     */
    @Override
    public boolean hasBeenOpened() {
        return opened;
    }

    /**
     * Sets the tool contained in this box.
     * @param tool The tool to place in the box
     */
    public void setContainedTool(SpecialTool tool) {
        this.containedTool = tool;
    }

    /**
     * Opens the box and returns the tool inside.
     * Marks the box as opened and clears the contained tool.
     * @return The SpecialTool inside the box
     * @throws EmptyBoxException if the box is empty
     */
    @Override
    public SpecialTool open() throws EmptyBoxException {
        if (containedTool == null) {
            opened = true;
            throw new EmptyBoxException(position);
        }
        
        SpecialTool tool = containedTool;
        containedTool = null;
        opened = true;
        return tool;
    }

    /**
     * Checks if the box has no tool.
     * @return true if the box is empty
     */
    @Override
    public boolean isEmpty() {
        return containedTool == null;
    }

    /**
     * Stamps the top side with a new letter.
     * Can be overridden by subclasses (UnchangingBox does nothing).
     * @param letter The new letter for the top side
     */
    @Override
    public void stampTopSide(char letter) {
        surfaces.setTop(letter);
    }

    /**
     * Default implementation: box can be stamped.
     * Overridden by UnchangingBox to return false.
     * @return true if the box can be stamped
     */
    @Override
    public boolean canBeStamped() {
        return true;
    }

    /**
     * Returns the content marker for display.
     * 'M' for mystery (not opened), 'O' for opened/empty.
     * @return The content marker
     */
    public String getContentMarker() {
        if (opened || containedTool == null) {
            return "O";
        }
        return "M";
    }

    /**
     * Returns the position of this box.
     * @return The position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the position of this box.
     * @param position The new position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Returns a string representation of this box.
     * Format: "TYPE-LETTER-CONTENT" (e.g., "R-E-M")
     * @return The string representation
     */
    @Override
    public String toString() {
        return getBoxTypeMarker() + "-" + getTopSide() + "-" + getContentMarker();
    }
}
