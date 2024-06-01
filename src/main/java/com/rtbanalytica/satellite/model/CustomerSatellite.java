package com.rtbanalytica.satellite.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rtbanalytica.satellite.constants.SatelliteConstants;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

/**
 * Model class for the customer_satellite
 * entity(table) in the database.
 *
 * @author Venkatesh K Y
 * @since 31 May 2024
 * */
@Entity
@Getter
@Setter
@Table(name = SatelliteConstants.CUSTOMER_SATELLITE_TABLE)
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSatellite extends BaseEntity {
    private String country;
    @JsonFormat(pattern = SatelliteConstants.DATE_FORMAT)
    private Date launchDate;
    private int mass;
    @ManyToOne
    @JoinColumn(name = SatelliteConstants.LAUNCHER_STRING)
    private Launcher launcher;
}
