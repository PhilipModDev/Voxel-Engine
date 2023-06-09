package com.dawnfall.engine.rendering.Texture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.dawnfall.engine.util.libUtil;

public class BlockTextureAtlas implements Disposable {
    public static TextureRegion missing;
    public static TextureRegion cross;
    public static com.badlogic.gdx.graphics.g2d.TextureAtlas atlas;
    public static TextureRegion loadRegion;
    public static void loadTexture() {
        Gdx.app.log("BlockTextureAtlas","Registering block texture atlas");

        atlas = new TextureAtlas(libUtil.getInternalFile("blocks/blocks.atlas"));
        loadRegion = new TextureRegion(new Texture(libUtil.getInternalFile("blocks/blocks.png")));
        missing = atlas.findRegion("missing");
        cross = atlas.findRegion("cross");
        Gdx.app.log("BlockTextureAtlas","Registering block texture atlas complete");
    }
    public static TextureRegion getLoadRegion() {
        return loadRegion;
    }
    public static TextureRegion getTex(final String name) {
        return atlas.findRegion(name);
    }
    @Override
    public void dispose() {
        atlas.dispose();
    }
}