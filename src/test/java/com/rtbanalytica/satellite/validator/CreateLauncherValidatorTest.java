package com.rtbanalytica.satellite.validator;

import com.rtbanalytica.satellite.exception.EmptyIdException;
import com.rtbanalytica.satellite.exception.EntityAlreadyFoundException;
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
public class CreateLauncherValidatorTest {

    @InjectMocks
    private CreateLauncherValidator createLauncherValidator;

    @Mock
    private LauncherRepository launcherRepository;

    @Test
    public void testValidate() throws Exception {
        Launcher launcher = Mockito.mock(Launcher.class);
        Assert.assertThrows("Empty id exception", EmptyIdException.class,
                () -> createLauncherValidator.validate(launcher));
        Mockito.when(launcher.getId()).thenReturn("");
        Assert.assertThrows("Empty id exception", EmptyIdException.class,
                () -> createLauncherValidator.validate(launcher));
        Mockito.when(launcher.getId()).thenReturn("PSLV-01");
        Mockito.when(launcherRepository.findById(launcher.getId())).thenReturn(Optional.of(launcher));
        Assert.assertThrows("Entity already found exception", EntityAlreadyFoundException.class,
                () -> createLauncherValidator.validate(launcher));
        Mockito.when(launcherRepository.findById(launcher.getId())).thenReturn(Optional.empty());
        createLauncherValidator.validate(launcher);
    }
}