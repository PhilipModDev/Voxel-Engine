package com.dawnfall.engine.gen;

import com.badlogic.gdx.math.Vector2;
import com.dawnfall.engine.Blocknet.Server.Server;
import com.dawnfall.engine.gen.Blocks.BlockRegister;
import com.dawnfall.engine.util.math.perlinNoise.OpenSimplex2S;

public class ChunkDataGeneration {
    private final ChunkManager chunkManager;
    public boolean Terminate;
    private Vector2 coordinates;
    private final GenerationProperties generationProperties;
    public ChunkDataGeneration(ChunkManager chunkManager) {
        this.chunkManager = chunkManager;
        generationProperties = new GenerationProperties(100);
    }
    public byte[][][] generateData(Vector2 coordinates) {
        while (!Terminate) {
            if (chunkManager.hasNeighbours(coordinates)) {
                ChunkManager.dataSize = chunkManager.ChunkData.size();
                return chunkManager.ChunkData.get(coordinates);
            }
            this.coordinates = coordinates;

            Vector2 NorthCoordinates = new Vector2(coordinates.x, coordinates.y + 1);
            Vector2 SouthCoordinates = new Vector2(coordinates.x, coordinates.y - 1);
            Vector2 EastCoordinates = new Vector2(coordinates.x + 1, coordinates.y);
            Vector2 WestCoordinates = new Vector2(coordinates.x - 1, coordinates.y);
            if (!chunkManager.ChunkData.containsKey(coordinates)){
                createChunkDataGeneration(coordinates);
            }
            if (!chunkManager.ChunkData.containsKey(NorthCoordinates)){
                createChunkDataGeneration(NorthCoordinates);
            }
            if (!chunkManager.ChunkData.containsKey(SouthCoordinates)){
                createChunkDataGeneration(SouthCoordinates);
            }
            if (!chunkManager.ChunkData.containsKey(EastCoordinates)){
                createChunkDataGeneration(EastCoordinates);
            }
            if (!chunkManager.ChunkData.containsKey(WestCoordinates)){
                createChunkDataGeneration(WestCoordinates);
            }
        }
        return null;
    }
    public void createChunkDataGeneration(Vector2 offset) {
        int heightOffset = GenerationProperties.heightOffset;
        float heightIntensity = GenerationProperties.heightIntensity;
        Vector2 NoiseOffset = GenerationProperties.NoiseOffset;
        Vector2 NoiseScale = GenerationProperties.NoiseScale;
        byte[][][] blockData = new byte[(int) Chunk.size.x][(int) Chunk.size.y][(int) Chunk.size.z];
        int noise1, noise2;
        for (int x = 0; x < Chunk.size.x; x++) {
            for (int z = 0; z < Chunk.size.z; z++) {
                float PerlinX = NoiseOffset.x + (x + offset.x * 16f) / Chunk.size.x * NoiseScale.x;
                float PerlinY = NoiseOffset.y + (z + offset.y * 16f) / Chunk.size.z * NoiseScale.y;
                int value = Math.round(
                        OpenSimplex2S.noise2_ImproveX(GenerationProperties.seed, PerlinX / 16.2f, PerlinY / 16.0f) * heightIntensity + heightOffset);
                noise1 = Math.round(
                        OpenSimplex2S.noise2(GenerationProperties.seed, PerlinX / 16.2f, PerlinY / 10.3f) * heightIntensity + heightOffset);
                noise2 = Math.round(
                        OpenSimplex2S.noise2_ImproveX(GenerationProperties.seed, PerlinX / 5.4f, PerlinY / 6.0f) * heightIntensity +heightOffset);
                value = Math.round(value - noise2 + noise1);
                //Fixed to flat.
                // TODO: 6/6/2023 Change it back to value.
                int heightGen = 40;
                for (int y = heightGen; y >= 0; y--) {
                    blockData[x][y][z] = BlockRegister.blocks[BlockRegister.AIR].id;
                    //Grass.
                    generationProperties.buildRandomGrassPatch(x,y,z,heightGen,blockData,90);
                    //Dirt.
                    generationProperties.buildDirtLayer(x,y,z,heightGen,blockData);
                    //Stone.
                    generationProperties.buildStoneLayer(x,y,z,heightGen,blockData);
                    //BedRock.
                    generationProperties.buildBedRockLayer(x,y,z,heightGen,blockData);
                    //Rocky hills.
                    generationProperties.buildRockyHillLayer(x,y,z,heightGen,blockData);
                }
            }
        }
        chunkManager.ChunkData.put(offset,blockData);
    }

    public static class GenerateServerData {
        private final Server server;
        public byte[][][] NorthChunk;
        public byte[][][] SouthChunk;
        public byte[][][] EastChunk;
        public byte[][][] WestChunk;
        public boolean Terminate;
        private final GenerationProperties generationProperties;

        public GenerateServerData(Server server) {
            this.server = server;
            generationProperties = new GenerationProperties(100);
        }

        public byte[][][] getChunkAt(int x,int z){
            if (server.WorldData.containsKey(new Vector2(x,z))){
                return server.WorldData.get(new Vector2(x,z));
            }
            return null;
        }
        public boolean hasNeighbours(Vector2 chunkCoordinates){
            NorthChunk = getChunkAt((int) chunkCoordinates.x, (int) (chunkCoordinates.y+1));
            SouthChunk = getChunkAt((int) chunkCoordinates.x, (int) (chunkCoordinates.y-1));
            EastChunk  = getChunkAt((int) (chunkCoordinates.x+1), (int)chunkCoordinates.y);
            WestChunk  = getChunkAt((int) (chunkCoordinates.x-1),(int) chunkCoordinates.y);
            return NorthChunk != null && SouthChunk != null && EastChunk != null && WestChunk != null;
        }

        public void generateData(Vector2 coordinates) {
            while (!Terminate) {
                if (hasNeighbours(coordinates)) {
                    ChunkManager.dataSize = server.WorldData.size();
                    return;
                }
                Vector2 NorthCoordinates = new Vector2(coordinates.x, coordinates.y + 1);
                Vector2 SouthCoordinates = new Vector2(coordinates.x, coordinates.y - 1);
                Vector2 EastCoordinates = new Vector2(coordinates.x + 1, coordinates.y);
                Vector2 WestCoordinates = new Vector2(coordinates.x - 1, coordinates.y);
                if (!server.WorldData.containsKey(coordinates)){
                    createChunkDataGeneration(coordinates);
                }
                if (!server.WorldData.containsKey(NorthCoordinates)){
                    createChunkDataGeneration(NorthCoordinates);
                }
                if (!server.WorldData.containsKey(SouthCoordinates)){
                    createChunkDataGeneration(SouthCoordinates);
                }
                if (!server.WorldData.containsKey(EastCoordinates)){
                    createChunkDataGeneration(EastCoordinates);
                }
                if (!server.WorldData.containsKey(WestCoordinates)){
                    createChunkDataGeneration(WestCoordinates);
                }
            }
        }
        public void createChunkDataGeneration(Vector2 offset) {
            int heightOffset = GenerationProperties.heightOffset;
            float heightIntensity = GenerationProperties.heightIntensity;
            Vector2 NoiseOffset = GenerationProperties.NoiseOffset;
            Vector2 NoiseScale = GenerationProperties.NoiseScale;
            byte[][][] blockData = new byte[(int) Chunk.size.x][(int) Chunk.size.y][(int) Chunk.size.z];
            int noise1, noise2;
            for (int x = 0; x < Chunk.size.x; x++) {
                for (int z = 0; z < Chunk.size.z; z++) {
                    float PerlinX = NoiseOffset.x + (x + offset.x * 16f) / Chunk.size.x * NoiseScale.x;
                    float PerlinY = NoiseOffset.y + (z + offset.y * 16f) / Chunk.size.z * NoiseScale.y;
                    int value = Math.round(
                            OpenSimplex2S.noise2_ImproveX(GenerationProperties.seed, PerlinX / 16.2f, PerlinY / 16.0f) * heightIntensity + heightOffset);
                    noise1 = Math.round(
                            OpenSimplex2S.noise2(GenerationProperties.seed, PerlinX / 16.2f, PerlinY / 10.3f) * heightIntensity + heightOffset);
                    noise2 = Math.round(
                            OpenSimplex2S.noise2_ImproveX(GenerationProperties.seed, PerlinX / 5.4f, PerlinY / 6.0f) * heightIntensity +heightOffset);
                    value = Math.round(value - noise2 + noise1);
                    int heightGen = value;
                    for (int y = heightGen; y >= 0; y--) {
                        blockData[x][y][z] = BlockRegister.blocks[BlockRegister.AIR].id;
                        //Grass.
                        generationProperties.buildRandomGrassPatch(x,y,z,heightGen,blockData,90);
                        //Dirt.
                        generationProperties.buildDirtLayer(x,y,z,heightGen,blockData);
                        //Stone.
                        generationProperties.buildStoneLayer(x,y,z,heightGen,blockData);
                        //BedRock.
                        generationProperties.buildBedRockLayer(x,y,z,heightGen,blockData);
                        //Rocky hills.
                        generationProperties.buildRockyHillLayer(x,y,z,heightGen,blockData);
                    }
                }
            }
            server.WorldData.put(offset,blockData);
        }
        public byte[][][] getChunkAt(Vector2 coordinates){
            return server.WorldData.get(coordinates);
        }
    }
}
