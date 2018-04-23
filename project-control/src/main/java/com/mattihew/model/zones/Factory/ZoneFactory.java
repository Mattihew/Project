package com.mattihew.model.zones.Factory;

import com.mattihew.model.zones.Zone;
import org.json.JSONObject;

public interface ZoneFactory<Z extends Zone>
{
    public abstract boolean isValidJson(final JSONObject json);

    public abstract Z fromJson(final JSONObject json);
}
