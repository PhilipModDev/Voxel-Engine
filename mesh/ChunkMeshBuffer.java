package com.dawnfall.engine.mesh;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.dawnfall.engine.gen.ChunkManager;
import com.dawnfall.engine.gen.multithread.ChunkBuilder;
import java.util.*;

public class ChunkMeshBuffer {
    private final Array<ModelInstance> buffer;
    private final ChunkManager chunkManager;
    public ChunkMeshBuffer(ChunkManager chunkManager){
        this.chunkManager = chunkManager;
        buffer = new Array<>();
    }
    public void addToChunkBuffer(HashMap<Vector2,ModelInstance> chunks){
        buffer.clear();
        Set<Map.Entry<Vector2, ModelInstance>> chunkSet = chunks.entrySet();
        for (Map.Entry<Vector2, ModelInstance> instanceEntry : chunkSet) {
            buffer.add(instanceEntry.getValue());
        }
        ChunkManager.setProcessedChunks(1);
    }
    public void updateBuffer(){
        try {
            // FIXME: 6/5/2023 Update the worker thread.
           ChunkBuilder chunkBuilder = chunkManager.BuiltChunks.poll();
           if (chunkBuilder != null) {
               chunkBuilder.end();
               ChunkManager.setProcessedChunks(1);
           } else {
               chunkManager.updateWorkerThread = true;
       }

        }catch (Exception exception){
            exception.printStackTrace();
            chunkManager.updateWorkerThread = false;
            Gdx.app.error("Render Chunks","Builder chunk overload, main thread can't keep up.");
        }
    }
    public Array<ModelInstance> getBuffer() {
        chunkManager.buffer.updateBuffer();
        return buffer;
    }
}
