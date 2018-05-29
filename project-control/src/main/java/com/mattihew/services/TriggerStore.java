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
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class TriggerStore
{
    private JSONArray triggers;

    public TriggerStore(final InputStream stream)
    {
        this(new InputStreamReader(stream, StandardCharsets.UTF_8));
    }

    public TriggerStore(final Reader reader)
    {
        this.triggers = new JSONArray(new JSONTokener(reader));
    }

    public JSONArray getTriggers()
    {
        return this.triggers;
    }

    public void write(final Writer stream)
    {
        this.triggers.write(stream);
    }

}
