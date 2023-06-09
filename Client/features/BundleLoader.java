package com.dawnfall.engine.Client.features;

import com.badlogic.gdx.Gdx;
import com.dawnfall.engine.Client.MainRenderer;
import com.dawnfall.engine.gen.Blocks.BlockRegister;
import com.dawnfall.engine.handle.InputUpdater;
import com.dawnfall.engine.rendering.Texture.BlockTextureAtlas;
import com.dawnfall.engine.handle.Inputs;

public class BundleLoader {
   private static final Inputs inputs = new Inputs();
    public static void ResizeUpdate(int width, int height) {
        MainRenderer.player.getPlayer().viewportWidth = width;
        MainRenderer.player.getPlayer().viewportHeight = height;
        MainRenderer.player.getPlayer().update();
        Gdx.gl.glViewport(0, 0, width, height);
        MainRenderer.spriteBatch.setProjectionMatrix(MainRenderer.fitViewport.getCamera().combined);
        MainRenderer.fitViewport.update(width, height, true);
    }
    public static void LoadResourceContainer(){
        BlockTextureAtlas.loadTexture();
        BlockRegister.loadBlocks();
        BlockRegister.loadTextures();
    }
    public static void LoadEvents(){
        if (!InputUpdater.Pause){
            Gdx.input.setCursorCatched(true);
            MainRenderer.player.updatePlayer();
            Gdx.input.setInputProcessor(inputs);
        }
    }
    public static void ResizeUpdate(){
        Inputs inputs = new Inputs();
        inputs.clear();
    }
}