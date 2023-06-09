package com.dawnfall.engine.mesh;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.dawnfall.engine.gen.Blocks.Block;
import com.dawnfall.engine.gen.Blocks.BlockProperties.BlockType;
import com.dawnfall.engine.gen.Blocks.BlockRegister;
import com.dawnfall.engine.gen.Chunk;
import com.dawnfall.engine.gen.ChunkManager;
import com.dawnfall.engine.gen.multithread.ChunkBuilder;
import com.dawnfall.engine.rendering.Texture.BlockTextureAtlas;

import static com.dawnfall.engine.gen.Blocks.BlockRegister.*;

public class ChunkMeshBuilder implements Disposable {

    final int maskSize = 16-1;
    public ChunkMeshFace chunkFace;
    private ModelBuilder modelBuilder;
    private Model model;
    public MeshPartBuilder meshBuilder;
    TextureRegion assignTextureRegion;
    private ModelInstance instance;
    public BlockType[][][] blockTypes;
    public final Chunk chunk;
    private final ChunkManager chunkManager;
    private final Material material = new Material(
            new TextureAttribute(TextureAttribute.createDiffuse(BlockTextureAtlas.getLoadRegion()
            )));
    private final ChunkMeshFaceBuilder faceBuilder;
    private final ChunkBuilder.ChunkSides chunkSides;
    public ChunkMeshBuilder(Chunk chunk, ChunkBuilder.ChunkSides chunkSides,ChunkManager chunkManager){
        this.chunkSides = chunkSides;
        this.chunkManager = chunkManager;

        blockTypes = new BlockType[(int) Chunk.size.x][(int) Chunk.size.y][(int) Chunk.size.z];
        chunkFace = new ChunkMeshFace();
        this.chunk = chunk;
        faceBuilder = new ChunkMeshFaceBuilder(this);
    }
    public void createChunkMesh(){
        calculateBlockTypes();
        modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        meshBuilder =  modelBuilder.part("part1",GL20.GL_TRIANGLES,VertexAttributes.Usage.Position |
                VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates, material);
        //build one or more nodes
        for (int x = 0; x< Chunk.size.x; x++) {
            for (int y = 0; y < Chunk.size.y; y++) {
                for (int z = 0; z < Chunk.size.z; z++) {
                    final byte id = chunk.blockData[x][y][z];
                    if (id == AIR) continue;
                    final Block block = BlockRegister.blocks[id];
                    // FIXME: 5/6/2023 Creating the new Chunk meshing engine.
                    // check south Z-
                    if (z-1 == -1){
                        if (chunkSides.south != null){
                            if (canAddFace(block,chunkSides.south[x][y][z+maskSize])){
                                faceBuilder.buildBack(x,y,z);
                            }
                        }
                    } else {
                        if (chunk.getBlock(x,y,z-1) == 0) {
                            faceBuilder.buildBack(x,y,z);
                        }
                    }
                    // check north Z+
                    if (z+1 == 16){
                        if (chunkSides.north != null){
                           if (canAddFace(block,chunkSides.north[x][y][z-maskSize])){
                               faceBuilder.buildFront(x,y,z);
                           }
                        }
                    } else {
                        if (chunk.getBlock(x, y, z +1) == 0) {
                            faceBuilder.buildFront(x, y, z);
                        }
                    }
                    // check west X-
                    if (x-1 == -1){
                        if (chunkSides.west != null){
                            if (canAddFace(block,chunkSides.west[x+maskSize][y][z])){
                                faceBuilder.buildLeft(x,y,z);
                            }
                        }
                    } else {
                        if (chunk.getBlock(x - 1, y, z) == 0) {
                            faceBuilder.buildLeft(x, y, z);
                        }
                    }
                    // check east X+
                    if (x+1 == 16){
                        if (chunkSides.east != null){
                            if (canAddFace(block,chunkSides.east[x-maskSize][y][z])){
                                faceBuilder.buildRight(x,y,z);
                            }
                        }
                    } else {
                        if (chunk.getBlock(x + 1, y, z) == 0) {
                            faceBuilder.buildRight(x, y, z);
                        }
                    }
                    if (chunk.getBlock(x,y+1,z) == 0) {
                        faceBuilder.buildTop(x,y,z);
                    }
                    if (chunk.getBlock(x,y-1,z) == 0 ) {
                        faceBuilder.buildBottom(x,y,z);
                    }
                }
            }
        }
    }

    // FIXME: 5/6/2023 FIX.
    public boolean canAddFace(Block block, int id) {
        if (id == AIR) return true;

        final Block secondary = blocks[id];
        if (block.isSoild == secondary.isSoild)
            return false;
        return block.isSoild; // primary is solid and secondary is trans.
    }
    public boolean isInChunkData(Vector2 coordinates){
       return chunkManager.ChunkData.containsKey(coordinates);
    }
    public void buildChunk(){
        model = modelBuilder.end();
        instance = new ModelInstance(model);
    }
    public int getBlock(int x,int y,int z){
        try {
            if (blockTypes[x][y][z] == BlockType.AIR) {
                return 0;
            }
            return 1;
        }catch (Exception exception){
            return 0;
        }
    }
    /** Calculates the block types */
    public void calculateBlockTypes(){
        for (int x = 0; x<= Chunk.size.x; x++){
            for (int y = 0; y<= Chunk.size.y; y++){
                for (int z = 0; z<= Chunk.size.z; z++){
                    byte id =  chunk.getBlock(x,y,z);

                    if (id == blocks[AIR].id){
                        setBlockType(x,y,z,BlockType.AIR);
                    }
                    if (id == blocks[GRASS].id){
                        setBlockType(x,y,z,BlockType.GRASS);
                    }
                    if (id == blocks[DIRT].id){
                        setBlockType(x,y,z,BlockType.SOIL);
                    }
                    if (id == blocks[STONE].id){
                        setBlockType(x,y,z,BlockType.STONE);
                    }
                    if (id == blocks[BED_ROCK].id){
                        setBlockType(x,y,z,BlockType.BED_ROCK);
                    }
                }
            }
        }
    }

    public void setBlockType(int x,int y,int z,BlockType type){
        if (x < 0 || y < 0 || z < 0 || x > 15 || y > 255 || z > 15)
            return;

        blockTypes[x][y][z] = type;
    }
    public  BlockType getBlockType(int x,int y,int z){
        try {
            if (blockTypes[x][y][z] == BlockType.AIR){
                return BlockType.AIR;
            } else if (blockTypes[x][y][z] == BlockType.STONE) {
                return BlockType.STONE;
            } else if (blockTypes[x][y][z] == BlockType.SOIL) {
                return BlockType.SOIL;
            }else if (blockTypes[x][y][z] == BlockType.GRASS) {
                return BlockType.GRASS;
            }else if (blockTypes[x][y][z] == BlockType.BED_ROCK) {
                return BlockType.BED_ROCK;
            } else if (blockTypes[x][y][z] == BlockType.WATER) {
                return BlockType.WATER;
            }
        }catch (Exception exception){
            return BlockType.AIR;
        }
        return BlockType.AIR;
    }

    public Model getModel() {
        return model;
    }
    public ModelInstance getInstance() {
        return instance;
    }
    public ChunkMeshFace getChunkFace() {
        return chunkFace;
    }

    @Override
    public void dispose() {
        model.dispose();
    }
}
