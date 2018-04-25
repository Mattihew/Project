package com.mattihew.services;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.Reader;

public class TriggerFileReader
{
    final Service service;

    public TriggerFileReader(final Service service)
    {
        this.service = service;
    }

    public void readFile(final Reader reader)
    {
        final JSONArray array = new JSONArray(new JSONTokener(reader));
        for (Object triggerObject : array)
        {
            if (triggerObject instanceof JSONObject)
            {

            }
            else
            {

            }
        }

    }
}
