package com.rtbanalytica.satellite.exception;

/**
 * Exception class for entity already in use
 *
 * @author Venkatesh K Y
 * @since 31 May 2024
 * */
public class EntityAlreadyInUseException extends Exception {
    public EntityAlreadyInUseException(String message) {
        super(message);
    }
}
