package com.groupid.boxpuzzle.game;

import java.util.Scanner;
import java.util.Set;

import com.groupid.boxpuzzle.exceptions.BoxAlreadyFixedException;
import com.groupid.boxpuzzle.exceptions.EmptyBoxException;
import com.groupid.boxpuzzle.exceptions.InvalidPositionException;
import com.groupid.boxpuzzle.exceptions.UnmovableFixedBoxException;
import com.groupid.boxpuzzle.model.Direction;
import com.groupid.boxpuzzle.model.Position;
import com.groupid.boxpuzzle.model.box.Box;
import com.groupid.boxpuzzle.model.tool.MassColumnStamp;
import com.groupid.boxpuzzle.model.tool.MassRowStamp;
import com.groupid.boxpuzzle.model.tool.SpecialTool;
import com.groupid.boxpuzzle.util.GridDisplayFormatter;
import com.groupid.boxpuzzle.util.InputValidator;

/**
 * Main game controller class for the Box Puzzle game.
 * Orchestrates game flow, manages turns, and contains menu operations.
 * Contains the MenuHandler inner class for user interactions.
 */
public class BoxPuzzle {
    private BoxGrid boxGrid;            // The 8x8 grid of boxes
    private GameState gameState;        // Current game state (turn, target letter, etc.)
    private MenuHandler menuHandler;    // Inner class instance for menu operations
    private Scanner scanner;            // User input scanner

    /**
     * Constructor for BoxPuzzle.
     * Initializes BoxGrid, GameState, and MenuHandler.
     */
    public BoxPuzzle() {
        this.scanner = new Scanner(System.in);
        this.boxGrid = new BoxGrid();
        this.gameState = new GameState();
        this.menuHandler = new MenuHandler();
    }

    /**
     * Main game loop: runs 5 turns and handles game-over conditions.
     * Displays welcome message, initial grid, and processes each turn.
     */
    public void startGame() {
        // Display welcome message
        menuHandler.displayMessage("Welcome to Box Top Side Matching Puzzle App. An 8x8 box grid is being generated.");
        menuHandler.displayMessage("Your goal is to maximize the letter \"" + 
            gameState.getTargetLetter() + "\" on the top sides of the boxes.");
        menuHandler.displayMessage("");
        
        // Display initial grid
        menuHandler.displayMessage("The initial state of the box grid:");
        menuHandler.displayGrid(boxGrid);
        
        // Main game loop for 5 turns
        while (gameState.hasRemainingTurns()) {
            // Check if any moves can be made
            if (!canMakeAnyMove()) {
                gameState.setGameOver(false);
                break;
            }
            
            // Execute the current turn
            executeTurn(gameState.getCurrentTurn());
            
            // Advance to next turn
            gameState.advanceTurn();
        }
        
        // Display final results
        displayGameResult();
    }

    /**
     * Executes a single turn: view ability, edge selection, box opening.
     * @param turnNumber The current turn number
     */
    private void executeTurn(int turnNumber) {
        menuHandler.displayMessage("=====> TURN " + turnNumber + ":");
        
        // View ability (optional)
        handleViewAbility();
        
        // STAGE 1: Edge box selection and rolling
        menuHandler.displayMessage("---> TURN " + turnNumber + " – FIRST STAGE:");
        
        Position edgePos = menuHandler.promptEdgeBoxSelection();
        Direction direction = menuHandler.promptRollDirection(edgePos);
        
        try {
            boxGrid.rollFromEdge(edgePos, direction);
            
            // Check if domino was blocked by a FixedBox
            Set<Position> rolledPositions = boxGrid.getLastRolledPositions();
            if (rolledPositions.size() < boxGrid.getGridSize()) {
                menuHandler.displayMessage("The chosen box and any box on its path have been rolled until a FixedBox has been reached. The new state of the box grid:");
            } else {
                menuHandler.displayMessage("The chosen box and any box on its path have been rolled. The new state of the box grid:");
            }
            menuHandler.displayGrid(boxGrid);
            
            // STAGE 2: Box opening and tool usage
            executeSecondStage(turnNumber, rolledPositions);
            
        } catch (UnmovableFixedBoxException e) {
            menuHandler.displayMessage("HOWEVER, IT IS A FIXED BOX AND CANNOT BE MOVED. Continuing to the next turn...");
            return;  // Turn is wasted
        }
    }

    /**
     * Handles the view ability option at the start of each turn.
     * Allows player to view all surfaces of a selected box.
     */
    private void handleViewAbility() {
        if (menuHandler.promptViewAbility()) {
            Position pos = menuHandler.promptBoxToView();
            Box box = boxGrid.getBox(pos);
            menuHandler.displayBoxSurfaces(box);
        } else {
            menuHandler.displayMessage("Continuing to the first stage...");
        }
    }

    /**
     * Executes the second stage of a turn: box opening and tool usage.
     * @param turnNumber The current turn number
     * @param rolledBoxes Set of positions that were rolled
     */
    private void executeSecondStage(int turnNumber, Set<Position> rolledBoxes) {
        menuHandler.displayMessage("---> TURN " + turnNumber + " – SECOND STAGE:");
        
        Position boxToOpen = menuHandler.promptBoxToOpen(rolledBoxes);
        Box box = boxGrid.getBox(boxToOpen);
        
        try {
            SpecialTool acquiredTool = box.open();
            menuHandler.displayMessage("The box on location " + boxToOpen.toDisplayString() + 
                " is opened. It contains a SpecialTool --> " + acquiredTool.getToolName());
            
            // Use the tool with generics
            useTool(acquiredTool);
            
        } catch (EmptyBoxException e) {
            menuHandler.displayMessage("BOX IS EMPTY! Continuing to the next turn...");
            return;  // Turn is wasted
        }
    }

    /**
     * Generic method to use any SpecialTool.
     * Delegates to tool's apply() method polymorphically.
     * This satisfies the Generics requirement.
     * @param tool The tool to use
     * @param <T> Type extending SpecialTool
     */
    private <T extends SpecialTool> void useTool(T tool) {
        Position targetPos = menuHandler.promptToolTargetPosition(tool);
        
        try {
            // Polymorphic call - actual behavior depends on tool's runtime type
            tool.apply(boxGrid, gameState.getTargetLetter(), targetPos);
            
            // Display success message based on tool type
            displayToolSuccessMessage(tool, targetPos);
            menuHandler.displayGrid(boxGrid);
            
        } catch (UnmovableFixedBoxException e) {
            menuHandler.displayMessage("HOWEVER, IT IS A FIXED BOX AND CANNOT BE MOVED. Turn wasted.");
        } catch (BoxAlreadyFixedException e) {
            menuHandler.displayMessage("Box is already fixed. Turn wasted.");
        }
    }

    /**
     * Displays a success message based on the tool type used.
     * @param tool The tool that was used
     * @param targetPos The target position
     */
    private void displayToolSuccessMessage(SpecialTool tool, Position targetPos) {
        String toolName = tool.getToolName();
        char targetLetter = gameState.getTargetLetter();
        
        switch (toolName) {
            case "PlusShapeStamp":
                menuHandler.displayMessage("Top sides of the chosen box (" + targetPos.toDisplayString() + 
                    ") and its surrounding boxes have been stamped to letter \"" + targetLetter + 
                    "\". The new state of the box grid:");
                break;
            case "MassRowStamp":
                menuHandler.displayMessage("All boxes in row " + (targetPos.getRow() + 1) + 
                    " have been stamped to letter \"" + targetLetter + 
                    "\". The new state of the box grid:");
                break;
            case "MassColumnStamp":
                menuHandler.displayMessage("All boxes in column " + (targetPos.getColumn() + 1) + 
                    " have been stamped to letter \"" + targetLetter + 
                    "\". The new state of the box grid:");
                break;
            case "BoxFlipper":
                menuHandler.displayMessage("The chosen box on location " + targetPos.toDisplayString() + 
                    " has been flipped upside down. The new state of the box grid:");
                break;
            case "BoxFixer":
                menuHandler.displayMessage("The chosen box on location " + targetPos.toDisplayString() + 
                    " has been fixed. The new state of the box grid:");
                break;
            default:
                menuHandler.displayMessage("Tool applied successfully. The new state of the box grid:");
        }
    }

    /**
     * Displays the final grid state and target letter count.
     */
    private void displayGameResult() {
        menuHandler.displayMessage("");
        menuHandler.displayMessage("******** GAME OVER ********");
        menuHandler.displayMessage("The final state of the box grid:");
        menuHandler.displayGrid(boxGrid);
        
        int targetCount = boxGrid.countTargetLetter(gameState.getTargetLetter());
        menuHandler.displayMessage("THE TOTAL NUMBER OF TARGET LETTER \"" + 
            gameState.getTargetLetter() + "\" IN THE BOX GRID --> " + targetCount);
        
        if (gameState.isGameSuccess()) {
            menuHandler.displayMessage("The game has been SUCCESSFULLY completed!");
        } else {
            menuHandler.displayMessage("The game has ended with FAILURE!");
        }
    }

    /**
     * Checks if any valid moves remain (not all edge boxes are fixed).
     * @return true if at least one movable edge box exists
     */
    private boolean canMakeAnyMove() {
        return boxGrid.hasAnyMovableEdgeBox();
    }

    /**
     * Inner class for handling all user interactions, input parsing, and menu display.
     * Has access to outer class fields directly.
     */
    private class MenuHandler {
        
        /**
         * Prompts user for edge box position and validates input.
         * Re-prompts on invalid input.
         * @return The valid edge position
         */
        public Position promptEdgeBoxSelection() {
            while (true) {
                System.out.print("Please enter the location of the edge box you want to roll: ");
                String input = scanner.nextLine().trim();
                
                try {
                    Position pos = Position.fromString(input);
                    
                    // Check if position is on the edge
                    if (!boxGrid.isEdgePosition(pos)) {
                        System.out.print("INCORRECT INPUT: The chosen box is not on any of the edges. Please reenter the location: ");
                        continue;
                    }
                    
                    return pos;
                    
                } catch (InvalidPositionException e) {
                    System.out.print("INCORRECT INPUT: Invalid format. Please reenter the location: ");
                }
            }
        }

        /**
         * Prompts for roll direction based on edge position.
         * For corner boxes: prompts user to choose between two directions.
         * For edge (non-corner) boxes: returns the only valid direction.
         * @param pos The edge position
         * @return The chosen direction
         */
        public Direction promptRollDirection(Position pos) {
            Direction[] validDirs = InputValidator.getValidDirectionsForEdge(pos);
            
            if (validDirs.length == 1) {
                // Non-corner edge: only one direction possible
                displayMessage("The chosen box is automatically rolled to " + 
                    directionToString(validDirs[0]) + ".");
                return validDirs[0];
            }
            
            // Corner: two directions possible
            System.out.println("The chosen box can be rolled to either [1] " + 
                directionToString(validDirs[0]) + " or [2] " + 
                directionToString(validDirs[1]) + ": ");
            
            while (true) {
                String input = scanner.nextLine().trim();
                if (input.equals("1")) return validDirs[0];
                if (input.equals("2")) return validDirs[1];
                System.out.print("Invalid choice. Please enter 1 or 2: ");
            }
        }

        /**
         * Converts a Direction to a display string.
         * @param dir The direction
         * @return The display string (e.g., "right", "downwards")
         */
        private String directionToString(Direction dir) {
            switch (dir) {
                case UP: return "upwards";
                case DOWN: return "downwards";
                case LEFT: return "left";
                case RIGHT: return "right";
                default: return dir.toString().toLowerCase();
            }
        }

        /**
         * Prompts user to select a box from the rolled boxes set.
         * @param rolledBoxes The set of positions that were rolled
         * @return The valid selected position
         */
        public Position promptBoxToOpen(Set<Position> rolledBoxes) {
            while (true) {
                System.out.print("Please enter the location of the box you want to open: ");
                String input = scanner.nextLine().trim();
                
                try {
                    Position pos = Position.fromString(input);
                    
                    // Check if the position was rolled in the first stage
                    if (!rolledBoxes.contains(pos)) {
                        System.out.print("INCORRECT INPUT: The chosen box was not rolled during the first stage. Please reenter the location: ");
                        continue;
                    }
                    
                    return pos;
                    
                } catch (InvalidPositionException e) {
                    System.out.print("INCORRECT INPUT: Invalid format. Please reenter the location: ");
                }
            }
        }

        /**
         * Prompts for position based on tool type requirements.
         * @param tool The tool being used
         * @return The target position for the tool
         */
        public Position promptToolTargetPosition(SpecialTool tool) {
            System.out.print("Please enter the location of the box to use this SpecialTool: ");
            
            // Special handling for row/column stamps
            if (tool instanceof MassRowStamp) {
                return promptRowAsPosition();
            } else if (tool instanceof MassColumnStamp) {
                return promptColumnAsPosition();
            }
            
            // Default: prompt for a specific box position
            while (true) {
                String input = scanner.nextLine().trim();
                
                try {
                    return Position.fromString(input);
                } catch (InvalidPositionException e) {
                    System.out.print("INCORRECT INPUT: Invalid format. Please reenter: ");
                }
            }
        }

        /**
         * Prompts for row number and returns a Position with that row.
         * @return Position with the selected row (column 0)
         */
        private Position promptRowAsPosition() {
            while (true) {
                String input = scanner.nextLine().trim();
                
                try {
                    int row = Integer.parseInt(input);
                    if (InputValidator.isValidRowNumber(row)) {
                        return new Position(row - 1, 0);  // Convert to 0-based
                    }
                    System.out.print("INCORRECT INPUT: Row must be 1-8. Please reenter: ");
                } catch (NumberFormatException e) {
                    System.out.print("INCORRECT INPUT: Invalid number. Please reenter: ");
                }
            }
        }

        /**
         * Prompts for column number and returns a Position with that column.
         * @return Position with the selected column (row 0)
         */
        private Position promptColumnAsPosition() {
            while (true) {
                String input = scanner.nextLine().trim();
                
                try {
                    int col = Integer.parseInt(input);
                    if (InputValidator.isValidColumnNumber(col)) {
                        return new Position(0, col - 1);  // Convert to 0-based
                    }
                    System.out.print("INCORRECT INPUT: Column must be 1-8. Please reenter: ");
                } catch (NumberFormatException e) {
                    System.out.print("INCORRECT INPUT: Invalid number. Please reenter: ");
                }
            }
        }

        /**
         * Prompts for row selection (1-8).
         * @return The selected row number (1-based)
         */
        public int promptRowSelection() {
            System.out.print("Please select a row (1-8): ");
            
            while (true) {
                String input = scanner.nextLine().trim();
                
                try {
                    int row = Integer.parseInt(input);
                    if (InputValidator.isValidRowNumber(row)) {
                        return row;
                    }
                    System.out.print("INCORRECT INPUT: Row must be 1-8. Please reenter: ");
                } catch (NumberFormatException e) {
                    System.out.print("INCORRECT INPUT: Invalid number. Please reenter: ");
                }
            }
        }

        /**
         * Prompts for column selection (1-8).
         * @return The selected column number (1-based)
         */
        public int promptColumnSelection() {
            System.out.print("Please select a column (1-8): ");
            
            while (true) {
                String input = scanner.nextLine().trim();
                
                try {
                    int col = Integer.parseInt(input);
                    if (InputValidator.isValidColumnNumber(col)) {
                        return col;
                    }
                    System.out.print("INCORRECT INPUT: Column must be 1-8. Please reenter: ");
                } catch (NumberFormatException e) {
                    System.out.print("INCORRECT INPUT: Invalid number. Please reenter: ");
                }
            }
        }

        /**
         * Asks if player wants to use view ability.
         * @return true if player wants to view a box
         */
        public boolean promptViewAbility() {
            System.out.print("---> Do you want to view all surfaces of a box? [1] Yes or [2] No? ");
            
            while (true) {
                String input = scanner.nextLine().trim();
                if (input.equals("1")) return true;
                if (input.equals("2")) return false;
                System.out.print("Invalid choice. Please enter 1 or 2: ");
            }
        }

        /**
         * Prompts for box position to view.
         * @return The position to view
         */
        public Position promptBoxToView() {
            while (true) {
                System.out.print("Please enter the location of the box you want to view: ");
                String input = scanner.nextLine().trim();
                
                try {
                    return Position.fromString(input);
                } catch (InvalidPositionException e) {
                    System.out.print("INCORRECT INPUT: Invalid format. Please reenter: ");
                }
            }
        }

        /**
         * Displays the current state of the box grid.
         * @param grid The BoxGrid to display
         */
        public void displayGrid(BoxGrid grid) {
            System.out.println(GridDisplayFormatter.formatGrid(grid));
        }

        /**
         * Displays the cube diagram of a box's 6 surfaces.
         * @param box The box to display
         */
        public void displayBoxSurfaces(Box box) {
            System.out.println(box.getSurfaces().toCubeDiagram());
        }

        /**
         * Utility method for consistent message display.
         * @param message The message to display
         */
        public void displayMessage(String message) {
            System.out.println(message);
        }
    }
}
