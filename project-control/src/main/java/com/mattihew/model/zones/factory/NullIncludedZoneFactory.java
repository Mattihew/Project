package com.mattihew.model.zones.factory;

import com.mattihew.Props;
import com.mattihew.model.zones.NullIncludedZone;
import com.mattihew.model.zones.Zone;
import org.json.JSONException;
import org.json.JSONObject;

public class NullIncludedZoneFactory implements ZoneTypeFactory<NullIncludedZone>
{
    NullIncludedZoneFactory()
    {
        super();
    }

    @Override
    public boolean isValidJson(final JSONObject json)
    {
        try
        {
            return json.getString(Props.ZoneFactory_Type.getValue())
                    .equalsIgnoreCase(Props.NullIncludingZone_Type.getValue());
        }
        catch (final JSONException e)
        {
            return false;
        }
    }

    @Override
    public NullIncludedZone fromJson(final JSONObject json)
    {
        final JSONObject jsonZone = json.getJSONObject(Props.NullIncludingZone_Zone.getValue());
        final Zone zone = ZoneFactory.fromJson(jsonZone);

        if (zone == null)
        {
            return null;
        }
        else
        {
            return new NullIncludedZone(zone);
        }
    }
}
