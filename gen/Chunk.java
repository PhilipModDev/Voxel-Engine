package com.dawnfall.engine.gen;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.dawnfall.engine.gen.Blocks.BlockRegister;


public class Chunk {
    public static final Vector3 size = new Vector3(16,256,16);
    public byte[][][] blockData;
    /** Is this chunk needs update their mesh. */
    public boolean isDirty = false;
    /** Is this a new unloaded chunk. Then build the chunk model when player  */
    public boolean isNewChunk = true;
    /** Is this chunk safe to modify blocks. */
    public boolean hasData = false;
    public volatile boolean isChunkSafe = false;
    public boolean visible = true;
    public Vector2 chunkCoordinates;

    public Chunk(Vector2 chunkCoordinates) {
        this.chunkCoordinates = chunkCoordinates;
        //Builds the voxel chunk.
        blockData = new byte[(int) size.x][(int) size.y][(int) size.z];
    }
    public byte getBlock(int x, int y, int z) {
        if (x < 0 || y < 0 || z < 0 || x > 15 || y > 255|| z > 15)
            return BlockRegister.AIR;

        return blockData[x][y][z];
    }

    public boolean isAtBorder(int x, int y, int z){
        return x <= 0 || y <= 0 || z <= 0 || x >= 15 || y >= 255 || z >= 15;
    }
    public ChunkSides getChunkSide(int x, int y, int z){
       if (z-1 == -1){
           return ChunkSides.BACK;
       }
       if (z+1 == size.y){
           return ChunkSides.FRONT;
       }
       if (x+1 == size.y){
           return ChunkSides.RIGHT;
       }
       if (x-1 == -1){
           return ChunkSides.LEFT;
       }
       if (y+1 == size.y){
           return ChunkSides.TOP;
       }
       return ChunkSides.BOTTOM;
    }
    public void setBlock(int x, int y, int z, byte ID) {
        if (x < 0 || y < 0 || z < 0 || x > 15 || y > 15 || z > 15)
            return;

        blockData[x][y][z] = ID;
    }
    public void clear() {
        for (int x = 0; x < size.x; x++)
        {
            for (int y = 0; y < size.y; y++)
            {
                for (int z = 0; z < size.z; z++)
                {
                    blockData[x][y][z] = BlockRegister.AIR;
                }
            }
        }
    }
    public byte[][][] getBlockData() {
        return blockData;
    }

    public boolean isChunkSafe() {
        return isChunkSafe;
    }

    public boolean isDirty() {
        return isDirty;
    }

    public boolean isNewChunk() {
        return isNewChunk;
    }
    public void setData(byte[][][] blockData) {
        this.blockData = blockData;
    }

    public void setBlockData(byte[][][] blockData) {
        this.blockData = blockData;
    }
}
