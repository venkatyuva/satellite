package com.rtbanalytica.satellite.util;

import com.rtbanalytica.satellite.constants.SatelliteConstants;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DateUtilTest {

    @InjectMocks
    private DateUtil dateUtil;

    @Test
    public void testGetSqlTimeStamp() {
        Assert.assertNotNull("Non empty timestamp", dateUtil.getSqlTimeStamp("03-06-2024"));
        Assert.assertThrows("Invalid date", RuntimeException.class, () ->
                dateUtil.getSqlTimeStamp(SatelliteConstants.DATE_FORMAT));
    }
}