package exceptions;

import model.Position;

/**
 * Exception thrown when opening a box that contains no SpecialTool.
 * This occurs when the player opens an empty box, wasting their turn.
 */
public class EmptyBoxException extends Exception {
    
    /**
     * Default constructor with standard message.
     */
    public EmptyBoxException() {
        super("Box is empty");
    }

    /**
     * Constructor with position information.
     * @param position The position of the empty box
     */
    public EmptyBoxException(Position position) {
        super("Box at " + position.toDisplayString() + " is empty");
    }
}
