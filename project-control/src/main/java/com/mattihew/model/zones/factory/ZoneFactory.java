package com.mattihew.model.zones.factory;

import com.mattihew.model.zones.Zone;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collection;

public class ZoneFactory
{
    private static final Collection<ZoneTypeFactory<? extends Zone>> factories;

    static
    {
        factories = Arrays.asList(
                new SingleStationZoneFactory(),
                new MultiStationZoneFactory(),
                new NullIncludedZoneFactory());
    }

    private ZoneFactory()
    {

    }

    public static Zone fromJson(final String json)
    {
        return ZoneFactory.fromJson(new JSONObject(json));
    }

    public static Zone fromJson(final JSONObject json)
    {
        for (ZoneTypeFactory<? extends Zone> factory : ZoneFactory.factories)
        {
            if(factory.isValidJson(json))
            {
                return factory.fromJson(json);
            }
        }
        return null;
    }
}
