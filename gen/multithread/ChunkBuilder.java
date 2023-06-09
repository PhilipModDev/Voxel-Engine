package com.dawnfall.engine.gen.multithread;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.dawnfall.engine.gen.Chunk;
import com.dawnfall.engine.gen.ChunkManager;
import com.dawnfall.engine.mesh.ChunkMeshBuilder;

public class ChunkBuilder {
    private Vector2 chunkCoordinates;
    private final ChunkManager chunkManager;
    public Chunk chunk;
    public ChunkMeshBuilder chunkMeshBuilder;
    private ModelInstance chunkInstance;
    public ChunkBuilder(ChunkManager chunkManager){
        this.chunkManager = chunkManager;
    }
    public void build(Chunk chunk){
        chunkCoordinates = chunk.chunkCoordinates;
        chunk.setData(chunkManager.ChunkData.get(chunkCoordinates));
        chunk.hasData = true;
        chunkMeshBuilder = new ChunkMeshBuilder(chunk,new ChunkSides(
                chunkManager.NorthChunk,
                chunkManager.SouthChunk,
                chunkManager.EastChunk,
                chunkManager.WestChunk),
                chunkManager);
        chunkMeshBuilder.createChunkMesh();
        chunk.isChunkSafe = true;
        this.chunk = chunk;
        chunkManager.BuiltChunks.add(this);
    }
    public void update(Chunk chunk){
        if (chunk.isDirty) {
            chunk.isDirty = false;
            chunkCoordinates = chunk.chunkCoordinates;
            chunk.setData(chunkManager.ChunkData.get(chunkCoordinates));
            //Set the chunk coordinates.
            chunkCoordinates = chunk.chunkCoordinates;
            if (chunkManager.hasNeighbours(chunk.chunkCoordinates)) {
                //Creates the mesh.
                chunkMeshBuilder = new ChunkMeshBuilder(chunk,new ChunkSides(
                        chunkManager.NorthChunk,
                        chunkManager.SouthChunk,
                        chunkManager.EastChunk,
                        chunkManager.WestChunk),
                        chunkManager
                );
                chunkMeshBuilder.createChunkMesh();
                // adds it to the Queue.
                chunk.isChunkSafe = true;
                this.chunk = chunk;
                chunkManager.BuiltChunks.add(this);
            }
        }
    }
    public void end(){
        //Builds the finish chunk for render.
        chunkMeshBuilder.buildChunk();
        chunkMeshBuilder.getInstance().transform.setToTranslation(new Vector3((int) chunkCoordinates.x<<4, 0, (int) chunkCoordinates.y<<4));
        chunkInstance = chunkMeshBuilder.getInstance();
        chunkManager.LoadedChunks.put(chunkCoordinates,chunkMeshBuilder.getInstance());
        chunkManager.buffer.addToChunkBuffer(chunkManager.LoadedChunks);
    }

    public ModelInstance getChunkInstance() {
        return chunkInstance;
    }

    public static class ChunkSides {
       public byte[][][] north;
       public byte[][][] south;
       public byte[][][] east;
       public byte[][][] west;

       public ChunkSides(byte[][][] n,byte[][][] s,byte[][][] e,byte [][][] w){
         this.north = n;
         this.south = s;
         this.west = w;
         this.east = e;
       }
    }
}
