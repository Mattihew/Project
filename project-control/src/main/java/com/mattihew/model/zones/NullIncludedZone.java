package com.mattihew.model.zones;

import com.mattihew.model.Point;

import java.util.Objects;

import static com.mattihew.model.zones.Zone.State.IN;
import static com.mattihew.model.zones.Zone.State.NULL;

public class NullIncludedZone extends Zone
{
    private final Zone zone;

    public NullIncludedZone(final Zone zone)
    {
        this.zone = zone;
    }

    @Override
    public State inRange(final Point point)
    {
        final State state = this.zone.inRange(point);
        return state.equals(NULL) ? IN : state;
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (obj == this) return true;
        if (!(obj instanceof NullIncludedZone)) return false;
        return ((NullIncludedZone) obj).zone.equals(this.zone);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(zone);
    }
}
