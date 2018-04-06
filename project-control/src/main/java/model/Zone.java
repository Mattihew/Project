package model;

public abstract class Zone
{
    public abstract State inRange(final Point point);

    public enum State
    {
        IN,
        OUT,
        NULL;

        public State inverse()
        {
            switch (this)
            {
                case IN:
                    return OUT;
                case OUT:
                    return IN;
                case NULL:
                default:
                    return NULL;
            }
        }
    }
}
