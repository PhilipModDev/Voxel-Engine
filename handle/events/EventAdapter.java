package com.dawnfall.engine.handle.events;


public abstract class EventAdapter implements Events {
    /**
     * Constructs a prototypical EventAdapter.
     */
    public void port() {}
    @Override
    public void updateTick() {}
    @Override
    public void updateInput() {}
}
