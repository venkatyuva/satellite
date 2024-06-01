package com.rtbanalytica.satellite.client;

import com.rtbanalytica.satellite.dto.LauncherResponse;
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
public class LaunchersClientTest {

    @InjectMocks
    private LaunchersClient launchersClient;

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void testFetchLaunchers() throws EntityNotFoundException {
        String path = "${LAUNCHERS_PATH}";
        ReflectionTestUtils.setField(launchersClient, "path", path);
        LauncherResponse launcherResponse = Mockito.mock(LauncherResponse.class);
        Mockito.when(restTemplate.getForObject(path, LauncherResponse.class))
                .thenReturn(launcherResponse);
        Assert.assertEquals("Verify same object", launcherResponse,
                launchersClient.fetchLaunchers());

        Mockito.when(restTemplate.getForObject(path, LauncherResponse.class)).thenThrow(new RuntimeException());
        Assert.assertThrows("Entity Not Found Exception", EntityNotFoundException.class,
                () -> launchersClient.fetchLaunchers());
    }
}