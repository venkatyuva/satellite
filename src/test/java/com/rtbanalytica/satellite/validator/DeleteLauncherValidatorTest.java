package com.rtbanalytica.satellite.validator;

import com.rtbanalytica.satellite.exception.EmptyIdException;
import com.rtbanalytica.satellite.exception.EntityAlreadyInUseException;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class DeleteLauncherValidatorTest {

    @InjectMocks
    private DeleteLauncherValidator deleteLauncherValidator;

    @Mock
    private LauncherRepository launcherRepository;

    @Mock
    private CustomerSatelliteRepository customerSatelliteRepository;

    @Test
    public void testValidate() throws Exception {
        Assert.assertThrows("Empty id exception", EmptyIdException.class,
                () -> deleteLauncherValidator.validate(null));
        Assert.assertThrows("Empty id exception", EmptyIdException.class,
                () -> deleteLauncherValidator.validate(""));
        String launcherId = "PSLV-01";
        Mockito.when(launcherRepository.findById(launcherId)).thenReturn(Optional.empty());
        Assert.assertThrows("Entity not exception", EntityNotFoundException.class,
                () -> deleteLauncherValidator.validate(launcherId));
        Launcher launcher = Mockito.mock(Launcher.class);
        Mockito.when(launcherRepository.findById(launcherId)).thenReturn(
                Optional.of(launcher));
        CustomerSatellite customerSatellite = Mockito.mock(CustomerSatellite.class);
        List<CustomerSatellite> customerSatellites = new ArrayList<>();
        customerSatellites.add(customerSatellite);
        Mockito.when(customerSatelliteRepository.findAllByLauncherId(launcherId)).thenReturn(customerSatellites);
        Assert.assertThrows("Entity already in use exception", EntityAlreadyInUseException.class,
                () -> deleteLauncherValidator.validate(launcherId));
        Mockito.when(customerSatelliteRepository.findAllByLauncherId(launcherId)).thenReturn(Collections.emptyList());
        deleteLauncherValidator.validate(launcherId);
    }
}