package com.mattihew.model.zones;

import com.mattihew.model.Point;
import com.mattihew.model.Vertex;
import com.mattihew.model.edge.Edge;

import java.util.Objects;

public class SingleStationZone extends Zone
{
    private final Vertex station;
    private final Vertex peripheral;
    private final int minDist;
    private final int maxDist;

    public SingleStationZone(final Vertex station, final Vertex peripheral, final int minDist)
    {
        this(station, peripheral, minDist, Integer.MAX_VALUE);
    }

    public SingleStationZone(final Vertex station, final Vertex peripheral, final int minDist, final int maxDist)
    {
        this.station = station;
        this.peripheral = peripheral;
        this.minDist = minDist;
        this.maxDist = maxDist;
    }

    @Override
    public State inRange(final Point point)
    {
        if (point == null || !point.getPeripheral().equals(this.peripheral))
        {
            return State.NULL;
        }
        final Edge edge = point.getEdge(this.station);
        if (edge != null)
        {
            if(this.minDist <= edge.getDistance() && edge.getDistance() < this.maxDist)
            {
                return State.IN;
            }
            else
            {
                return State.OUT;
            }
        }
        else
        {
            return State.NULL;
        }
    }

    @Override
    public boolean equals(final Object obj)
    {
        if(obj == this) return true;
        if(!(obj instanceof SingleStationZone)) return false;
        return this.minDist == ((SingleStationZone) obj).minDist &&
                this.maxDist == ((SingleStationZone) obj).maxDist &&
                this.peripheral == ((SingleStationZone) obj).peripheral &&
                this.station == ((SingleStationZone) obj).station;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(minDist, maxDist, peripheral, station);
    }
}
