package com.dawnfall.engine.gen.multithread;

import com.badlogic.gdx.Gdx;
import com.dawnfall.engine.Client.MainGame;
import com.dawnfall.engine.Client.features.OptionsProperties.Options;
import com.dawnfall.engine.Blocknet.Server.Server;
import com.dawnfall.engine.gen.ChunkManager;
import com.dawnfall.engine.util.libUtil;
import java.util.concurrent.ForkJoinPool;

public class ChunkBuilderThread implements Runnable {
    private final ChunkManager chunkManager;
    private boolean portServer;
    private final ForkJoinPool forkJoinPool;
    public final byte clientId = (byte) libUtil.getRandomNumber(500);
    public Server server;
    public ChunkBuilderThread(ChunkManager chunkManager,int pLevel) {
        server = new Server();
        this.chunkManager = chunkManager;
        forkJoinPool = new ForkJoinPool(pLevel);
    }
    @Override
    public void run() {
        BuilderPacket();
    }
    private void BuilderPacket(){
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
