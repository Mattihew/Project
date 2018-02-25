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
        return obj == this || obj instanceof Vertex && this.name.equals(((Vertex) obj).name);
    }
}
