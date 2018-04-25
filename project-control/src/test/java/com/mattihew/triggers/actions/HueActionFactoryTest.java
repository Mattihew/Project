package com.mattihew.triggers.actions;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
public class HueActionFactoryTest
{
    private static final String json = "{\"type\": \"HueAction\", \"light\": 3, \"on\": false}"; //NON-NLS

    @Test
    public void fromJson()
    {
        assertTrue(ActionFactory.fromJson(json) instanceof HueAction);
    }
}
