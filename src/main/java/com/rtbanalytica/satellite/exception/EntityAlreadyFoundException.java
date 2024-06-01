package com.rtbanalytica.satellite.exception;

/**
 * Exception class for entity already found
 *
 * @author Venkatesh K Y
 * @since 31 May 2024
 * */
public class EntityAlreadyFoundException extends Exception {
    public EntityAlreadyFoundException(String message) {
        super(message);
    }
}
