package com.mattihew.model.zones;

import com.mattihew.model.Point;

public abstract class Zone
{
    public abstract State inRange(final Point point);

    @SuppressWarnings("Duplicates")
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

        public State and(final State other)
        {
            if (NULL.equals(this))
            {
                return other;
            }
            else if(OUT.equals(this) || OUT.equals(other))
            {
                return OUT;
            }
            else
            {
                return IN;
            }
        }

        public State or(final State other)
        {
            if (NULL.equals(this))
            {
                return other;
            }
            else if(IN.equals(this) || IN.equals(other))
            {
                return IN;
            }
            else
            {
                return OUT;
            }
        }
    }
}
