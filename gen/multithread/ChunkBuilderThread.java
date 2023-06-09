package com.dawnfall.engine.gen.multithread;

import com.badlogic.gdx.Gdx;
import com.dawnfall.engine.Client.MainGame;
import com.dawnfall.engine.Client.features.OptionsProperties.Options;
import com.dawnfall.engine.Blocknet.Server.Server;
import com.dawnfall.engine.gen.ChunkManager;

public class ChunkBuilderThread implements Runnable {
    private final ChunkManager chunkManager;
    private boolean portServer;
    public Server server;
    public ChunkBuilderThread(ChunkManager chunkManager) {
        server = new Server();
        this.chunkManager = chunkManager;
    }
    @Override
    public void run() {
        synchronized (this){
            try{
                //Render the client
                while (Gdx.graphics.isContinuousRendering()) {
                    if (chunkManager.updateWorkerThread) {
                        if (Options.NetworkResource.equals(Options.NetworkSides.CLIENT)) {
                            // FIXME: 5/20/2023 TEST.
                            chunkManager.updateChunkPosition();
                            chunkManager.updateDirtyChunks();
                            chunkManager.update();
                        }
                        if (Options.NetworkResource.equals(Options.NetworkSides.SERVER)) {
                            //Port on Server.
                            if (!portServer) {
                                MainGame.eventRegister.serverPortEvent.register();
                                server.StartServer();
                                portServer = true;
                                Thread.sleep(100);
                            }
                            chunkManager.updatePos();
                            chunkManager.updateDirtyChunks();
                            chunkManager.update();
                        }
                        chunkManager.updateWorkerThread = false;
                        Thread.sleep(100);
                    }
                }
            }catch (Exception exception){
                exception.printStackTrace();
                new Thread(this).start();
            }
        }
    }
}
