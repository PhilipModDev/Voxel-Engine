package com.dawnfall.engine.gen.World;

import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.dawnfall.engine.Client.MainGame;
import com.dawnfall.engine.Client.MainRenderer;
import com.dawnfall.engine.gen.Blocks.BlockRegister;
import com.dawnfall.engine.gen.ChunkManager;
import com.dawnfall.engine.gen.ChunkRegion;
import com.dawnfall.engine.util.libUtil;

public class World {

    public static World world;
    private static final int default_size = 15;
    public final static int WORLD_SIZE = default_size*16;
    public int RenderDistance = 20;
    public ChunkManager chunkManager;
    public Array<ModelInstance> chunks;
    private static int tickUpdate = 0;
    public boolean isWorldLoaded = false;
    public final ChunkRegion.BlockEdit blockEdit = new ChunkRegion.BlockEdit(this);
    public World(boolean generate) {
         world = this;
        if (generate){
             chunkManager = new ChunkManager(this);
             MainRenderer.player.setWorld(this);
             chunks = new Array<>();
         }
       }
    public void update(){
        if (tickUpdate()){
            chunkManager.updateBuffer();
            chunks = chunkManager.buffer.getBuffer();
            if (chunks.size > 400){
                isWorldLoaded = true;
            }
        }
    }
   public boolean tickUpdate(){
         //Request new chunk every update tick.
      if (tickUpdate == libUtil.milseconds(5)) {
          MainGame.eventRegister.tickEventRegister.register();
          tickUpdate = 0;
          return true;
      }
       tickUpdate++;
       return false;
   }
   public static int getTickUpdate(){
        return tickUpdate;
    }
    public byte[][][] getChunkAt(int x,int z){
        if (chunkManager.ChunkData.containsKey(new Vector2(x,z))){
            return chunkManager.ChunkData.get(new Vector2(x,z));
        }
        return null;
    }

    public byte getBlock(int x,int y, int z){
        byte[][][] chunk = getChunkAt(x>>4, z>>4);
        return chunk == null ? BlockRegister.AIR : blockEdit.getBlock(x,y,z,chunk);
    }

    public void setBlock(int x,int y, int z,byte block){
        byte[][][] chunk = getChunkAt(x>>4, z>>4);
        if (chunk == null) return;
      blockEdit.setBlock(x,y,z,block,chunk);
    }
   public boolean isWorldSize(int x, int y){
       if (x <= WORLD_SIZE && y <= WORLD_SIZE){
           return x >= -WORLD_SIZE && y >= -WORLD_SIZE;
       }
       return false;
   }
    public static World getWorld() {
        return world;
    }
}
