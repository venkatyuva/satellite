package com.rtbanalytica.satellite.factory;

import com.rtbanalytica.satellite.constants.SatelliteConstants;
import com.rtbanalytica.satellite.enums.Entity;
import com.rtbanalytica.satellite.enums.ValidatorType;
import com.rtbanalytica.satellite.validator.CreateLauncherValidator;
import com.rtbanalytica.satellite.validator.Validator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class ValidatorFactoryTest {

    @InjectMocks
    private ValidatorFactory validatorFactory;

    @Test
    public void testGetValidatorInstance() {
        CreateLauncherValidator createLauncherValidator = Mockito.mock(CreateLauncherValidator.class);
        Map<String, Validator> validatorMap = new HashMap<>();
        validatorMap.put(ValidatorType.CREATE.getValue() + Entity.LAUNCHER.getValue() +
                SatelliteConstants.VALIDATOR, createLauncherValidator);
        ReflectionTestUtils.setField(validatorFactory, "validators", validatorMap);
        Assert.assertEquals("Verify validators", createLauncherValidator, validatorFactory
                .getValidatorInstance(Entity.LAUNCHER, ValidatorType.CREATE));
    }
}