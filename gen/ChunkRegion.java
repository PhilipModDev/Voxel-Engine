package com.dawnfall.engine.gen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.dawnfall.engine.Client.MainGame;
import com.dawnfall.engine.Client.MainRenderer;
import com.dawnfall.engine.gen.Blocks.Block;
import com.dawnfall.engine.gen.Blocks.BlockRegister;
import com.dawnfall.engine.gen.World.World;
import com.dawnfall.engine.handle.RayCast;
import com.dawnfall.engine.util.BlockPos;

import static com.dawnfall.engine.Client.MainRenderer.player;
import static com.dawnfall.engine.gen.Blocks.BlockRegister.blocks;

public class ChunkRegion {
    private final ChunkManager chunkManager;
    private final World world;
    public static int LENGTH;
    public static final int CHUNK_SIZE_X = (int) Chunk.size.x;
    public static final int CHUNK_SIZE_Y = (int) Chunk.size.y;
    public static final int CHUNK_SIZE_Z = (int) Chunk.size.z;
    public ChunkRegion(World world,ChunkManager chunkManager,int length){
         LENGTH = length;
         this.world = world;
         this.chunkManager = chunkManager;
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
    public static class BlockEdit {
        private final World world;
        public BlockEdit(final World world) {
            this.world = world;
        }

        public void breakBlock(final BlockPos in, final Block block) {
            world.setBlock(in.x,in.y,in.z,block.id);
        }

        public void placeBlock(final BlockPos out, final Block block) {
            world.setBlock(out.x,out.y,out.z,block.id);
        }
        public void placeBlock(final Vector3 out, final Block block) {
            world.setBlock((int) out.x, (int) out.y, (int) out.z,block.id);
        }
        public void setBlock(int x, int y, int z,byte block,byte[][][] data){
            if (data != null) {
                setFast(x % CHUNK_SIZE_X, y, z % CHUNK_SIZE_Z, data, block);
            }
        }
        public byte getBlock(int x, int y, int z,byte[][][] data){
            if (data != null) {
                return getFast( x % CHUNK_SIZE_X, y, z % CHUNK_SIZE_Z , data);
            }
            return BlockRegister.AIR;
        }
        public void setFast(int x,int y, int z,byte[][][] data,byte block){
             int xi = Math.abs(x);
             int zi = Math.abs(z);
            data[xi][y][zi] = block;
        }
        public byte getFast(int x,int y, int z,byte[][][] data){
            int xi = Math.abs(x);
            int zi = Math.abs(z);
            return data[xi][y][zi];
        }


        public void update() {
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
                            Chunk chunk = new Chunk(new Vector2(rayX>>4,rayZ>>4));
                            world.blockEdit.breakBlock(ray.in, blocks[BlockRegister.AIR]);
                            RayCast.ray(player);
                            world.chunkManager.DirtyChunks.add(chunk);
                            if (world.chunkManager.DirtyChunks.peek() != null) {
                                world.chunkManager.DirtyChunks.peek().isDirty = true;
                                ChunkManager.dirty++;
                            }
                        }
                        if (placeBlock) {
//                            int rayX = ray.out.x;
//                            int rayZ = ray.out.z;
                            int rayX = (int) ChunkManager.playerCoordinates.x;
                            int rayZ = (int) ChunkManager.playerCoordinates.y;
                            Chunk chunk = new Chunk(new Vector2(rayX>>4,rayZ>>4));
                            world.blockEdit.placeBlock(new Vector3(rayX, player.getPlayerPosY(),rayZ), blocks[BlockRegister.STONE]);
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
}
