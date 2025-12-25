package interfaces;

/**
 * Interface for objects whose surfaces can be stamped with letters.
 * Implemented by Box and its subclasses.
 */
public interface Stampable {
    
    /**
     * Stamps the top side with a new letter.
     * @param letter The letter to stamp on the top side
     */
    void stampTopSide(char letter);
    
    /**
     * Checks if the object accepts stamping.
     * @return true if the object can be stamped, false otherwise (e.g., UnchangingBox returns false)
     */
    boolean canBeStamped();
}
