package com.rtbanalytica.satellite.controller;

import com.rtbanalytica.satellite.dto.ApiResponse;
import com.rtbanalytica.satellite.exception.*;
import com.rtbanalytica.satellite.model.Launcher;
import com.rtbanalytica.satellite.service.LauncherService;
import jakarta.servlet.http.HttpServletRequest;
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
public class LauncherControllerTest {

    @InjectMocks
    private LauncherController launcherController;

    @Mock
    private LauncherService launcherService;

    @Test
    public void testFetchLaunchers() {
        Launcher launcher = Mockito.mock(Launcher.class);
        List<Launcher> launchers = new ArrayList<>();
        launchers.add(launcher);
        Mockito.when(launcherService.fetchLaunchers()).thenReturn(launchers);
        ApiResponse<List<Launcher>> response = launcherController
                .fetchLaunchers(Mockito.mock(HttpServletRequest.class)).getBody();
        Assert.assertNotNull("Verify response not null", response);
        Assert.assertEquals("Verify launchers", launchers, response.getData());
    }

    @Test
    public void testFetchLauncher() {
        Launcher launcher = Mockito.mock(Launcher.class);
        String launcherId = "PSLV-01";
        Mockito.when(launcherService.fetchLauncher(launcherId))
                .thenReturn(Optional.of(launcher));
        ApiResponse<Launcher> response = launcherController.fetchLauncher(
                Mockito.mock(HttpServletRequest.class), launcherId).getBody();
        Assert.assertNotNull("Verify response not null", response);
        Assert.assertEquals("Verify launcher", launcher, response.getData());
        Mockito.when(launcherService.fetchLauncher(launcherId))
                .thenReturn(Optional.empty());
        response = launcherController.fetchLauncher(
                Mockito.mock(HttpServletRequest.class), launcherId).getBody();
        Assert.assertNotNull("Verify response not null", response);
        Assert.assertEquals("Verify error launcher",
                "No launcher found", response.getMessage());
    }

    @Test
    public void loadLaunchers() throws EntityNotFoundException {
        Launcher launcher = Mockito.mock(Launcher.class);
        List<Launcher> launchers = new ArrayList<>();
        launchers.add(launcher);
        Mockito.when(launcherService.loadLaunchers()).thenReturn(Optional.of(launchers));
        ApiResponse<List<Launcher>> response = launcherController.loadLaunchers(
                Mockito.mock(HttpServletRequest.class)).getBody();
        Assert.assertNotNull("Verify response not null", response);
        Assert.assertEquals("Verify launchers", launchers, response.getData());

        Mockito.when(launcherService.loadLaunchers()).thenReturn(Optional.empty());
        response = launcherController.loadLaunchers(
                Mockito.mock(HttpServletRequest.class)).getBody();
        Assert.assertNotNull("Verify response not null", response);
        Assert.assertEquals("Verify error launcher",
                "No launchers found", response.getMessage());
        Mockito.when(launcherService.loadLaunchers()).thenThrow(EntityNotFoundException.class);
        response = launcherController.loadLaunchers(
                Mockito.mock(HttpServletRequest.class)).getBody();
        Assert.assertNotNull("Verify response not null", response);
        Assert.assertNull("Empty object", response.getData());
    }

    @Test
    public void testCreateLaunchers() throws CreateLauncherException {
        Launcher launcher = Mockito.mock(Launcher.class);
        List<Launcher> launchers = new ArrayList<>();
        launchers.add(launcher);
        Mockito.when(launcherService.createLaunchers(launchers))
                .thenReturn(launchers);
        ApiResponse<List<Launcher>> response = launcherController
                .createLaunchers(Mockito.mock(HttpServletRequest.class), launchers).getBody();
        Assert.assertNotNull("Verify response not null", response);
        Assert.assertEquals("Verify launchers", launchers, response.getData());
        String message = "Error creating launchers";
        Mockito.when(launcherService.createLaunchers(launchers))
                .thenThrow(new CreateLauncherException(message));
        response = launcherController
                .createLaunchers(Mockito.mock(HttpServletRequest.class), launchers).getBody();
        Assert.assertNotNull("Verify response not null", response);
        Assert.assertEquals("Verify error launchers", message, response.getMessage());
        Assert.assertEquals("Verify error launchers", launchers, response.getData());
    }

    @Test
    public void testCreateLauncher() throws CreateLauncherException {
        Launcher launcher = Mockito.mock(Launcher.class);
        Mockito.when(launcherService.createLauncher(launcher))
                .thenReturn(launcher);
        ApiResponse<Launcher> response = launcherController.createLauncher(
                Mockito.mock(HttpServletRequest.class), launcher).getBody();
        Assert.assertNotNull("Verify response not null", response);
        Assert.assertEquals("Verify launcher", launcher, response.getData());

        String message = "Error creating launcher";
        Mockito.when(launcherService.createLauncher(launcher))
                .thenThrow(new CreateLauncherException(message));
        response = launcherController.createLauncher(
                Mockito.mock(HttpServletRequest.class), launcher).getBody();
        Assert.assertNotNull("Verify response not null", response);
        Assert.assertEquals("Verify error launcher", message, response.getMessage());
        Assert.assertEquals("Verify error launcher", launcher, response.getData());
    }

    @Test
    public void testUpdateLaunchers() throws UpdateLauncherException {
        Launcher launcher = Mockito.mock(Launcher.class);
        List<Launcher> launchers = new ArrayList<>();
        launchers.add(launcher);
        Mockito.when(launcherService.updateLaunchers(launchers))
                .thenReturn(launchers);
        ApiResponse<List<Launcher>> response = launcherController.updateLaunchers(
                Mockito.mock(HttpServletRequest.class), launchers).getBody();
        Assert.assertNotNull("Verify response not null", response);
        Assert.assertEquals("Verify launcher", launchers, response.getData());

        String message = "Error updating launchers";
        Mockito.when(launcherService.updateLaunchers(launchers))
                .thenThrow(new UpdateLauncherException(message));
        response = launcherController.updateLaunchers(
                Mockito.mock(HttpServletRequest.class), launchers).getBody();
        Assert.assertNotNull("Verify response not null", response);
        Assert.assertEquals("Verify error launchers", message, response.getMessage());
        Assert.assertEquals("Verify error launchers", launchers, response.getData());
    }

    @Test
    public void testUpdateLauncher() throws UpdateLauncherException {
        String launcherId = "PSLV-01";
        Launcher launcher = Mockito.mock(Launcher.class);
        Mockito.when(launcherService.updateLauncher(
                launcherId, launcher)).thenReturn(launcher);
        ApiResponse<Launcher> response = launcherController.updateLauncher(
                Mockito.mock(HttpServletRequest.class), launcherId, launcher).getBody();
        Assert.assertNotNull("Verify response not null", response);
        Assert.assertEquals("Verify launcher", launcher, response.getData());

        String message = "Error updating launcher";
        Mockito.when(launcherService.updateLauncher(
                launcherId, launcher)).thenThrow(new UpdateLauncherException(message));
        response = launcherController.updateLauncher(
                Mockito.mock(HttpServletRequest.class), launcherId, launcher).getBody();
        Assert.assertNotNull("Verify response not null", response);
        Assert.assertEquals("Verify error launcher", message, response.getMessage());
        Assert.assertEquals("Verify error launcher", launcher, response.getData());
    }

    @Test
    public void testDeleteLaunchers() throws DeleteLauncherException {
        String launcherId = "PSLV-01";
        List<String> launcherIds = new ArrayList<>();
        launcherIds.add(launcherId);
        Mockito.doAnswer(invocationOnMock -> null).when(launcherService)
                .deleteLaunchers(launcherIds);
        ApiResponse<String> response = launcherController.deleteLaunchers(
                Mockito.mock(HttpServletRequest.class), launcherIds).getBody();
        Assert.assertNotNull("Verify response not null", response);
        Assert.assertEquals("Verify message", "Success", response.getMessage());

        String message = "Error deleting launchers";
        Mockito.doThrow(new DeleteLauncherException(message)).when(launcherService)
                .deleteLaunchers(launcherIds);
        response = launcherController.deleteLaunchers(
                Mockito.mock(HttpServletRequest.class), launcherIds).getBody();
        Assert.assertNotNull("Verify response not null", response);
        Assert.assertEquals("Verify error message", message, response.getMessage());
    }

    @Test
    public void testDeleteLauncher() throws DeleteLauncherException {
        String launcherId = "PSLV-01";
        Mockito.doAnswer(invocationOnMock -> null).when(launcherService)
                .deleteLauncher(launcherId);
        ApiResponse<String> response = launcherController.deleteLauncher(
                Mockito.mock(HttpServletRequest.class), launcherId).getBody();
        Assert.assertNotNull("Verify response not null", response);
        Assert.assertEquals("Verify message", "Success", response.getMessage());

        String message = "Error deleting launcher";
        Mockito.doThrow(new DeleteLauncherException(message)).when(launcherService)
                .deleteLauncher(launcherId);
        response = launcherController.deleteLauncher(
                Mockito.mock(HttpServletRequest.class), launcherId).getBody();
        Assert.assertNotNull("Verify response not null", response);
        Assert.assertEquals("Verify error message", message, response.getMessage());
    }
}