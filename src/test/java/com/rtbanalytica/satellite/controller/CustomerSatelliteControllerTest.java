package com.rtbanalytica.satellite.controller;

import com.rtbanalytica.satellite.constants.SatelliteConstants;
import com.rtbanalytica.satellite.dto.ApiResponse;
import com.rtbanalytica.satellite.exception.CreateCustomerSatelliteException;
import com.rtbanalytica.satellite.exception.DeleteCustomerSatelliteException;
import com.rtbanalytica.satellite.exception.EntityNotFoundException;
import com.rtbanalytica.satellite.exception.UpdateCustomerSatelliteException;
import com.rtbanalytica.satellite.model.CustomerSatellite;
import com.rtbanalytica.satellite.service.CustomerSatelliteService;
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
public class CustomerSatelliteControllerTest {

    @InjectMocks
    private CustomerSatelliteController customerSatelliteController;

    @Mock
    private CustomerSatelliteService customerSatelliteService;

    @Test
    public void testFetchCustomerSatellites() {
        CustomerSatellite customerSatellite = Mockito.mock(CustomerSatellite.class);
        List<CustomerSatellite> customerSatellites = new ArrayList<>();
        customerSatellites.add(customerSatellite);
        Mockito.when(customerSatelliteService.fetchCustomerSatellites(Mockito.any())).thenReturn(customerSatellites);
        ApiResponse<List<CustomerSatellite>> response = customerSatelliteController
                .fetchCustomerSatellites(Mockito.mock(HttpServletRequest.class), SatelliteConstants.ID,
                        SatelliteConstants.FROM_DATE, SatelliteConstants.TO_DATE, SatelliteConstants.COUNTRY,
                        SatelliteConstants.LAUNCHER_ID, SatelliteConstants.MASS).getBody();
        Assert.assertNotNull("Verify response not null", response);
        Assert.assertEquals("Verify customer satellites", customerSatellites, response.getData());

        String message = "Date parsing error";
        Mockito.when(customerSatelliteService.fetchCustomerSatellites(Mockito.any()))
                .thenThrow(new RuntimeException(message));
        response = customerSatelliteController
                .fetchCustomerSatellites(Mockito.mock(HttpServletRequest.class), SatelliteConstants.ID,
                        SatelliteConstants.FROM_DATE, SatelliteConstants.TO_DATE, SatelliteConstants.COUNTRY,
                        SatelliteConstants.LAUNCHER_ID, SatelliteConstants.MASS).getBody();
        Assert.assertNotNull("Verify response not null", response);
        Assert.assertEquals("Verify error customer satellites", message, response.getMessage());
    }

    @Test
    public void testFetchCustomerSatellite() {
        CustomerSatellite customerSatellite = Mockito.mock(CustomerSatellite.class);
        String customerSatelliteId = "CUST-01";
        Mockito.when(customerSatelliteService.fetchCustomerSatellite(customerSatelliteId))
                .thenReturn(Optional.of(customerSatellite));
        ApiResponse<CustomerSatellite> response = customerSatelliteController.fetchCustomerSatellite(
                Mockito.mock(HttpServletRequest.class), customerSatelliteId).getBody();
        Assert.assertNotNull("Verify response not null", response);
        Assert.assertEquals("Verify customer satellite", customerSatellite, response.getData());
        Mockito.when(customerSatelliteService.fetchCustomerSatellite(customerSatelliteId))
                .thenReturn(Optional.empty());
        response = customerSatelliteController.fetchCustomerSatellite(
                Mockito.mock(HttpServletRequest.class), customerSatelliteId).getBody();
        Assert.assertNotNull("Verify response not null", response);
        Assert.assertEquals("Verify error customer satellite",
                "No customer satellite found", response.getMessage());
    }

    @Test
    public void loadCustomerSatellites() throws EntityNotFoundException {
        CustomerSatellite customerSatellite = Mockito.mock(CustomerSatellite.class);
        List<CustomerSatellite> customerSatellites = new ArrayList<>();
        customerSatellites.add(customerSatellite);
        Mockito.when(customerSatelliteService.loadCustomerSatellites()).thenReturn(Optional.of(customerSatellites));
        ApiResponse<List<CustomerSatellite>> response = customerSatelliteController.loadCustomerSatellites(
                Mockito.mock(HttpServletRequest.class)).getBody();
        Assert.assertNotNull("Verify response not null", response);
        Assert.assertEquals("Verify customer satellites", customerSatellites, response.getData());

        Mockito.when(customerSatelliteService.loadCustomerSatellites()).thenReturn(Optional.empty());
        response = customerSatelliteController.loadCustomerSatellites(
                Mockito.mock(HttpServletRequest.class)).getBody();
        Assert.assertNotNull("Verify response not null", response);
        Assert.assertEquals("Verify error customer satellite",
                "No customer satellites found", response.getMessage());
        Mockito.when(customerSatelliteService.loadCustomerSatellites()).thenThrow(EntityNotFoundException.class);
        response = customerSatelliteController.loadCustomerSatellites(
                Mockito.mock(HttpServletRequest.class)).getBody();
        Assert.assertNotNull("Verify response not null", response);
        Assert.assertNull("Empty object", response.getData());
    }

    @Test
    public void testCreateCustomerSatellites() throws CreateCustomerSatelliteException {
        CustomerSatellite customerSatellite = Mockito.mock(CustomerSatellite.class);
        List<CustomerSatellite> customerSatellites = new ArrayList<>();
        customerSatellites.add(customerSatellite);
        Mockito.when(customerSatelliteService.createCustomerSatellites(customerSatellites))
                .thenReturn(customerSatellites);
        ApiResponse<List<CustomerSatellite>> response = customerSatelliteController
                .createCustomerSatellites(Mockito.mock(HttpServletRequest.class), customerSatellites).getBody();
        Assert.assertNotNull("Verify response not null", response);
        Assert.assertEquals("Verify customer satellites", customerSatellites, response.getData());
        String message = "Error creating customer satellites";
        Mockito.when(customerSatelliteService.createCustomerSatellites(customerSatellites))
                .thenThrow(new CreateCustomerSatelliteException(message));
        response = customerSatelliteController
                .createCustomerSatellites(Mockito.mock(HttpServletRequest.class), customerSatellites).getBody();
        Assert.assertNotNull("Verify response not null", response);
        Assert.assertEquals("Verify error customer satellites", message, response.getMessage());
        Assert.assertEquals("Verify error customer satellites", customerSatellites, response.getData());
    }

    @Test
    public void testCreateCustomerSatellite() throws CreateCustomerSatelliteException {
        CustomerSatellite customerSatellite = Mockito.mock(CustomerSatellite.class);
        Mockito.when(customerSatelliteService.createCustomerSatellite(customerSatellite))
                .thenReturn(customerSatellite);
        ApiResponse<CustomerSatellite> response = customerSatelliteController.createCustomerSatellite(
                Mockito.mock(HttpServletRequest.class), customerSatellite).getBody();
        Assert.assertNotNull("Verify response not null", response);
        Assert.assertEquals("Verify customer satellite", customerSatellite, response.getData());

        String message = "Error creating customer satellite";
        Mockito.when(customerSatelliteService.createCustomerSatellite(customerSatellite))
                .thenThrow(new CreateCustomerSatelliteException(message));
        response = customerSatelliteController.createCustomerSatellite(
                Mockito.mock(HttpServletRequest.class), customerSatellite).getBody();
        Assert.assertNotNull("Verify response not null", response);
        Assert.assertEquals("Verify error customer satellite", message, response.getMessage());
        Assert.assertEquals("Verify error customer satellite", customerSatellite, response.getData());
    }

    @Test
    public void testUpdateCustomerSatellites() throws UpdateCustomerSatelliteException {
        CustomerSatellite customerSatellite = Mockito.mock(CustomerSatellite.class);
        List<CustomerSatellite> customerSatellites = new ArrayList<>();
        customerSatellites.add(customerSatellite);
        Mockito.when(customerSatelliteService.updateCustomerSatellites(customerSatellites))
                .thenReturn(customerSatellites);
        ApiResponse<List<CustomerSatellite>> response = customerSatelliteController.updateCustomerSatellites(
                Mockito.mock(HttpServletRequest.class), customerSatellites).getBody();
        Assert.assertNotNull("Verify response not null", response);
        Assert.assertEquals("Verify customer satellite", customerSatellites, response.getData());

        String message = "Error updating customer satellites";
        Mockito.when(customerSatelliteService.updateCustomerSatellites(customerSatellites))
                .thenThrow(new UpdateCustomerSatelliteException(message));
        response = customerSatelliteController.updateCustomerSatellites(
                Mockito.mock(HttpServletRequest.class), customerSatellites).getBody();
        Assert.assertNotNull("Verify response not null", response);
        Assert.assertEquals("Verify error customer satellite", message, response.getMessage());
        Assert.assertEquals("Verify error customer satellite", customerSatellites, response.getData());
    }

    @Test
    public void testUpdateCustomerSatellite() throws UpdateCustomerSatelliteException {
        String customerSatelliteId = "CUST-01";
        CustomerSatellite customerSatellite = Mockito.mock(CustomerSatellite.class);
        Mockito.when(customerSatelliteService.updateCustomerSatellite(
                customerSatelliteId, customerSatellite)).thenReturn(customerSatellite);
        ApiResponse<CustomerSatellite> response = customerSatelliteController.updateCustomerSatellite(
                Mockito.mock(HttpServletRequest.class), customerSatelliteId, customerSatellite).getBody();
        Assert.assertNotNull("Verify response not null", response);
        Assert.assertEquals("Verify customer satellite", customerSatellite, response.getData());

        String message = "Error updating customer satellite";
        Mockito.when(customerSatelliteService.updateCustomerSatellite(
                customerSatelliteId, customerSatellite)).thenThrow(new UpdateCustomerSatelliteException(message));
        response = customerSatelliteController.updateCustomerSatellite(
                Mockito.mock(HttpServletRequest.class), customerSatelliteId, customerSatellite).getBody();
        Assert.assertNotNull("Verify response not null", response);
        Assert.assertEquals("Verify error customer satellite", message, response.getMessage());
        Assert.assertEquals("Verify error customer satellite", customerSatellite, response.getData());
    }

    @Test
    public void testDeleteCustomerSatellites() throws DeleteCustomerSatelliteException {
        String customerSatelliteId = "CUST-01";
        List<String> customerSatelliteIds = new ArrayList<>();
        customerSatelliteIds.add(customerSatelliteId);
        Mockito.doAnswer(invocationOnMock -> null).when(customerSatelliteService)
                .deleteCustomerSatellites(customerSatelliteIds);
        ApiResponse<String> response = customerSatelliteController.deleteCustomerSatellites(
                Mockito.mock(HttpServletRequest.class), customerSatelliteIds).getBody();
        Assert.assertNotNull("Verify response not null", response);
        Assert.assertEquals("Verify message", "Success", response.getMessage());

        String message = "Error deleting customer satellites";
        Mockito.doThrow(new DeleteCustomerSatelliteException(message)).when(customerSatelliteService)
                .deleteCustomerSatellites(customerSatelliteIds);
        response = customerSatelliteController.deleteCustomerSatellites(
                Mockito.mock(HttpServletRequest.class), customerSatelliteIds).getBody();
        Assert.assertNotNull("Verify response not null", response);
        Assert.assertEquals("Verify error message", message, response.getMessage());
    }

    @Test
    public void testDeleteCustomerSatellite() throws DeleteCustomerSatelliteException {
        String customerSatelliteId = "CUST-01";
        Mockito.doAnswer(invocationOnMock -> null).when(customerSatelliteService)
                .deleteCustomerSatellite(customerSatelliteId);
        ApiResponse<String> response = customerSatelliteController.deleteCustomerSatellite(
                Mockito.mock(HttpServletRequest.class), customerSatelliteId).getBody();
        Assert.assertNotNull("Verify response not null", response);
        Assert.assertEquals("Verify message", "Success", response.getMessage());

        String message = "Error deleting customer satellite";
        Mockito.doThrow(new DeleteCustomerSatelliteException(message)).when(customerSatelliteService)
                .deleteCustomerSatellite(customerSatelliteId);
        response = customerSatelliteController.deleteCustomerSatellite(
                Mockito.mock(HttpServletRequest.class), customerSatelliteId).getBody();
        Assert.assertNotNull("Verify response not null", response);
        Assert.assertEquals("Verify error message", message, response.getMessage());
    }
}