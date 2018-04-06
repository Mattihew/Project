package model;

import model.edge.Edge;

import java.util.Objects;

public class SingleStationZone extends Zone
{
    private final Vertex station;
    private final int minDist;
    private final int maxDist;

    public SingleStationZone(final Vertex station, final int minDist)
    {
        this(station, minDist, Integer.MAX_VALUE);
    }

    public SingleStationZone(final Vertex station, final int minDist, final int maxDist)
    {
        this.station = station;
        this.minDist = minDist;
        this.maxDist = maxDist;
    }

    @Override
    public State inRange(final Point point)
    {
        final Edge edge = point.getEdge(this.station);
        if (edge != null)
        {
            if(edge.getDistance() > this.minDist && edge.getDistance() < this.maxDist)
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
                this.maxDist == ((SingleStationZone) obj).maxDist;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(minDist, maxDist);
    }
}
