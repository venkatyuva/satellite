package com.rtbanalytica.satellite.client;

import com.rtbanalytica.satellite.constants.SatelliteConstants;
import com.rtbanalytica.satellite.dto.LauncherResponse;
import com.rtbanalytica.satellite.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Client class to interact with the third party service
 * for fetching the launchers
 *
 * @author Venkatesh K Y
 * @since 31 May 2024
 * */
@Component
public class LaunchersClient {
    private static final Logger logger = Logger.getLogger(LaunchersClient.class.getName());

    @Autowired
    private RestTemplate restTemplate;


    @Value(SatelliteConstants.LAUNCHERS_URL)
    private String path;

    public LaunchersClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Method to fetch the launchers
     * from the third party API.
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @return LauncherResponse - launcher response
     * @throws EntityNotFoundException - Exception thrown when API is not reachable
     * */
    public LauncherResponse fetchLaunchers() throws EntityNotFoundException {
        try {
            LauncherResponse launcherResponse = restTemplate.getForObject(path, LauncherResponse.class);
            logger.log(Level.INFO, "Fetched launchers successfully");
            return launcherResponse;
        } catch (Exception exception) {
            String errorMessage = "Error fetching launchers from third party API";
            logger.log(Level.SEVERE, errorMessage);
            throw new EntityNotFoundException(exception.getMessage());
        }
    }
}
