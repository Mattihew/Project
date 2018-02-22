package model;

public class Edge
{
    private final Vertex v1;
    private final Vertex v2;
    private final int distance;

    public Edge(final Vertex v1, final Vertex v2, final int distance)
    {
        this.v1 = v1;
        this.v2 = v2;
        this.distance = distance;
    }

    public int getDistance()
    {
        return this.distance;
    }

    public boolean contains(final Vertex vertex)
    {
        return vertex.equals(this.v1) || vertex.equals(this.v2);
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj) return true;
        if (!(obj instanceof Edge)) return false;

        final Edge other = (Edge) obj;
        return other.contains(this.v1) && other.contains(this.v2) && other.distance == this.distance;

    }

    @Override
    public String toString()
    {
        return String.format("%s, %s, %s", this.v1, this.v2, this.distance); //NON-NLS
    }
}
