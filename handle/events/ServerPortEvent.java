package com.dawnfall.engine.handle.events;

import com.badlogic.gdx.Gdx;

public class ServerPortEvent extends EventAdapter {
    @Override
    public void port() {
        //This event will only fire when a server is ported.
        Gdx.app.log("Server","Server has been ported." +
                "\nStarting Compressed chunk data.");
        super.port();
    }
}
