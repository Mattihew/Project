package model;

public class Triangle
{
    private final Edge[] edges = new Edge[3];
    private final double[] angles = new double[3];

    public Triangle(final Edge edge0, final Edge edge1, final Edge edge2)
    {
        this.edges[0] = edge0;
        this.edges[1] = edge1;
        this.edges[2] = edge2;

        int maxLengthIndex = 0;
        for (int i=1; i<3; i++)
        {
            if (this.edges[i].getDistance() > this.edges[maxLengthIndex].getDistance())
            {
                maxLengthIndex = i;
            }
        }
        this.angles[maxLengthIndex] = cosSolve(
                this.edges[(maxLengthIndex+1)%3].getDistance(),
                this.edges[(maxLengthIndex+2)%3].getDistance(),
                this.edges[maxLengthIndex].getDistance());

        this.angles[(maxLengthIndex+1)%3] = sinSolve(
                this.edges[maxLengthIndex].getDistance(),
                this.angles[maxLengthIndex],
                this.edges[(maxLengthIndex+1)%3].getDistance());

        this.angles[(maxLengthIndex+2)%3] = 180d - this.angles[maxLengthIndex] - this.angles[(maxLengthIndex+1)%3];
    }

    public Triangle(final Edge edge0, final Edge edge1, final double angle2)
    {
        //todo
        this.edges[0] = edge0;
        this.edges[1] = edge1;

        this.angles[2] = angle2;

    }

    private static double cosSolve(final int adjacent1, final int adjacent2, final int opposite)
    {
        double result = adjacent1*adjacent1 + adjacent2*adjacent2 - opposite*opposite;
        result /= (2 * adjacent1 * adjacent2);
        return Math.acos(result);
    }

    private static double sinSolve(final int edge1, final double angle1, final int edge2)
    {
        double result = edge2 * Math.sin(angle1);
        result /= edge1;
        return Math.asin(result);
    }
}
