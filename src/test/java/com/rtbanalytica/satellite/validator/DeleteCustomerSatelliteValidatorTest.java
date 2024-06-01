package com.rtbanalytica.satellite.validator;

import com.rtbanalytica.satellite.exception.EmptyIdException;
import com.rtbanalytica.satellite.exception.EntityNotFoundException;
import com.rtbanalytica.satellite.model.CustomerSatellite;
import com.rtbanalytica.satellite.repository.CustomerSatelliteRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class DeleteCustomerSatelliteValidatorTest {

    @InjectMocks
    private DeleteCustomerSatelliteValidator deleteCustomerSatelliteValidator;

    @Mock
    private CustomerSatelliteRepository customerSatelliteRepository;

    @Test
    public void testValidate() throws Exception {
        Assert.assertThrows("Empty id exception", EmptyIdException.class,
                () -> deleteCustomerSatelliteValidator.validate(null));
        Assert.assertThrows("Empty id exception", EmptyIdException.class,
                () -> deleteCustomerSatelliteValidator.validate(""));
        String customerSatelliteId = "CUST-01";
        Mockito.when(customerSatelliteRepository.findById(customerSatelliteId)).thenReturn(Optional.empty());
        Assert.assertThrows("Entity not exception", EntityNotFoundException.class,
                () -> deleteCustomerSatelliteValidator.validate(customerSatelliteId));
        CustomerSatellite customerSatellite = Mockito.mock(CustomerSatellite.class);
        Mockito.when(customerSatelliteRepository.findById(customerSatelliteId)).thenReturn(
                Optional.of(customerSatellite));
        deleteCustomerSatelliteValidator.validate(customerSatelliteId);
    }
}