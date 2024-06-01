package com.rtbanalytica.satellite.mappers;

import com.rtbanalytica.satellite.constants.SatelliteConstants;
import com.rtbanalytica.satellite.dto.CustomerSatelliteDto;
import com.rtbanalytica.satellite.model.CustomerSatellite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.stereotype.Component;

/**
 * Mapper class for mapping between two objects.
 *
 * @author Venkatesh K Y
 * @since 31 May 2024
 * */
@Component
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerSatelliteMapper {

    /**
     * Method to map the fields between the
     * CustomerSatelliteDto and CustomerSatellite objects.
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param customerSatelliteDto - CustomerSatelliteDto
     * @return CustomerSatellite - The mapped CustomerSatellite
     * object created from CustomerSatelliteDto
     * */
    @Mapping(source = SatelliteConstants.ID, target = SatelliteConstants.ID)
    @Mapping(source = SatelliteConstants.COUNTRY, target = SatelliteConstants.COUNTRY)
    @Mapping(source = SatelliteConstants.LAUNCH_DATE, target = SatelliteConstants.LAUNCH_DATE,
            dateFormat = SatelliteConstants.DATE_FORMAT)
    @Mapping(source = SatelliteConstants.MASS, target = SatelliteConstants.MASS)
    @Mapping(source = SatelliteConstants.LAUNCHER_STRING, target = SatelliteConstants.LAUNCHER_DOT_ID)
    CustomerSatellite mapCustomerSatelliteDtoToCustomerSatellite(CustomerSatelliteDto customerSatelliteDto);
}
