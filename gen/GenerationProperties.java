package com.dawnfall.engine.gen;

import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Vector2;
import com.dawnfall.engine.gen.Blocks.BlockRegister;

public class GenerationProperties {
    public static int heightOffset = 80;
    public static float heightIntensity = 25f;
    public static Vector2 NoiseOffset = new Vector2(0.5f, 0);
    public static Vector2 NoiseScale = new Vector2(0.9f, 0.8f);
    public static long seed;
    private final RandomXS128 randomXS128;
    private final int MaxRandomization;
    public GenerationProperties(int MaxRandomization){
        randomXS128 = new RandomXS128();
        seed = randomXS128.nextLong();
        this.MaxRandomization = MaxRandomization;
    }
    public GenerationProperties(int seed,int MaxRandomization){
        GenerationProperties.seed = seed;
        randomXS128 = new RandomXS128(seed);
        this.MaxRandomization = MaxRandomization;
    }
    public void buildGrassLayer(int x, int y , int z, int heightGen, byte[][][] blockData){
        if (y == heightGen) {
            blockData[x][y][z] = BlockRegister.blocks[BlockRegister.GRASS].id;
        }
    }

    public void buildGrassRandomDuration(int x, int y , int z, int heightGen, byte[][][] blockData, int value){
        // FIXME: 5/21/2023 FIX.
        if (y == heightGen) {
            if (randomXS128.nextInt(MaxRandomization) > value){
                blockData[x][y][z] = BlockRegister.blocks[BlockRegister.DIRT].id;
            }else {
                blockData[x][y][z] = BlockRegister.blocks[BlockRegister.GRASS].id;
                if (checkDataException(x,y,z,blockData)){
                    blockData[x][y][z] = BlockRegister.blocks[BlockRegister.GRASS].id;
                }
            }
        }
    }
    public void buildRandomGrassPatch(int x, int y , int z, int heightGen, byte[][][] blockData, int value){
        if (y == heightGen) {
            if (randomXS128.nextInt(MaxRandomization) > value){
                blockData[x][y][z] = BlockRegister.blocks[BlockRegister.DIRT].id;
            }else {
                blockData[x][y][z] = BlockRegister.blocks[BlockRegister.GRASS].id;
            }
        }
    }

    public void buildDirtLayer(int x, int y , int z, int heightGen, byte[][][] blockData){
        if (y < heightGen && y >= heightGen - 8) {
            blockData[x][y][z] = BlockRegister.blocks[BlockRegister.DIRT].id;
        }
    }

    public void buildStoneLayer(int x, int y , int z, int heightGen, byte[][][] blockData){
        if (y <= heightGen - 8 && y > 0) {
            blockData[x][y][z] = BlockRegister.blocks[BlockRegister.STONE].id;
        }
    }

    public void buildBedRockLayer(int x, int y , int z, int heightGen, byte[][][] blockData){
        if (y  < 2) {
            blockData[x][y][z] = BlockRegister.blocks[BlockRegister.BED_ROCK].id;
        }
    }

    public void buildRockyHillLayer(int x, int y , int z, int heightGen, byte[][][] blockData){
        if (y > 80 && y >= heightGen-2) {
            RandomXS128 randomXS128 = new RandomXS128();

            if (y >= heightGen-5) {
                blockData[x][y][z] = BlockRegister.blocks[BlockRegister.DIRT].id;
            }
            if (randomXS128.nextInt(100) > 65) {
                blockData[x][y][z] = BlockRegister.blocks[BlockRegister.STONE].id;
            }
            else {
                buildGrassLayer(x,y,z,heightGen,blockData);
                buildDirtLayer(x,y,z,heightGen,blockData);
            }
        }
    }
    private boolean checkDataException(int x,int y,int z,byte[][][] data) {
        if (data != null) {
            try {
                byte dat = data[x][y][z];
                if (dat == 0) {
                    return true;
                }
            } catch (Exception exception) {
                return false;
            }
        }
        return false;
    }
}
