package com.dawnfall.engine.gen.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.utils.Disposable;
import com.dawnfall.engine.Client.MainRenderer;
import com.dawnfall.engine.Client.features.DebugProperties.Debug;
import com.dawnfall.engine.Client.features.OptionsProperties.Options;
import com.dawnfall.engine.rendering.shaders.lighting;

public class WorldRenderer implements Disposable {
    private World world;
    private ModelBatch batch;
    private lighting lighting;
    private final boolean gen;
    public WorldRenderer(boolean gen) {
        if (gen){
            Gdx.app.log("World", "Loading world");
            lighting = new lighting();
            batch = new ModelBatch();
            world = new World(true);
        }
        this.gen = gen;
    }
    public void renderWorld() {
        if (gen) {
            try {
                world.blockEdit.update();
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
