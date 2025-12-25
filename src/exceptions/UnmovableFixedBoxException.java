package exceptions;

import model.Position;

/**
 * Exception thrown when attempting to roll or flip a FixedBox.
 * FixedBox cannot be moved, so this exception signals an invalid operation.
 */
public class UnmovableFixedBoxException extends Exception {
    
    /**
     * Default constructor with standard message.
     */
    public UnmovableFixedBoxException() {
        super("Cannot move a FixedBox");
    }

    /**
     * Constructor with custom message.
     * @param message The custom error message
     */
    public UnmovableFixedBoxException(String message) {
        super(message);
    }

    /**
     * Constructor with position information.
     * @param position The position of the FixedBox that cannot be moved
     */
    public UnmovableFixedBoxException(Position position) {
        super("Cannot move FixedBox at " + position.toDisplayString());
    }
}
