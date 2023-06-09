package com.dawnfall.engine.gen.Blocks;

import com.badlogic.gdx.Gdx;
import com.dawnfall.engine.gen.Blocks.BlockProperties.BlockType;

import static com.dawnfall.engine.rendering.Texture.BlockTextureAtlas.getTex;
public final class BlockRegister {
    private static byte i = 0;
    public static final byte
            AIR = i++,
            GRASS =i++,
            DIRT = i++,
            STONE = i++,
            BED_ROCK =i++,
            WATER = i++;

    public static final int size = i;
    public static final Block[] blocks = new Block[size];
    /** Make sure the BlockTextureAtlas is called first */
    public static void loadBlocks() {
        Gdx.app.log("BlockRegister","Loading block types");
        blocks[AIR] = new Block(AIR, "Air", false, BlockType.AIR);
        blocks[GRASS] = new Block(GRASS, "grass", true, BlockType.GLASS);
        blocks[DIRT] = new Block(DIRT, "dirt", true, BlockType.SOIL);
        blocks[STONE] = new Block(STONE, "Stone", true, BlockType.STONE);
        blocks[BED_ROCK] = new Block(BED_ROCK,"bedrock",true,BlockType.BED_ROCK);
        blocks[WATER] = new Block(WATER,"water",true,BlockType.WATER);
        Gdx.app.log("BlockRegister","Loading block types complete");
    }
    public static void loadTextures() {
        Gdx.app.log("BlockRegister","Loading block texture atlas");
        blocks[STONE].tex(getTex("stone"));
        blocks[DIRT].tex(getTex("dirt"));
        blocks[GRASS].tex(getTex("grass_top"), getTex("grass_side"), getTex("dirt"));
        blocks[BED_ROCK].tex(getTex("bedrock"));
        blocks[WATER].tex(getTex("water1"));
        Gdx.app.log("BlockRegister","Loading block texture atlas complete");
    }
    public static boolean canAddFace(Block block, int id) {
        if (id == AIR) return true;
        final Block secondary = blocks[id];
        if (block.isSoild == secondary.isSoild)
            return false;
        if (block.isSoild == true && secondary.isSoild == false)
            return true; // primary is solid and secondary is trans.
        if (block.isSoild == false && secondary.isSoild == true)
            return false; // primary is trans and secondary is solid.
        return false;
    }
}