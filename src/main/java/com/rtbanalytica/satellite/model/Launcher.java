package com.rtbanalytica.satellite.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rtbanalytica.satellite.constants.SatelliteConstants;
import com.rtbanalytica.satellite.enums.LauncherType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

/**
 * Model class for the launcher
 * entity(table) in the database.
 *
 * @author Venkatesh K Y
 * @since 31 May 2024
 * */
@Entity
@Getter
@Setter
@Table(name = SatelliteConstants.LAUNCHER_STRING)
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class Launcher extends BaseEntity {
    private LauncherType launcherType;
    @JsonFormat(pattern = SatelliteConstants.DATE_FORMAT)
    private Date registeredOn;
}
