package com.dawnfall.engine.gen.multithread;

import com.dawnfall.engine.gen.ChunkManager;

public class LaunchMultithreadingApplication {
    public ChunkBuilderThread chunkBuilderThread;
    public LaunchMultithreadingApplication(ChunkManager chunkManager){
        chunkBuilderThread = new ChunkBuilderThread(chunkManager,2);
    }
    public void Launch(){
        new Thread(chunkBuilderThread).start();
    }
}
