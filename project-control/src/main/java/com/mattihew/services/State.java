package com.mattihew.services;

public class State
{
    private final int steps;
    private int progress;
    private boolean value;

    public State(final int steps)
    {
        this.steps = steps;
        this.value = false;
        this.progress = 0;
    }

    public void increment()
    {
        if (this.value)
        {
            this.reset();
        }
        else
        {
            this.progress++;
            if (this.progress >= this.steps)
            {
                this.value = !this.value;
            }
        }
    }

    public void decrement()
    {
        if (!this.value)
        {
            this.reset();
        }
        else
        {
            this.progress--;
            if (this.progress < 0)
            {
                this.value = !this.value;
            }
        }
    }

    public void reset()
    {
        this.progress = this.value ? this.steps : 0;
    }

    public void setValue(final boolean value)
    {
        this.value = value;
        this.progress = this.value ? 0 : this.steps;
    }

    public boolean getValue()
    {
        return this.value;
    }
}
