package com.rtbanalytica.satellite.validator;

import com.rtbanalytica.satellite.model.BaseEntity;

/**
 * Validator interface for validating the whole base entity
 *
 * @author Venkatesh K Y
 * @since 31 May 2024
 * */
public interface BaseEntityValidator extends Validator {

    /**
     * Method to validate the base entity
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param baseEntity - BaseEntity
     * @throws Exception - Throws exception
     * */
    void validate(BaseEntity baseEntity) throws Exception;
}
