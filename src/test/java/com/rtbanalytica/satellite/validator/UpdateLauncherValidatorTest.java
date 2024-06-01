package com.rtbanalytica.satellite.validator;

import com.rtbanalytica.satellite.exception.EmptyIdException;
import com.rtbanalytica.satellite.exception.EntityNotFoundException;
import com.rtbanalytica.satellite.model.Launcher;
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
public class UpdateLauncherValidatorTest {

    @InjectMocks
    private UpdateLauncherValidator updateLauncherValidator;

    @Mock
    private LauncherRepository launcherRepository;

    @Test
    public void testValidate() throws Exception {
        Launcher launcher = Mockito.mock(Launcher.class);
        Assert.assertThrows("Empty id exception", EmptyIdException.class,
                () -> updateLauncherValidator.validate(launcher));
        Mockito.when(launcher.getId()).thenReturn("");
        Assert.assertThrows("Empty id exception", EmptyIdException.class,
                () -> updateLauncherValidator.validate(launcher));
        String launcherId = "PSLV-01";
        Mockito.when(launcher.getId()).thenReturn(launcherId);
        Mockito.when(launcherRepository.findById(launcher.getId())).thenReturn(Optional.empty());
        Assert.assertThrows("Entity not found exception", EntityNotFoundException.class,
                () -> updateLauncherValidator.validate(launcher));
        Mockito.when(launcherRepository.findById(launcher.getId())).thenReturn(Optional.of(launcher));
        updateLauncherValidator.validate(launcher);
    }
}