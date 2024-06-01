package com.rtbanalytica.satellite.validator;

/**
 * Validator interface for validating the id of base entity
 *
 * @author Venkatesh K Y
 * @since 31 May 2024
 * */
public interface IdValidator extends Validator {

    /**
     * Method to validate the id of the base entity
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param id - String
     * @throws Exception - Throws exception
     * */
    void validate(String id) throws Exception;
}
