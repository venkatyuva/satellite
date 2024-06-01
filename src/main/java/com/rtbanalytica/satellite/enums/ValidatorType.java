package com.rtbanalytica.satellite.enums;

import lombok.Getter;

@Getter
public enum ValidatorType {
    CREATE("create"),
    UPDATE("update"),
    DELETE("delete");

    private final String value;

    ValidatorType(String value) {
        this.value = value;
    }
}
