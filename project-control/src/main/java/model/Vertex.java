package model;

public class Vertex
{
    private final String name;

    public Vertex(final String name)
    {
        this.name = name;
    }

    public String toString()
    {
        return this.name;
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (obj == this) return true;
        if (!(obj instanceof Vertex)) return false;
        return this.name.equals(((Vertex) obj).name);
    }
}
