package exceptions;

import model.Position;

/**
 * Exception thrown when attempting to fix an already-fixed box using BoxFixer.
 * A FixedBox cannot be fixed again.
 */
public class BoxAlreadyFixedException extends Exception {
    
    /**
     * Default constructor with standard message.
     */
    public BoxAlreadyFixedException() {
        super("Box is already fixed");
    }

    /**
     * Constructor with position information.
     * @param position The position of the box that is already fixed
     */
    public BoxAlreadyFixedException(Position position) {
        super("Box at " + position.toDisplayString() + " is already fixed");
    }
}
