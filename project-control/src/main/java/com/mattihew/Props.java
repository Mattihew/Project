package com.mattihew;

import java.io.IOException;
import java.util.Properties;

public enum Props
{
    HueURL("hue.url"),
    HueBody("hue.body"),
    HueMethod("hue.method"),
    PointCacheMaxAge("pointCache.maxAge"),

    Trigger_Action("Trigger.action"),
    Trigger_Zone("Trigger.zone"),

    ZoneFactory_Type("ZoneFactory.type"),

    SingleStationZone_Type("SingleStationZoneFactory.type"),
    SingleStationZone_Station("SingleStationZoneFactory.station"),
    SingleStationZone_Peripheral("SingleStationZoneFactory.peripheral"),
    SingleStationZone_Min("SingleStationZoneFactory.min"),
    SingleStationZone_Max("SingleStationZoneFactory.max"),

    MultiStationZone_Type("MultiStationZoneFactory.type"),
    MultiStationZone_Method("MultiStationZoneFactory.method"),
    MultiStationZone_Zones("MultiStationZoneFactory.zones"),

    NullIncludingZone_Type("NullIncludingZoneFactory.type"),
    NullIncludingZone_Zone("NullIncludingZoneFactory.zone"),

    ActionFactory_Type("ActionFactory.type"),

    HueActionFactory_Type("HueActionFactory.type"),
    HueActionFactory_Light("HueActionFactory.light"),
    HueActionFactory_On("HueActionFactory.on");

    private static final Properties propsFile = new Properties();

    static
    {
        try
        {
            Props.propsFile.load(ClassLoader.getSystemResourceAsStream("config.properties")); //NON-NLS
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
    }

    private String key;

    private Props(final String key)
    {
        this.key = key;
    }

    public String getValue()
    {
        return Props.propsFile.getProperty(this.key);
    }

    public String getValue(final String defaultValue)
    {
        return Props.propsFile.getProperty(this.key, defaultValue);
    }

    public String format(final Object... values)
    {
        return String.format(this.getValue(), values);
    }

    public String toString()
    {
        return this.getValue();
    }
}
