package com.dawnfall.engine.handle.events;
public class EventRegister<T extends EventAdapter> {
    private T event;
    public TickEventRegister tickEventRegister;
    public ServerPortEventRegister serverPortEvent;
    public void registerTick(EventAdapter event){
       this.event = (T) event;
       tickEventRegister = new TickEventRegister(event);
    }
    public void registerServerPort(EventAdapter event){
        this.event = (T)  event;
        serverPortEvent = new ServerPortEventRegister(event);
    }
    public T getEvent() {
        return event;
    }
}
