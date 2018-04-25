package com.mattihew.triggers.zones.factory;

import com.mattihew.triggers.zones.NullIncludedZone;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class NullIncludedZoneFactoryTest
{
    private static final String json =
            "{\"type\":\"NullIncludingZone\",\"zone\":{\"type\":\"SingleStationZone\",\"station\":\"test\",\"peripheral\":\"test\",\"min\": 45}}"; //NON-NLS

    @Test
    public void fromJson()
    {
        assertTrue(ZoneFactory.fromJson(json) instanceof NullIncludedZone);
    }
}
