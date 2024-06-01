package com.rtbanalytica.satellite.validator;

import com.rtbanalytica.satellite.exception.EmptyIdException;
import com.rtbanalytica.satellite.exception.EntityNotFoundException;
import com.rtbanalytica.satellite.model.CustomerSatellite;
import com.rtbanalytica.satellite.model.Launcher;
import com.rtbanalytica.satellite.repository.CustomerSatelliteRepository;
import com.rtbanalytica.satellite.repository.LauncherRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class UpdateCustomerSatelliteValidatorTest {

    @InjectMocks
    private UpdateCustomerSatelliteValidator updateCustomerSatelliteValidator;

    @Mock
    private CustomerSatelliteRepository customerSatelliteRepository;

    @Mock
    private LauncherRepository launcherRepository;

    @Test
    public void testValidate() throws Exception {
        CustomerSatellite customerSatellite = Mockito.mock(CustomerSatellite.class);
        Assert.assertThrows("Empty id exception", EmptyIdException.class,
                () -> updateCustomerSatelliteValidator.validate(customerSatellite));
        Mockito.when(customerSatellite.getId()).thenReturn("");
        Assert.assertThrows("Empty id exception", EmptyIdException.class,
                () -> updateCustomerSatelliteValidator.validate(customerSatellite));
        String customerSatelliteId = "CUST-01";
        Mockito.when(customerSatellite.getId()).thenReturn(customerSatelliteId);
        Mockito.when(customerSatelliteRepository.findById(customerSatellite.getId()))
                .thenReturn(Optional.empty());
        Assert.assertThrows("Entity not found exception", EntityNotFoundException.class,
                () -> updateCustomerSatelliteValidator.validate(customerSatellite));
        Mockito.when(customerSatelliteRepository.findById(customerSatellite.getId()))
                .thenReturn(Optional.of(customerSatellite));
        Launcher launcher = Mockito.mock(Launcher.class);
        Mockito.when(customerSatellite.getLauncher()).thenReturn(launcher);
        String launcherId = "PLSV-01";
        Mockito.when(launcher.getId()).thenReturn(launcherId);
        Mockito.when(launcherRepository.findById(customerSatellite.getLauncher().getId()))
                .thenReturn(Optional.empty());
        Assert.assertThrows("Entity not found exception", EntityNotFoundException.class,
                () -> updateCustomerSatelliteValidator.validate(customerSatellite));
        Mockito.when(launcherRepository.findById(customerSatellite.getLauncher().getId()))
                .thenReturn(Optional.of(launcher));
        updateCustomerSatelliteValidator.validate(customerSatellite);
    }
}