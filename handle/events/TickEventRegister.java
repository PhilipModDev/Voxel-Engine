package com.dawnfall.engine.handle.events;

import java.util.EventObject;

public class TickEventRegister extends EventObject {
    private final EventAdapter source;
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public TickEventRegister(EventAdapter source) {
        super(source);
        this.source = source;
    }
    public void register(){
        source.updateTick();
    }
    @Override
    public EventAdapter getSource() {
        return source;
    }
    @Override
    public String toString() {
        return super.toString();
    }
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
