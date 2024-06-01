package com.rtbanalytica.satellite.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO class for the response of
 * customer satellite third party API
 *
 * @author Venkatesh K Y
 * @since 31 May 2024
 * */
@Getter
@Setter
@Builder
public class CustomerSatelliteDto {
    private String id;
    private String country;
    @JsonProperty("launch_date")
    private String launchDate;
    private double mass;
    private String launcher;
}
