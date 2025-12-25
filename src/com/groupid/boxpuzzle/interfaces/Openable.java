package com.groupid.boxpuzzle.interfaces;

import com.groupid.boxpuzzle.exceptions.EmptyBoxException;
import com.groupid.boxpuzzle.model.tool.SpecialTool;

/**
 * Interface for objects that can be opened to retrieve contents.
 * Implemented by Box and its subclasses.
 */
public interface Openable {
    
    /**
     * Opens the object and returns its contents.
     * @return The SpecialTool contained in the object
     * @throws EmptyBoxException if the object contains no tool
     */
    SpecialTool open() throws EmptyBoxException;
    
    /**
     * Checks if the object has already been opened.
     * @return true if already opened, false otherwise
     */
    boolean hasBeenOpened();
    
    /**
     * Checks if the object contains no tool.
     * @return true if the object is empty, false otherwise
     */
    boolean isEmpty();
}
