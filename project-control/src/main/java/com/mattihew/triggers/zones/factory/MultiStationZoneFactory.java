package com.mattihew.triggers.zones.factory;

import com.mattihew.Props;
import com.mattihew.triggers.zones.MultiStationZone;
import com.mattihew.triggers.zones.Zone;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

public class MultiStationZoneFactory implements ZoneTypeFactory<MultiStationZone>
{
    MultiStationZoneFactory()
    {
        super();
    }

    @Override
    public boolean isValidJson(final JSONObject json)
    {
        try
        {
            return json.getString(Props.ZoneFactory_Type.getValue())
                    .equalsIgnoreCase(Props.MultiStationZone_Type.getValue());
        }
        catch (final JSONException e)
        {
            return false;
        }
    }

    @Override
    public MultiStationZone fromJson(final JSONObject json)
    {
        final MultiStationZone.Method method = json.getEnum(MultiStationZone.Method.class, Props.MultiStationZone_Method.getValue());

        final JSONArray jsonZones = json.getJSONArray(Props.MultiStationZone_Zones.getValue());
        final Collection<Zone> zones = new ArrayList<>(jsonZones.length());
        for (final Object jsonZone : jsonZones)
        {
            if(jsonZone instanceof JSONObject)
            {
                final Zone zone = ZoneFactory.fromJson((JSONObject) jsonZone);
                if (zone != null)
                {
                    zones.add(zone);
                }
            }
            else
            {
                System.err.print("unexpected object '"+jsonZone+"' in MultiStationZone json"); //NON-NLS
            }
        }

        return new MultiStationZone(zones, method);
    }
}
