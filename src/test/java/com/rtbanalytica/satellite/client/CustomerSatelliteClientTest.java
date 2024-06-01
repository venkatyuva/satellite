package com.rtbanalytica.satellite.client;

import com.rtbanalytica.satellite.dto.CustomerSatelliteResponse;
import com.rtbanalytica.satellite.exception.EntityNotFoundException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
public class CustomerSatelliteClientTest {

    @InjectMocks
    private CustomerSatelliteClient customerSatelliteClient;

    @Mock
    private RestTemplate restTemplate;


    @Test
    public void testFetchCustomerSatellites() throws EntityNotFoundException {
        String path = "${CUSTOMER_SATELLITE_PATH}";
        ReflectionTestUtils.setField(customerSatelliteClient, "path", path);
        CustomerSatelliteResponse customerSatelliteResponse = Mockito.mock(CustomerSatelliteResponse.class);
        Mockito.when(restTemplate.getForObject(path, CustomerSatelliteResponse.class))
                .thenReturn(customerSatelliteResponse);
        Assert.assertEquals("Verify same object", customerSatelliteResponse,
                customerSatelliteClient.fetchCustomerSatellites());

        Mockito.when(restTemplate.getForObject(path, CustomerSatelliteResponse.class)).thenThrow(new RuntimeException());
        Assert.assertThrows("Entity Not Found Exception", EntityNotFoundException.class,
                () -> customerSatelliteClient.fetchCustomerSatellites());
    }
}