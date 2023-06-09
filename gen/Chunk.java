package com.dawnfall.engine.gen;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.dawnfall.engine.gen.Blocks.BlockRegister;
import com.dawnfall.engine.util.BlockPos;

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
    public Chunk(BlockPos coordinates) {
        chunkCoordinates = new Vector2(coordinates.x,coordinates.y);
        //Builds the voxel chunk.
        blockData = new byte[(int) size.x][(int) size.y][(int) size.z];
    }
    public byte getBlock(int x, int y, int z) {
        if (x < 0 || x >= Chunk.size.x) return BlockRegister.AIR;
        if (y < 0 || y >= Chunk.size.y) return BlockRegister.AIR;
        if (z < 0 || z >= Chunk.size.z) return BlockRegister.AIR;

        return blockData[x][y][z];
    }

    public boolean isAtBorder(int x, int y, int z){
        if (x < 0 || x >= Chunk.size.x) return true;
        if (y < 0 || y >= Chunk.size.y) return true;
        return z < 0 || z >= Chunk.size.z;
    }
    public void setBlock(int x, int y, int z, byte ID) {
        if (x < 0 || x >= Chunk.size.x) return;
        if (y < 0 || y >= Chunk.size.y) return;
        if (z < 0 || z >= Chunk.size.z) return;
        blockData[x][y][z] = ID;
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
}
