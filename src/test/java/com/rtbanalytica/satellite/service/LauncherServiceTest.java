package com.rtbanalytica.satellite.service;

import com.rtbanalytica.satellite.client.LaunchersClient;
import com.rtbanalytica.satellite.dto.LauncherResponse;
import com.rtbanalytica.satellite.enums.Entity;
import com.rtbanalytica.satellite.enums.ValidatorType;
import com.rtbanalytica.satellite.exception.*;
import com.rtbanalytica.satellite.factory.ValidatorFactory;
import com.rtbanalytica.satellite.model.CustomerSatellite;
import com.rtbanalytica.satellite.model.Launcher;
import com.rtbanalytica.satellite.repository.LauncherRepository;
import com.rtbanalytica.satellite.validator.BaseEntityValidator;
import com.rtbanalytica.satellite.validator.IdValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class LauncherServiceTest {

    @InjectMocks
    private LauncherService launcherService;

    @Mock
    private LaunchersClient launchersClient;

    @Mock
    private LauncherRepository launcherRepository;

    @Mock
    private ValidatorFactory validatorFactory;

    @Test
    public void testLoadLaunchers() throws EntityNotFoundException {
        LauncherResponse launcherResponse = Mockito.mock(LauncherResponse.class);
        Mockito.when(launchersClient.fetchLaunchers()).thenReturn(launcherResponse);
        Launcher launcher = Mockito.mock(Launcher.class);
        List<Launcher> launchers = new ArrayList<>();
        launchers.add(launcher);
        Mockito.when(launcherResponse.getLaunchers()).thenReturn(launchers);
        Assert.assertEquals("Non empty launchers", Optional.of(launchers), launcherService.loadLaunchers());
        Mockito.when(launchersClient.fetchLaunchers()).thenThrow(EntityNotFoundException.class);
        Assert.assertThrows("Entity not found exception", EntityNotFoundException.class,
                () -> launcherService.loadLaunchers());
    }

    @Test
    public void testFetchLaunchers() {
        Launcher launcher = Mockito.mock(Launcher.class);
        List<Launcher> launchers = new ArrayList<>();
        launchers.add(launcher);
        Mockito.when(launcherRepository.findAll()).thenReturn(launchers);
        Assert.assertEquals("Verify launchers", launchers, launcherService.fetchLaunchers());
    }

    @Test
    public void testFetchLauncher() {
        Launcher launcher = Mockito.mock(Launcher.class);
        String launcherId = "PLSV-01";
        Mockito.when(launcherRepository.findById(launcherId)).thenReturn(Optional.of(launcher));
        Assert.assertEquals("Verify launchers", Optional.of(launcher),
                launcherService.fetchLauncher(launcherId));
    }

    @Test
    public void testCreateLaunchers() throws CreateLauncherException {
        BaseEntityValidator validator = Mockito.mock(BaseEntityValidator.class);
        Mockito.when(validatorFactory.getValidatorInstance(Entity.LAUNCHER, ValidatorType.CREATE))
                .thenReturn(validator);
        Launcher launcher = Mockito.mock(Launcher.class);
        List<Launcher> launchers = new ArrayList<>();
        launchers.add(launcher);
        Mockito.when(launcherRepository.saveAll(launchers)).thenReturn(launchers);
        Assert.assertEquals("Non empty launchers", launchers, launcherService
                .createLaunchers(launchers));
        Mockito.when(launcherRepository.saveAll(launchers)).thenThrow(new RuntimeException());
        Assert.assertThrows("Create Launcher Exception", CreateLauncherException.class,
                () -> launcherService.createLaunchers(launchers));
    }

    @Test
    public void testCreateLauncher() throws CreateLauncherException {
        BaseEntityValidator validator = Mockito.mock(BaseEntityValidator.class);
        Mockito.when(validatorFactory.getValidatorInstance(Entity.LAUNCHER, ValidatorType.CREATE))
                .thenReturn(validator);
        Launcher launcher = Mockito.mock(Launcher.class);
        Mockito.when(launcherRepository.save(launcher)).thenReturn(launcher);
        Assert.assertEquals("Non empty launcher", launcher,
                launcherService.createLauncher(launcher));
        Mockito.when(launcherRepository.save(launcher))
                .thenThrow(RuntimeException.class);
        Assert.assertThrows("Create Launcher Exception", CreateLauncherException.class,
                () -> launcherService.createLauncher(launcher));
    }

    @Test
    public void testUpdateLaunchers() throws UpdateLauncherException {
        BaseEntityValidator validator = Mockito.mock(BaseEntityValidator.class);
        Mockito.when(validatorFactory.getValidatorInstance(Entity.LAUNCHER, ValidatorType.UPDATE))
                .thenReturn(validator);
        Launcher launcher = Mockito.mock(Launcher.class);
        List<Launcher> launchers = new ArrayList<>();
        launchers.add(launcher);
        Mockito.when(launcherRepository.saveAll(launchers)).thenReturn(launchers);
        Assert.assertEquals("Non empty launchers", launchers,
                launcherService.updateLaunchers(launchers));
        Mockito.when(launcherRepository.saveAll(launchers)).thenThrow(new RuntimeException());
        Assert.assertThrows("Update Launcher Exception", UpdateLauncherException.class,
                () -> launcherService.updateLaunchers(launchers));
    }

    @Test
    public void testUpdateLauncher() throws UpdateLauncherException {
        BaseEntityValidator validator = Mockito.mock(BaseEntityValidator.class);
        Mockito.when(validatorFactory.getValidatorInstance(Entity.LAUNCHER, ValidatorType.UPDATE))
                .thenReturn(validator);
        Launcher launcher = Mockito.mock(Launcher.class);
        Mockito.when(launcherRepository.save(launcher)).thenReturn(launcher);
        String launcherId = "PSLV-01";
        Assert.assertEquals("Non empty launcher", launcher,
                launcherService.updateLauncher(launcherId, launcher));
        Mockito.when(launcherRepository.save(launcher)).thenThrow(new RuntimeException());
        Assert.assertThrows("Update Launcher Exception", UpdateLauncherException.class,
                () -> launcherService.updateLauncher(launcherId, launcher));
    }

    @Test
    public void testDeleteLaunchers() throws DeleteLauncherException {
        IdValidator validator = Mockito.mock(IdValidator.class);
        Mockito.when(validatorFactory.getValidatorInstance(Entity.LAUNCHER, ValidatorType.DELETE))
                .thenReturn(validator);
        String launcherId = "PSLV-01";
        List<String> launcherIds = new ArrayList<>();
        launcherIds.add(launcherId);
        launcherService.deleteLaunchers(launcherIds);
        Mockito.doThrow(RuntimeException.class).when(launcherRepository).deleteAllById(launcherIds);
        Assert.assertThrows("Delete Launcher Exception", DeleteLauncherException.class,
                () -> launcherService.deleteLaunchers(launcherIds));
    }

    @Test
    public void testDeleteLauncher() throws DeleteLauncherException {
        IdValidator validator = Mockito.mock(IdValidator.class);
        Mockito.when(validatorFactory.getValidatorInstance(Entity.LAUNCHER, ValidatorType.DELETE))
                .thenReturn(validator);
        String launcherId = "PSLV-01";
        launcherService.deleteLauncher(launcherId);
        Mockito.doThrow(RuntimeException.class).when(launcherRepository).deleteById(launcherId);
        Assert.assertThrows("Delete Launcher Exception", DeleteLauncherException.class,
                () -> launcherService.deleteLauncher(launcherId));
    }
}