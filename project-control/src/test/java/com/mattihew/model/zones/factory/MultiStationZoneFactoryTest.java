package com.mattihew.model.zones.factory;

import com.mattihew.model.zones.MultiStationZone;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.*;

public class MultiStationZoneFactoryTest
{
    private static final String json = "{\"type\":\"MultiStationZone\", \"method\":\"AND\", \"zones\": []}"; //NON-NLS

    @Test
    public void fromJson()
    {
        assertTrue(ZoneFactory.fromJson(new JSONObject(json)) instanceof MultiStationZone);
    }
}