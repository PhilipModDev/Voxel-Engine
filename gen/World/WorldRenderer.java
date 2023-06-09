package com.dawnfall.engine.gen.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.utils.Disposable;
import com.dawnfall.engine.Client.MainRenderer;
import com.dawnfall.engine.Client.features.DebugProperties.Debug;
import com.dawnfall.engine.Client.features.OptionsProperties.Options;
import com.dawnfall.engine.rendering.shaders.EngineLighting;

public class WorldRenderer implements Disposable {
    /*
     *World Renderer renders the world.
     */
    private World world;
    private ModelBatch batch;
    private EngineLighting lighting;
    private final boolean gen;
    public WorldRenderer(boolean gen) {
        if (gen){
            Gdx.app.log("World", "Loading world");
            lighting = new EngineLighting();
            batch = new ModelBatch();
            world = new World(true);
        }
        this.gen = gen;
    }
    /*
     * Starts the Rendering process.
     */
    public void initializeWorld() {
        if (gen) {
            try {
                world.updateRayCast();
                batch.begin(MainRenderer.player.getPlayer());
                batch.render(world.chunks, lighting.environment);
                batch.render(Debug.showXYZLine());
                batch.end();
            } catch (Exception exception) {
                batch.end();
                Gdx.app.error("Chunks Load", Options.NetworkResource.name() +" Renderer is overloading");
            }
            world.update();
        }
    }
    @Override
    public void dispose() {
        batch.dispose();
    }
}
