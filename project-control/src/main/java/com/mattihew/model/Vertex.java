package com.mattihew.model;

import java.util.Objects;

public class Vertex
{
    private final String name;

    public Vertex(final String name)
    {
        this.name = Objects.requireNonNull(name, "name cannot be null");
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

    @Override
    public int hashCode()
    {
        return this.name.hashCode() * 21;
    }
}
