package com.mattihew.services;

import com.mattihew.triggers.Trigger;
import com.mattihew.triggers.actions.Action;
import com.mattihew.triggers.actions.ActionFactory;
import com.mattihew.triggers.zones.Zone;
import com.mattihew.triggers.zones.factory.ZoneFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class TriggerFileReader
{
    final Service service;

    public TriggerFileReader(final Service service)
    {
        this.service = service;
    }

    public void readFile(final InputStream stream)
    {
        this.readFile(new InputStreamReader(stream, StandardCharsets.UTF_8));
    }

    public void readFile(final Reader reader)
    {
        final JSONArray array = new JSONArray(new JSONTokener(reader));
        for (Object triggerObject : array)
        {
            if (triggerObject instanceof JSONObject)
            {
                final Action action = ActionFactory.fromJson(Trigger.parseAction((JSONObject) triggerObject));
                final Zone zone = ZoneFactory.fromJson(Trigger.parseZone((JSONObject) triggerObject));
                this.service.addTrigger(zone, action);
            }
            else
            {
                System.err.println("unexpected object '"+triggerObject+"'");
            }
        }

    }
}
