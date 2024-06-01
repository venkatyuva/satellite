package com.rtbanalytica.satellite.dto;

import com.rtbanalytica.satellite.model.Launcher;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * DTO class for the response of
 * launcher third party API
 *
 * @author Venkatesh K Y
 * @since 31 May 2024
 * */
@Getter
@Setter
public class LauncherResponse {
    private List<Launcher> launchers;
}
