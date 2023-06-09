package com.dawnfall.engine.gen.Blocks.BlockProperties;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dawnfall.engine.gen.Blocks.Block;
import com.dawnfall.engine.rendering.Texture.BlockTextureAtlas;

import static com.dawnfall.engine.gen.Blocks.BlockRegister.*;


public class BlockMaterials<T extends BlockType> {

    public enum FaceSide{
        SIDE,BOTTOM,TOP
    };
    //This is the block materials class.
    private final T type;
    public Block block;
    public BlockType blockType;
    public Block.BlockTex blockTex;
    private TextureRegion region;
    public BlockMaterials(T blockType){
        this.type = blockType;
        this.blockType = blockType;
        region = new TextureRegion();
       //Checks the block type id.
        if (blockType.equals(BlockType.STONE)) {
            block = blocks[STONE];
        } else if (blockType.equals(BlockType.BED_ROCK)) {
            block = blocks[BED_ROCK];
        } else if (blockType.equals(BlockType.SOIL)) {
            block = blocks[DIRT];
        } else if (blockType.equals(BlockType.GRASS)) {
            block = blocks[GRASS];
        } else if (blockType.equals(BlockType.AIR)) {
            block = blocks[AIR];
        }
    }
    public TextureRegion getFullBlockMaterial(){
       if (type == BlockType.STONE){
           return block.textures.side;
       } else if (type == BlockType.BED_ROCK) {
           return  block.textures.side;
       } else if (type == BlockType.SOIL) {
           return  block.textures.side;
       } else if (type == BlockType.WATER) {
           return  block.textures.side;
       }else {
           return BlockTextureAtlas.getTex("missing");
       }
    }
    public TextureRegion getBlockSideMaterial(FaceSide side){
        if (type == BlockType.GRASS) {
            if (side == FaceSide.SIDE){
               blockTex = new Block.BlockTex(
                       BlockTextureAtlas.getTex("grass_side"),
                       BlockTextureAtlas.getTex("grass_top"),
                       BlockTextureAtlas.getTex("dirt"));
               region = block.textures.side;
           }else if (side == FaceSide.TOP){
                blockTex = new Block.BlockTex(
                        BlockTextureAtlas.getTex("grass_side"),
                        BlockTextureAtlas.getTex("grass_top"),
                        BlockTextureAtlas.getTex("dirt"));
                region = block.textures.top;
            } else if (side == FaceSide.BOTTOM) {
                blockTex = new Block.BlockTex(
                        BlockTextureAtlas.getTex("grass_side"),
                        BlockTextureAtlas.getTex("grass_top"),
                        BlockTextureAtlas.getTex("dirt"));
                region = block.textures.bottom;
            }
        } else {
            region = BlockTextureAtlas.getTex("missing");
        }
        return region;
    }
    public BlockType getBlockType() {
        return blockType;
    }
    public Block getBlock() {
        return block;
    }
    public TextureRegion getRegion() {
        return region;
    }
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
    public void setRegion(TextureRegion region) {
        this.region = region;
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    @Override
    public String toString() {
        return super.toString();
    }
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
