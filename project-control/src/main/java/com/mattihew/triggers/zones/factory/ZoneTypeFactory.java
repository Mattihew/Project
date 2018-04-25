package com.mattihew.triggers.zones.factory;

import com.mattihew.triggers.zones.Zone;
import org.json.JSONObject;

interface ZoneTypeFactory<Z extends Zone>
{
    public abstract boolean isValidJson(final JSONObject json);

    public abstract Z fromJson(final JSONObject json);
}
