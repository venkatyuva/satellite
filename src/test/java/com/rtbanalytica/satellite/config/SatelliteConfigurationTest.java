package com.rtbanalytica.satellite.config;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SatelliteConfigurationTest {

    @InjectMocks
    private SatelliteConfiguration satelliteConfiguration;

    @Test
    public void testRestTemplate() {
        Assert.assertNotNull("Rest template should not be null",
                satelliteConfiguration.restTemplate());
    }
}