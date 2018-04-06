package model;

import model.edge.Edge;

import java.util.*;

public class Point
{
    private final Collection<Edge> edges;

    public Point (final Edge edge)
    {
        this(Collections.singleton(edge));
    }

    public Point(final Edge... edges)
    {
        this(Arrays.asList(edges));
    }

    public Point(final Collection<Edge> edges)
    {
        this.edges = Collections.unmodifiableCollection(new ArrayList<>(edges));
    }

    public int edgeCount()
    {
        return edges.size();
    }

    public Edge getEdge(final Vertex station)
    {
        for (final Edge edge : this.edges)
        {
            if (edge.getStation().equals(station))
            {
                return edge;
            }
        }
        return null;
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj) return true;
        if (!(obj instanceof Point)) return false;

        final Point other = (Point) obj;
        return this.edges.size() == other.edges.size() && this.edges.containsAll(other.edges);
    }
}
