package util;

import java.util.Random;

import model.Position;
import model.BoxSurfaces;
import model.box.Box;
import model.box.RegularBox;
import model.box.UnchangingBox;
import model.box.FixedBox;
import model.tool.*;

/**
 * Centralized random number generation utility for consistent behavior.
 * Provides methods for generating random letters, boxes, tools, and surfaces.
 */
public class RandomGenerator {
    private static final Random random = new Random();
    
    // Letters available for stamping (A-H)
    private static final char[] LETTERS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
    
    // Box generation probabilities
    private static final double REGULAR_BOX_PROBABILITY = 0.85;      // 85%
    private static final double UNCHANGING_BOX_PROBABILITY = 0.10;   // 10%
    // FixedBox probability is remaining 5%
    
    // Tool generation probabilities for RegularBox
    private static final double TOOL_PROBABILITY_REGULAR = 0.75;     // 75% chance of tool
    private static final double INDIVIDUAL_TOOL_CHANCE_REGULAR = 0.15; // 15% each tool
    
    // Tool generation for UnchangingBox (always has a tool)
    private static final double INDIVIDUAL_TOOL_CHANCE_UNCHANGING = 0.20; // 20% each tool

    /**
     * Returns a random letter from A-H.
     * @return A random letter
     */
    public static char randomLetter() {
        return LETTERS[random.nextInt(LETTERS.length)];
    }

    /**
     * Returns the target letter from A-H with equal probability (12.5% each).
     * @return A random target letter
     */
    public static char generateTargetLetter() {
        return LETTERS[random.nextInt(LETTERS.length)];
    }

    /**
     * Generates a random box based on probability distribution.
     * 85% RegularBox, 10% UnchangingBox, 5% FixedBox
     * @param position The position for the new box
     * @return A new random Box instance
     */
    public static Box generateRandomBox(Position position) {
        double roll = random.nextDouble();
        BoxSurfaces surfaces = new BoxSurfaces(generateValidSurfaces());
        
        if (roll < REGULAR_BOX_PROBABILITY) {
            // 85% chance: RegularBox
            return new RegularBox(surfaces, position);
        } else if (roll < REGULAR_BOX_PROBABILITY + UNCHANGING_BOX_PROBABILITY) {
            // 10% chance: UnchangingBox
            return new UnchangingBox(surfaces, position);
        } else {
            // 5% chance: FixedBox
            return new FixedBox(surfaces, position);
        }
    }

    /**
     * Generates a tool for RegularBox.
     * 75% chance of getting a tool (15% each), 25% chance of null (empty).
     * @return A SpecialTool or null if empty
     */
    public static SpecialTool generateToolForRegularBox() {
        double roll = random.nextDouble();
        
        if (roll >= TOOL_PROBABILITY_REGULAR) {
            // 25% chance: empty
            return null;
        }
        
        // 75% chance: one of the 5 tools (15% each)
        double toolRoll = random.nextDouble();
        if (toolRoll < INDIVIDUAL_TOOL_CHANCE_REGULAR) {
            return new PlusShapeStamp();
        } else if (toolRoll < 2 * INDIVIDUAL_TOOL_CHANCE_REGULAR) {
            return new MassRowStamp();
        } else if (toolRoll < 3 * INDIVIDUAL_TOOL_CHANCE_REGULAR) {
            return new MassColumnStamp();
        } else if (toolRoll < 4 * INDIVIDUAL_TOOL_CHANCE_REGULAR) {
            return new BoxFlipper();
        } else {
            return new BoxFixer();
        }
    }

    /**
     * Generates a tool for UnchangingBox.
     * Always returns a tool (20% chance for each of 5 tools).
     * @return A SpecialTool (never null)
     */
    public static SpecialTool generateToolForUnchangingBox() {
        double roll = random.nextDouble();
        
        if (roll < INDIVIDUAL_TOOL_CHANCE_UNCHANGING) {
            return new PlusShapeStamp();
        } else if (roll < 2 * INDIVIDUAL_TOOL_CHANCE_UNCHANGING) {
            return new MassRowStamp();
        } else if (roll < 3 * INDIVIDUAL_TOOL_CHANCE_UNCHANGING) {
            return new MassColumnStamp();
        } else if (roll < 4 * INDIVIDUAL_TOOL_CHANCE_UNCHANGING) {
            return new BoxFlipper();
        } else {
            return new BoxFixer();
        }
    }

    /**
     * Generates valid surfaces where no letter appears more than twice.
     * @return Array of 6 valid letters
     */
    public static char[] generateValidSurfaces() {
        char[] surfaces = new char[6];
        int[] letterCounts = new int[8];  // Counts for letters A-H
        
        for (int i = 0; i < 6; i++) {
            char letter;
            int letterIndex;
            
            // Keep generating until we find a letter that hasn't been used twice
            do {
                letter = randomLetter();
                letterIndex = letter - 'A';
            } while (letterCounts[letterIndex] >= 2);
            
            surfaces[i] = letter;
            letterCounts[letterIndex]++;
        }
        
        return surfaces;
    }

    /**
     * Returns true with the given probability.
     * @param probability The probability (0.0 to 1.0)
     * @return true if the random roll is less than probability
     */
    public static boolean chance(double probability) {
        return random.nextDouble() < probability;
    }

    /**
     * Returns a random integer between 0 (inclusive) and bound (exclusive).
     * @param bound The upper bound (exclusive)
     * @return A random integer
     */
    public static int nextInt(int bound) {
        return random.nextInt(bound);
    }
}
