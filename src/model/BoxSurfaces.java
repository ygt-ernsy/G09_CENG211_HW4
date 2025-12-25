package model;

import util.RandomGenerator;

/**
 * Manages the 6 surfaces of a cubic box and their letter assignments.
 * Surface indices: 0=TOP, 1=BOTTOM, 2=FRONT, 3=BACK, 4=LEFT, 5=RIGHT
 */
public class BoxSurfaces {
    // Surface index constants for clarity
    public static final int TOP = 0;
    public static final int BOTTOM = 1;
    public static final int FRONT = 2;
    public static final int BACK = 3;
    public static final int LEFT = 4;
    public static final int RIGHT = 5;
    
    private char[] surfaces;  // Array of 6 letters representing each surface

    /**
     * Constructor for BoxSurfaces with specified letters.
     * @param letters Array of 6 letters (A-H), each appearing at most twice
     */
    public BoxSurfaces(char[] letters) {
        if (letters == null || letters.length != 6) {
            throw new IllegalArgumentException("BoxSurfaces requires exactly 6 letters");
        }
        this.surfaces = letters.clone();
    }

    /**
     * Factory method to generate random valid surfaces.
     * Ensures no letter appears more than twice.
     * @return A new BoxSurfaces instance with random letters
     */
    public static BoxSurfaces generateRandom() {
        return new BoxSurfaces(RandomGenerator.generateValidSurfaces());
    }

    /**
     * Returns the letter on the top surface.
     * @return The top surface letter
     */
    public char getTop() {
        return surfaces[TOP];
    }

    /**
     * Sets the letter on the top surface.
     * @param letter The new letter for the top surface
     */
    public void setTop(char letter) {
        surfaces[TOP] = letter;
    }

    /**
     * Returns the letter on the bottom surface.
     * @return The bottom surface letter
     */
    public char getBottom() {
        return surfaces[BOTTOM];
    }

    /**
     * Sets the letter on the bottom surface.
     * @param letter The new letter for the bottom surface
     */
    public void setBottom(char letter) {
        surfaces[BOTTOM] = letter;
    }

    /**
     * Flips the box upside down by swapping TOP and BOTTOM surfaces.
     */
    public void flip() {
        char temp = surfaces[TOP];
        surfaces[TOP] = surfaces[BOTTOM];
        surfaces[BOTTOM] = temp;
    }

    /**
     * Rotates surfaces as if the box was rolled in the given direction.
     * @param direction The direction of the roll
     */
    public void rollInDirection(Direction direction) {
        char temp;
        switch (direction) {
            case RIGHT:
                // Rolling right: LEFT→TOP→RIGHT→BOTTOM→LEFT
                temp = surfaces[LEFT];
                surfaces[LEFT] = surfaces[BOTTOM];
                surfaces[BOTTOM] = surfaces[RIGHT];
                surfaces[RIGHT] = surfaces[TOP];
                surfaces[TOP] = temp;
                break;
            case LEFT:
                // Rolling left: RIGHT→TOP→LEFT→BOTTOM→RIGHT
                temp = surfaces[RIGHT];
                surfaces[RIGHT] = surfaces[BOTTOM];
                surfaces[BOTTOM] = surfaces[LEFT];
                surfaces[LEFT] = surfaces[TOP];
                surfaces[TOP] = temp;
                break;
            case UP:
                // Rolling up: FRONT→TOP→BACK→BOTTOM→FRONT
                temp = surfaces[FRONT];
                surfaces[FRONT] = surfaces[BOTTOM];
                surfaces[BOTTOM] = surfaces[BACK];
                surfaces[BACK] = surfaces[TOP];
                surfaces[TOP] = temp;
                break;
            case DOWN:
                // Rolling down: BACK→TOP→FRONT→BOTTOM→BACK
                temp = surfaces[BACK];
                surfaces[BACK] = surfaces[BOTTOM];
                surfaces[BOTTOM] = surfaces[FRONT];
                surfaces[FRONT] = surfaces[TOP];
                surfaces[TOP] = temp;
                break;
        }
    }

    /**
     * Returns a copy of all 6 surfaces.
     * @return Array copy of all surfaces
     */
    public char[] getAllSurfaces() {
        return surfaces.clone();
    }

    /**
     * Returns an ASCII cube diagram showing all 6 surfaces.
     * The middle position shows the TOP surface.
     * Layout:
     *      -----
     *      | B |      (BACK)
     * -------------
     * | L | T | R |   (LEFT, TOP, RIGHT)
     * -------------
     *      | F |      (FRONT)
     *      -----
     *      | Bo|      (BOTTOM)
     *      -----
     * @return The cube diagram as a string
     */
    public String toCubeDiagram() {
        StringBuilder sb = new StringBuilder();
        sb.append("      -----\n");
        sb.append("      | ").append(surfaces[BACK]).append(" |\n");
        sb.append("  -------------\n");
        sb.append("  | ").append(surfaces[LEFT]).append(" | ")
          .append(surfaces[TOP]).append(" | ")
          .append(surfaces[RIGHT]).append(" |\n");
        sb.append("  -------------\n");
        sb.append("      | ").append(surfaces[FRONT]).append(" |\n");
        sb.append("      -----\n");
        sb.append("      | ").append(surfaces[BOTTOM]).append(" |\n");
        sb.append("      -----");
        return sb.toString();
    }

    /**
     * Creates a copy of this BoxSurfaces.
     * @return A new BoxSurfaces with the same letter values
     */
    public BoxSurfaces copy() {
        return new BoxSurfaces(surfaces.clone());
    }

    /**
     * Returns a string representation of the surfaces.
     * @return String showing all surface letters
     */
    @Override
    public String toString() {
        return String.format("Surfaces[T=%c,B=%c,F=%c,Bk=%c,L=%c,R=%c]",
            surfaces[TOP], surfaces[BOTTOM], surfaces[FRONT],
            surfaces[BACK], surfaces[LEFT], surfaces[RIGHT]);
    }
}
