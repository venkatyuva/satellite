package com.rtbanalytica.satellite.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


/**
 * Configuration class to generate non spring beans
 * during application startup
 *
 * @author Venkatesh K Y
 * @since 31 May 2024
 * */
@Configuration
public class SatelliteConfiguration {

    /**
     * Method to generate RestTemplate beans
     * during application śtartup
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     * @return RestTemplate - rest template
     * */
    @Bean
    public RestTemplate restTemplate() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        return restTemplateBuilder.build();
    }
}
