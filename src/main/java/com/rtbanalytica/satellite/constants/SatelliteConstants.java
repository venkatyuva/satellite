package com.rtbanalytica.satellite.constants;

/**
 * Constants class to keep the
 * instance of a string in heap memory
 *
 * @author Venkatesh K Y
 * @since 31 May 2024
 * */
public class SatelliteConstants {

    // Third party service URLS
    public static final String CUSTOMER_SATELLITE_URL = "${customer_satellite.url}";
    public static final String LAUNCHERS_URL = "${launchers.url}";

    // Parameter constants
    public static final String ID = "id";
    public static final String FROM_DATE = "from_date";
    public static final String TO_DATE = "to_date";
    public static final String COUNTRY = "country";
    public static final String LAUNCHER_STRING = "launcher";
    public static final String LAUNCHER_ID = "launcher_id";
    public static final String LAUNCHER_DOT_ID = "launcher.id";
    public static final String LAUNCH_DATE = "launchDate";
    public static final String LAUNCH_DATE_VARIABLE = "launch_date";
    public static final String MASS = "mass";

    // Satellite controller constants
    public static final String SATELLITE_V1_BASE_PATH = "/v1/satellite";
    public static final String CUSTOMER_SATELLITES = "/customer-satellites";
    public static final String CUSTOMER_SATELLITE = "/customer-satellite";
    public static final String CUSTOMER_SATELLITE_ID = "/{customerSatelliteId}";
    public static final String LOAD = "/load";
    public static final String LAUNCHERS = "/launchers";
    public static final String LAUNCHER = "/launcher";
    public static final String LAUNCHER_ID_VARIABLE = "/{launcherId}";

    // Factory constants
    public static final String VALIDATOR = "Validator";

    // Date constants
    public static final String DATE_FORMAT = "dd-MM-yyyy";

    // Table constants
    public static final String CUSTOMER_SATELLITE_TABLE = "customer_satellite";
}
