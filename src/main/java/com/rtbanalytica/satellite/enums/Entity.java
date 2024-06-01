package com.rtbanalytica.satellite.enums;

import lombok.Getter;

@Getter
public enum Entity {
    LAUNCHER("Launcher"),
    CUSTOMER_SATELLITE("CustomerSatellite");

    private final String value;

    Entity(String value) {
        this.value = value;
    }
}
