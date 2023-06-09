package com.dawnfall.engine.Client.features.OptionsProperties;

import com.dawnfall.engine.Client.MainGame;
import com.dawnfall.engine.Client.MainRenderer;

public final class Options {
    public static boolean RENDER_GAME = false;
    public static MainGame MAIN_RENDERER = new MainGame();
    public static MainRenderer GAME = new MainRenderer();
    public static NetworkSides NetworkResource = NetworkSides.DEFAULT;
    public enum NetworkSides {
        CLIENT,
        SERVER,
        DEFAULT
    }
}
