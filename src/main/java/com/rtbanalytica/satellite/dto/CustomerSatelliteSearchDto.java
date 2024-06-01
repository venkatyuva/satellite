package com.rtbanalytica.satellite.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rtbanalytica.satellite.constants.SatelliteConstants;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO class for the search criteria of
 * customer satellite
 *
 * @author Venkatesh K Y
 * @since 31 May 2024
 * */
@Getter
@Setter
@Builder
public class CustomerSatelliteSearchDto {
    private String id;
    private String country;
    @JsonProperty(SatelliteConstants.LAUNCH_DATE_VARIABLE)
    @JsonFormat(pattern = SatelliteConstants.DATE_FORMAT)
    private String fromDate;
    @JsonProperty(SatelliteConstants.LAUNCH_DATE_VARIABLE)
    @JsonFormat(pattern = SatelliteConstants.DATE_FORMAT)
    private String toDate;
    private String  mass;
    private String launcher;
}
