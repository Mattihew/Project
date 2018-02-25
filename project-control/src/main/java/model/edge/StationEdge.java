package model.edge;

import model.Vertex;

public class StationEdge implements Edge
{
    private final Vertex station1;
    private final Vertex station2;
    private final int distance;

    public StationEdge(final Vertex station1, final Vertex station2, final int distance)
    {
        this.station1 = station1;
        this.station2 = station2;
        this.distance = distance;
    }

    public Vertex getStartStation()
    {
        return this.station1;
    }

    public Vertex getEndStation()
    {
        return this.station2;
    }

    @Override
    public int getDistance()
    {
        return this.distance;
    }

    @Override
    public boolean contains(final Vertex vertex)
    {
        return this.station1.equals(vertex) || this.station2.equals(vertex);
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (obj == this) return true;
        if (!(obj instanceof StationEdge)) return false;
        final StationEdge other = (StationEdge) obj;
        return other.contains(this.station1) && other.contains(this.station2) && other.distance == this.distance;
    }
}
