package com.mattihew.model.zones;

import com.mattihew.model.Point;

public class InverseZone extends Zone
{
    private final Zone zone;

    public InverseZone(final Zone zone)
    {
        this.zone = zone;
    }

    @Override
    public State inRange(final Point point)
    {
        return this.zone.inRange(point).inverse();
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (obj == this) return true;
        if (!(obj instanceof InverseZone)) return false;

        return this.zone.equals(((InverseZone) obj).zone);
    }

    @Override
    public int hashCode()
    {
        return this.zone.hashCode() * 21;
    }
}
