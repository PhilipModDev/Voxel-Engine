package com.dawnfall.engine.gen.Blocks;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dawnfall.engine.gen.Blocks.BlockProperties.BlockType;
import com.dawnfall.engine.rendering.Texture.BlockTextureAtlas;

public class Block {
   private final BlockType type;
    public final byte id;
    public final String name;
    public final boolean isSoild;
    public final boolean isTrans;
    public final boolean collision;
    public BlockTex textures;
    public Block(byte id, String name, boolean isSoildNcollision, BlockType type) {
        this(id, name, isSoildNcollision, isSoildNcollision, type);
    }
    public Block(byte id, String name, boolean isSoild, boolean collision, BlockType type) {
        this(id, name, isSoild, !isSoild, collision, type);
    }
    public Block(byte id, String name, boolean isSoild, boolean isTrans, boolean collision, BlockType type) {
        this.id = id;
        this.name = name;
        this.isSoild = isSoild;
        this.isTrans = isTrans;
        this.collision = collision;
        this.type = type;
    }
    public Block tex(TextureRegion all) {
        textures = new BlockTex(all, all, all);
        return this;
    }
    public Block tex(TextureRegion topBottom, TextureRegion side) {
        textures = new BlockTex(topBottom, side, topBottom);
        return this;
    }
    public Block tex(TextureRegion top, TextureRegion side, TextureRegion bottom) {
        textures = new BlockTex(top, side, bottom);
        return this;
    }
    public BlockType getBlockType(){
        return type;
    }
    public byte getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public static final class BlockTex {
        public final TextureRegion top, side, bottom;
        public BlockTex(TextureRegion top, TextureRegion side, TextureRegion bottom) {
            this.top = top == null ? BlockTextureAtlas.missing : top;
            this.side = side == null ? BlockTextureAtlas.missing : side;
            this.bottom = bottom == null ? BlockTextureAtlas.missing : bottom;
        }
    }
}
