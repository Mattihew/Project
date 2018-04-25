package com.mattihew.model.zones.factory;

import com.mattihew.Props;
import com.mattihew.model.Vertex;
import com.mattihew.model.zones.SingleStationZone;
import org.json.JSONException;
import org.json.JSONObject;

public class SingleStationZoneFactory implements ZoneTypeFactory<SingleStationZone>
{
    SingleStationZoneFactory()
    {
        super();
    }

    @Override
    public boolean isValidJson(final JSONObject json)
    {
        try
        {
            return json.getString(Props.ZoneFactory_Type.getValue())
                    .equalsIgnoreCase(Props.SingleStationZone_Type.getValue());
        }
        catch (final JSONException e)
        {
            return false;
        }
    }

    @Override
    public SingleStationZone fromJson(final JSONObject json) throws JSONException
    {
        return new SingleStationZone(
                new Vertex(json.getString(Props.SingleStationZone_Station.getValue())),
                new Vertex(json.getString(Props.SingleStationZone_Peripheral.getValue())),
                json.getInt(Props.SingleStationZone_Min.getValue()),
                json.optInt(Props.SingleStationZone_Max.getValue(), Integer.MAX_VALUE));
    }
}
