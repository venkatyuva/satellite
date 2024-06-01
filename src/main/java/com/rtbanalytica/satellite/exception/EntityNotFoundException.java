package com.rtbanalytica.satellite.exception;

/**
 * Exception class for entity not found
 *
 * @author Venkatesh K Y
 * @since 31 May 2024
 * */
public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
