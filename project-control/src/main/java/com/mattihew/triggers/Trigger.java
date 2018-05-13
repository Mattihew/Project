package com.mattihew.triggers;

import com.mattihew.Props;
import com.mattihew.triggers.actions.Action;
import com.mattihew.triggers.actions.ActionFactory;
import com.mattihew.triggers.zones.Zone;
import com.mattihew.triggers.zones.factory.ZoneFactory;
import org.json.JSONException;
import org.json.JSONObject;

public class Trigger
{
    private final Action action;
    private final Zone zone;

    public Trigger(final Action action, final Zone zone)
    {
        this.action = action;
        this.zone = zone;
    }

    public Trigger(final JSONObject json)
    {
        final Object actionJson = parseAction(json);
        final JSONObject zoneJson = parseZone(json);

        this.action = ActionFactory.fromJson(actionJson);
        this.zone = ZoneFactory.fromJson(zoneJson);

    }

    public static Object parseAction(final JSONObject json)
    {
        try
        {
            return json.getJSONObject(Props.Trigger_Action.getValue());
        }
        catch (final JSONException e)
        {
            return json.getJSONArray(Props.Trigger_Action.getValue());
        }
    }

    public static JSONObject parseZone(final JSONObject json)
    {
        return json.getJSONObject(Props.Trigger_Zone.getValue());
    }
}
