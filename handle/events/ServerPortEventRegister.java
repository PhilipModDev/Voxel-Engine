package com.dawnfall.engine.handle.events;

import java.util.EventObject;

public class ServerPortEventRegister extends EventObject {
    private final EventAdapter source;
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ServerPortEventRegister(EventAdapter source) {
        super(source);
        this.source = source;
    }
    public void register(){
        source.port();
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
        if (this.source == obj){
            return super.equals(obj);
        }
        return false;
    }
}
