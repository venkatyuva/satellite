package com.rtbanalytica.satellite.client;

import com.rtbanalytica.satellite.constants.SatelliteConstants;
import com.rtbanalytica.satellite.dto.CustomerSatelliteResponse;
import com.rtbanalytica.satellite.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Client class to interact with the third party service
 * for fetching the customer satellites
 *
 * @author Venkatesh K Y
 * @since 31 May 2024
 * */
@Component
public class CustomerSatelliteClient {

    private static final Logger logger = Logger.getLogger(CustomerSatelliteClient.class.getName());

    @Autowired
    private RestTemplate restTemplate;

    @Value(SatelliteConstants.CUSTOMER_SATELLITE_URL)
    private String path;

    public CustomerSatelliteClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Method to fetch the customer satellites
     * from the third party API.
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     * @return CustomerSatelliteResponse - customer satellite response
     * @throws EntityNotFoundException - Exception thrown when API is not reachable
     * */
    public CustomerSatelliteResponse fetchCustomerSatellites() throws EntityNotFoundException {
        try {
            CustomerSatelliteResponse customerSatelliteResponse = restTemplate
                    .getForObject(path, CustomerSatelliteResponse.class);
            logger.log(Level.INFO, "Fetched Customer Satellites successfully");
            return customerSatelliteResponse;
        } catch (Exception ex) {
            String errorMessage = "Error fetching customer satellites from third party API";
            logger.log(Level.SEVERE, errorMessage);
            throw new EntityNotFoundException(ex.getMessage());
        }
    }
}
