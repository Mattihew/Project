package model;

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
        this.edges = new ArrayList<>(edges);
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
