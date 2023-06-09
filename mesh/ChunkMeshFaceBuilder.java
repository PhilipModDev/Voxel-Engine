package com.dawnfall.engine.mesh;

import com.dawnfall.engine.gen.Blocks.BlockProperties.BlockMaterials;
import com.dawnfall.engine.gen.Blocks.BlockProperties.BlockType;
import com.dawnfall.engine.gen.Blocks.BlockRegister;

public class ChunkMeshFaceBuilder {
    private final ChunkMeshBuilder chunkMeshBuilder;
    private final ChunkMeshFace chunkFace;

    public ChunkMeshFaceBuilder(ChunkMeshBuilder chunkMeshBuilder){
        chunkFace = new ChunkMeshFace();
      this.chunkMeshBuilder = chunkMeshBuilder;
    }

    public void buildRight(int x, int y, int z){

        if (chunkMeshBuilder.chunk.getBlock(x,y,z)== BlockRegister.WATER){
            BlockMaterials<BlockType> blockMaterials = new BlockMaterials<>(BlockType.WATER);
            chunkMeshBuilder.assignTextureRegion = blockMaterials.getFullBlockMaterial();
        }
        if(chunkMeshBuilder.chunk.getBlock(x,y,z)== BlockRegister.GRASS){
            BlockMaterials<BlockType> blockMaterials = new BlockMaterials<>(BlockType.GRASS);
            chunkMeshBuilder.assignTextureRegion = blockMaterials.getBlockSideMaterial(BlockMaterials.FaceSide.SIDE);
        }
        if(chunkMeshBuilder.chunk.getBlock(x,y,z)== BlockRegister.STONE) {
            BlockMaterials<BlockType> blockMaterials = new BlockMaterials<>(BlockType.STONE);
            chunkMeshBuilder.assignTextureRegion = blockMaterials.getFullBlockMaterial();
        }
        if (chunkMeshBuilder.chunk.getBlock(x,y,z) == BlockRegister.DIRT) {
            BlockMaterials<BlockType> blockMaterials = new BlockMaterials<>(BlockType.SOIL);
            chunkMeshBuilder.assignTextureRegion = blockMaterials.getFullBlockMaterial();
        }
        if (chunkMeshBuilder.chunk.getBlock(x,y,z) == BlockRegister.BED_ROCK) {
            BlockMaterials<BlockType> blockMaterials = new BlockMaterials<>(BlockType.BED_ROCK);
            chunkMeshBuilder.assignTextureRegion = blockMaterials.getFullBlockMaterial();
        }
        //Correct facing.
        chunkFace.rightFace(x, y, z, chunkMeshBuilder.meshBuilder, chunkMeshBuilder.assignTextureRegion);
    }

    public void buildLeft(int x, int y, int z){
        if (chunkMeshBuilder.chunk.getBlock(x,y,z)== BlockRegister.WATER){
            BlockMaterials<BlockType> blockMaterials = new BlockMaterials<>(BlockType.WATER);
            chunkMeshBuilder.assignTextureRegion = blockMaterials.getFullBlockMaterial();
            chunkFace.leftFace(x,y,z,chunkMeshBuilder.meshBuilder, chunkMeshBuilder.assignTextureRegion);
        }
        if(chunkMeshBuilder.chunk.getBlock(x,y,z)== BlockRegister.GRASS){
            BlockMaterials<BlockType> blockMaterials = new BlockMaterials<>(BlockType.GRASS);
            chunkMeshBuilder.assignTextureRegion = blockMaterials.getBlockSideMaterial(BlockMaterials.FaceSide.SIDE);
            chunkFace.leftFace(x,y,z,chunkMeshBuilder.meshBuilder, chunkMeshBuilder.assignTextureRegion);
        }
        if(chunkMeshBuilder.chunk.getBlock(x,y,z)== BlockRegister.STONE){
            BlockMaterials<BlockType> blockMaterials = new BlockMaterials<>(BlockType.STONE);
            chunkMeshBuilder.assignTextureRegion = blockMaterials.getFullBlockMaterial();
            chunkFace.leftFace(x,y,z,chunkMeshBuilder.meshBuilder, chunkMeshBuilder.assignTextureRegion);
        }
        if (chunkMeshBuilder.chunk.getBlock(x,y,z)== BlockRegister.DIRT) {
            BlockMaterials<BlockType> blockMaterials = new BlockMaterials<>(BlockType.SOIL);
            chunkMeshBuilder.assignTextureRegion = blockMaterials.getFullBlockMaterial();
            chunkFace.leftFace(x,y,z,chunkMeshBuilder.meshBuilder, chunkMeshBuilder.assignTextureRegion);
        }
        if (chunkMeshBuilder.chunk.getBlock(x,y,z)== BlockRegister.BED_ROCK) {
            BlockMaterials<BlockType> blockMaterials = new BlockMaterials<>(BlockType.BED_ROCK);
            chunkMeshBuilder.assignTextureRegion = blockMaterials.getFullBlockMaterial();
        }
        //Correct facing.
        chunkFace.leftFace(x,y,z,chunkMeshBuilder.meshBuilder, chunkMeshBuilder.assignTextureRegion);
    }
    public void buildFront(int x, int y, int z){
        if (chunkMeshBuilder.chunk.getBlock(x,y,z)== BlockRegister.WATER){

            BlockMaterials<BlockType> blockMaterials = new BlockMaterials<>(BlockType.WATER);
            chunkMeshBuilder.assignTextureRegion = blockMaterials.getFullBlockMaterial();
        }
        if(chunkMeshBuilder.chunk.getBlock(x,y,z)== BlockRegister.GRASS){
            BlockMaterials<BlockType> blockMaterials = new BlockMaterials<>(BlockType.GRASS);
            chunkMeshBuilder.assignTextureRegion = blockMaterials.getBlockSideMaterial(BlockMaterials.FaceSide.SIDE);
        }
        if(chunkMeshBuilder.chunk.getBlock(x,y,z)== BlockRegister.STONE){
            BlockMaterials<BlockType> blockMaterials = new BlockMaterials<>(BlockType.STONE);
            chunkMeshBuilder.assignTextureRegion = blockMaterials.getFullBlockMaterial();
        }
        if (chunkMeshBuilder.chunk.getBlock(x,y,z)== BlockRegister.DIRT) {
            BlockMaterials<BlockType> blockMaterials = new BlockMaterials<>(BlockType.SOIL);
            chunkMeshBuilder.assignTextureRegion = blockMaterials.getFullBlockMaterial();
        }
        if (chunkMeshBuilder.chunk.getBlock(x,y,z)== BlockRegister.BED_ROCK) {
            BlockMaterials<BlockType> blockMaterials = new BlockMaterials<>(BlockType.BED_ROCK);
            chunkMeshBuilder.assignTextureRegion = blockMaterials.getFullBlockMaterial();
        }
        chunkFace.frontFace(x,y,z,chunkMeshBuilder.meshBuilder, chunkMeshBuilder.assignTextureRegion);
        //Correct facing.
    }
    public void buildBack(int x, int y, int z){
        if (chunkMeshBuilder.chunk.getBlock(x,y,z)== BlockRegister.WATER){

            BlockMaterials<BlockType> blockMaterials = new BlockMaterials<>(BlockType.WATER);
            chunkMeshBuilder.assignTextureRegion = blockMaterials.getFullBlockMaterial();
        }
        if(chunkMeshBuilder.chunk.getBlock(x,y,z)== BlockRegister.GRASS){
            BlockMaterials<BlockType> blockMaterials = new BlockMaterials<>(BlockType.GRASS);
            chunkMeshBuilder.assignTextureRegion = blockMaterials.getBlockSideMaterial(BlockMaterials.FaceSide.SIDE);
        }
        if(chunkMeshBuilder.chunk.getBlock(x,y,z)== BlockRegister.STONE){
            BlockMaterials<BlockType> blockMaterials = new BlockMaterials<>(BlockType.STONE);
            chunkMeshBuilder.assignTextureRegion = blockMaterials.getFullBlockMaterial();
        }
        if (chunkMeshBuilder.chunk.getBlock(x,y,z)== BlockRegister.DIRT) {
            BlockMaterials<BlockType> blockMaterials = new BlockMaterials<>(BlockType.SOIL);
            chunkMeshBuilder.assignTextureRegion = blockMaterials.getFullBlockMaterial();
        }
        if (chunkMeshBuilder.chunk.getBlock(x,y,z)== BlockRegister.BED_ROCK) {
            BlockMaterials<BlockType> blockMaterials = new BlockMaterials<>(BlockType.BED_ROCK);
            chunkMeshBuilder.assignTextureRegion = blockMaterials.getFullBlockMaterial();
        }
        chunkFace.backFace(x,y,z,chunkMeshBuilder.meshBuilder,chunkMeshBuilder.assignTextureRegion);
    }
    public void buildTop(int x, int y, int z){
        if (chunkMeshBuilder.chunk.getBlock(x,y,z)== BlockRegister.WATER){

            BlockMaterials<BlockType> blockMaterials = new BlockMaterials<>(BlockType.WATER);
            chunkMeshBuilder.assignTextureRegion = blockMaterials.getFullBlockMaterial();
        }
        if(chunkMeshBuilder.chunk.getBlock(x,y,z)== BlockRegister.GRASS){
            BlockMaterials<BlockType> blockMaterials = new BlockMaterials<>(BlockType.GRASS);
            chunkMeshBuilder.assignTextureRegion = blockMaterials.getBlockSideMaterial(BlockMaterials.FaceSide.TOP);
        }
        if(chunkMeshBuilder.chunk.getBlock(x,y,z)== BlockRegister.STONE){
            BlockMaterials<BlockType> blockMaterials = new BlockMaterials<>(BlockType.STONE);
            chunkMeshBuilder.assignTextureRegion = blockMaterials.getFullBlockMaterial();
        }
        if (chunkMeshBuilder.chunk.getBlock(x,y,z)== BlockRegister.DIRT) {
            BlockMaterials<BlockType> blockMaterials = new BlockMaterials<>(BlockType.SOIL);
            chunkMeshBuilder.assignTextureRegion = blockMaterials.getFullBlockMaterial();
        }
        if (chunkMeshBuilder.chunk.getBlock(x,y,z)== BlockRegister.BED_ROCK) {
            BlockMaterials<BlockType> blockMaterials = new BlockMaterials<>(BlockType.BED_ROCK);
            chunkMeshBuilder.assignTextureRegion = blockMaterials.getFullBlockMaterial();
        }
        //Correct facing.
        chunkFace.topFace(x,y,z,chunkMeshBuilder.meshBuilder,chunkMeshBuilder.assignTextureRegion);
    }
    public void buildBottom(int x, int y, int z){
        if (chunkMeshBuilder.chunk.getBlock(x,y,z)== BlockRegister.WATER){
            BlockMaterials<BlockType> blockMaterials = new BlockMaterials<>(BlockType.WATER);
            chunkMeshBuilder.assignTextureRegion = blockMaterials.getFullBlockMaterial();
        }
        if(chunkMeshBuilder.chunk.getBlock(x,y,z)== BlockRegister.GRASS){
            BlockMaterials<BlockType> blockMaterials = new BlockMaterials<>(BlockType.GRASS);
            chunkMeshBuilder.assignTextureRegion = blockMaterials.getBlockSideMaterial(BlockMaterials.FaceSide.BOTTOM);
        }
        if(chunkMeshBuilder.chunk.getBlock(x,y,z)== BlockRegister.STONE){
            BlockMaterials<BlockType> blockMaterials = new BlockMaterials<>(BlockType.STONE);
            chunkMeshBuilder.assignTextureRegion = blockMaterials.getFullBlockMaterial();
        }
        if (chunkMeshBuilder.chunk.getBlock(x,y,z)== BlockRegister.DIRT) {
            BlockMaterials<BlockType> blockMaterials = new BlockMaterials<>(BlockType.SOIL);
            chunkMeshBuilder.assignTextureRegion = blockMaterials.getFullBlockMaterial();
        }
        if (chunkMeshBuilder.chunk.getBlock(x,y,z)== BlockRegister.BED_ROCK) {
            BlockMaterials<BlockType> blockMaterials = new BlockMaterials<>(BlockType.BED_ROCK);
            chunkMeshBuilder.assignTextureRegion = blockMaterials.getFullBlockMaterial();
        }
        //Correct facing.
        chunkFace.bottomFace(x,y,z,chunkMeshBuilder.meshBuilder,chunkMeshBuilder.assignTextureRegion);
    }
}
