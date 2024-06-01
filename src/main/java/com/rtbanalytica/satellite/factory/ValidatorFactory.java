package com.rtbanalytica.satellite.factory;

import com.rtbanalytica.satellite.constants.SatelliteConstants;
import com.rtbanalytica.satellite.enums.Entity;
import com.rtbanalytica.satellite.enums.ValidatorType;
import com.rtbanalytica.satellite.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Factory class for create instance of validator files.
 *
 * @author Venkatesh K Y
 * @since 31 May 2024
 * */
@Component
public class ValidatorFactory {

    private static final Logger logger = Logger.getLogger(ValidatorFactory.class.getName());

    @Autowired
    private Map<String, Validator> validators;

    private ValidatorFactory() {
    }

    /**
     * Method to instantiate the validator class
     * based on entity and validator type.
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param entity - Entity
     * @param validatorType - ValidatorType
     * @return Validator - The respective instance of
     * validator based on entity and validator type.
     * */
    public Validator getValidatorInstance(Entity entity, ValidatorType validatorType) {
        String validatorName = validatorType.getValue() + entity.getValue() + SatelliteConstants.VALIDATOR;
        logger.log(Level.INFO, "Creating instance for validator: " + validatorName);
        return validators.get(validatorName);
    }
}
