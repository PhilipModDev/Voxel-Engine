package com.dawnfall.engine.mesh;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;

public class ChunkMeshFace {
    public void frontFace(int x, int y, int z, MeshPartBuilder meshPartBuilder,  TextureRegion region) {
        meshPartBuilder.setUVRange(region);
        meshPartBuilder.rect(
                0 + x, 0 + y, 0 + z,
                1 + x, 0 + y, 0 + z,
                1 + x, 1 + y, 0 + z,
                0 + x, 1 + y, 0 + z,
                0,1, 0
        );
    }
    public void backFace(int x, int y, int z,MeshPartBuilder meshPartBuilder,  TextureRegion region) {
        meshPartBuilder.setUVRange(region);
        meshPartBuilder.rect(
                1 + x, 0 + y, -1 + z,
                0 + x, 0 + y, -1 + z,
                0 + x, 1 + y, -1 + z,
                1 + x, 1 + y, -1 + z,
                0, 1, 0
        );

    }
    public void rightFace(int x, int y, int z, MeshPartBuilder meshPartBuilder,  TextureRegion region) {
        meshPartBuilder.setUVRange(region);
        meshPartBuilder.rect(
                1 + x, 0 + y, 0 + z,
                1 + x, 0 + y, -1 + z,
                1 + x, 1 + y, -1 + z,
                1 + x, 1 + y, 0 + z,
                1, 0, 0
        );
    }
    public void leftFace(int x, int y, int z, MeshPartBuilder meshPartBuilder,  TextureRegion region) {
        meshPartBuilder.setUVRange(region);
        meshPartBuilder.rect(
                0 + x, 0 + y, -1 + z,
                0 + x, 0 + y, 0 + z,
                0 + x, 1 + y, 0 + z,
                0 + x, 1 + y, -1 + z,
                1, 0, 1
        );
    }
    public void topFace(int x, int y, int z, MeshPartBuilder meshPartBuilder, TextureRegion region) {
        meshPartBuilder.setUVRange(region);
        meshPartBuilder.rect(
                0 + x, 1 + y, 0 + z,
                1 + x, 1 + y, 0 + z,
                1 + x, 1 + y, -1 + z,
                0 + x, 1 + y, -1 + z,
                0, 1, 0
        );
    }
    public void bottomFace(int x, int y, int z, MeshPartBuilder meshPartBuilder, TextureRegion region) {
        meshPartBuilder.setUVRange(region);
        meshPartBuilder.rect(
                0 + x, 0 + y, 0 + z,
                0 + x, 0 + y, -1 + z,
                1 + x, 0 + y, -1 + z,
                1 + x, 0 + y, 0 + z,
                0, 1, 0
        );
    }
}
