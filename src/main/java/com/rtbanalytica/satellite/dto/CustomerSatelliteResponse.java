package com.rtbanalytica.satellite.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * DTO class for the response of list of
 * customer satellite third party API
 *
 * @author Venkatesh K Y
 * @since 31 May 2024
 * */
@Getter
@Setter
public class CustomerSatelliteResponse {
    @JsonProperty("customer_satellites")
    private List<CustomerSatelliteDto> customerSatellites;
}
