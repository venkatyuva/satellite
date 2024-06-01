package com.rtbanalytica.satellite.util;

import com.rtbanalytica.satellite.constants.SatelliteConstants;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for the Date
 *
 * @author Venkatesh K Y
 * @since 31 May 2024
 * */
@Component
public class DateUtil {

    /**
     * Method to get the sql time stamp from the string
     *
     * @author Venkatesh K Y
     * @since 31 May 2024
     *
     * @param value - String
     * @return Timestamp - the sql timestamp
     * */
    public Timestamp getSqlTimeStamp(String value) {
        DateFormat dateFormat = new SimpleDateFormat(SatelliteConstants.DATE_FORMAT);
        Date date;
        try {
            date = dateFormat.parse(value);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return new Timestamp(date.getTime());
    }
}
