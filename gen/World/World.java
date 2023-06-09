package com.dawnfall.engine.gen.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.dawnfall.engine.Client.MainGame;
import com.dawnfall.engine.Client.MainRenderer;
import com.dawnfall.engine.gen.Blocks.Block;
import com.dawnfall.engine.gen.Blocks.BlockRegister;
import com.dawnfall.engine.gen.Chunk;
import com.dawnfall.engine.gen.ChunkManager;
import com.dawnfall.engine.handle.RayCast;
import com.dawnfall.engine.util.BlockPos;
import com.dawnfall.engine.util.libUtil;

import static com.dawnfall.engine.Client.MainRenderer.player;
import static com.dawnfall.engine.gen.Blocks.BlockRegister.blocks;

public class World {

    public static World world;
    private static final int default_size = 15;
    public final static int WORLD_SIZE = default_size*16;
    public int RenderDistance = 14;
    public ChunkManager chunkManager;
    public Array<ModelInstance> chunks;
    private static int tickUpdate = 0;
    public boolean isWorldLoaded = false;
    public static final int CHUNK_SIZE_X = (int) Chunk.size.x;
    public static final int CHUNK_SIZE_Y = (int) Chunk.size.y;
    public static final int CHUNK_SIZE_Z = (int) Chunk.size.z;
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
        return chunk == null ? BlockRegister.AIR : getBlock(x,y,z,chunk);
    }

    public void setBlock(int x,int y, int z,byte block){
        byte[][][] chunk = getChunkAt(x>>4, z>>4);
        if (chunk == null) return;
      setBlock(x,y,z,block,chunk);
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
    public Vector2 findChunkAt(int x, int y){
        //Finds the chunk using x and y coordinates.
        int chunkX = x >>4;
        int chunkZ = y >>4;
        return new Vector2(chunkX,chunkZ);
    }
    public Vector2 getChunkAtPlayer(){
        return findChunkAt((int) chunkManager.player.getPlayerPosX(),
                (int) chunkManager.player.getPlayerPosZ());
    }

    public void breakBlock(final BlockPos in, final Block block) {
        setBlock(in.x,in.y,in.z,block.id);
    }
    public void placeBlock(final BlockPos out, final Block block) {
        setBlock(out.x,out.y,out.z,block.id);
    }
    public void setBlock(int x, int y, int z,byte block,byte[][][] data){
        setFast(x % CHUNK_SIZE_X, y, z % CHUNK_SIZE_Z, data, block);
    }
    public byte getBlock(int x, int y, int z,byte[][][] data){
        return getFast( x % CHUNK_SIZE_X, y, z % CHUNK_SIZE_Z , data);
    }
    public void setFast(int x,int y, int z,byte[][][] data,byte block){
        int xi = Math.abs(x);
        int zi = Math.abs(z);

        if (xi < 0 || xi >= CHUNK_SIZE_X) return;
        if (y < 0 || y >= CHUNK_SIZE_Y) return;
        if (zi < 0 || zi >= CHUNK_SIZE_Z) return;

        data[xi][y][zi] = block;
    }
    public byte getFast(int x,int y, int z,byte[][][] data){
        int xi = Math.abs(x);
        int zi = Math.abs(z);

        if (xi < 0 || xi >= CHUNK_SIZE_X) return BlockRegister.AIR;
        if (y < 0 || y >= CHUNK_SIZE_Y) return BlockRegister.AIR;
        if (zi < 0 || zi >= CHUNK_SIZE_Z) return BlockRegister.AIR;

        return data[xi][y][zi];
    }

    public void updateRayCast() {
        try {
            if (world.chunkManager.ChunkData != null) {
                RayCast.RayInfo ray;
                ray = RayCast.ray(player);
                boolean breakBlock;
                boolean placeBlock;
                if (ray != null) {
                    //Feature controls options.
                    breakBlock = Gdx.input.isButtonJustPressed(Input.Buttons.LEFT);
                    placeBlock = Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT);
                    if (breakBlock) {
                        int rayX = ray.in.x;
                        int rayZ = ray.in.z;
                        Chunk chunk = new Chunk(new Vector2(rayX >> 4, rayZ >> 4));
                        breakBlock(ray.in, blocks[BlockRegister.AIR]);
                        RayCast.ray(player);
                        world.chunkManager.DirtyChunks.add(chunk);
                        if (world.chunkManager.DirtyChunks.peek() != null) {
                            world.chunkManager.DirtyChunks.peek().isDirty = true;
                            ChunkManager.dirty++;
                        }
                    }
                    if (placeBlock) {
                        int rayX = ray.out.x;
                        int rayZ = ray.out.z;

                        Chunk chunk = new Chunk(new Vector2(rayX >> 4, rayZ >> 4));
                        placeBlock(ray.out, blocks[BlockRegister.DIRT]);
                        RayCast.ray(player);
                        world.chunkManager.DirtyChunks.add(chunk);
                        if (world.chunkManager.DirtyChunks.peek() != null) {
                            world.chunkManager.DirtyChunks.peek().isDirty = true;
                            ChunkManager.dirty++;
                        }
                    }
                    MainGame.boxRenderer.render(ray);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
