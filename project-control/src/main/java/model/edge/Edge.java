package model.edge;

import model.Vertex;

public interface Edge
{
    public int getDistance();

    public boolean contains(final Vertex vertex);
}
