package com.dawnfall.engine.gen;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector2;
import com.dawnfall.engine.Blocknet.Client.ClientProtocol;
import com.dawnfall.engine.Client.MainRenderer;
import com.dawnfall.engine.entity.Player;
import com.dawnfall.engine.gen.multithread.ChunkBuilder;
import com.dawnfall.engine.gen.multithread.ChunkBuilderThread;
import com.dawnfall.engine.gen.World.World;
import com.dawnfall.engine.mesh.ChunkMeshBuffer;
import com.dawnfall.engine.util.libUtil;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ChunkManager {
    public HashMap<Vector2, ModelInstance> LoadedChunks;
    public ConcurrentHashMap<Vector2,byte[][][]> ChunkData;
    public final Queue<Chunk> DirtyChunks = new ArrayDeque<>();
    public final Queue<ChunkBuilder> BuiltChunks = new ArrayDeque<>();
    public final Queue<Vector2> ActiveChunks = new ArrayDeque<>();
    public final ArrayList<Vector2> CoordinatesToRemove = new ArrayList<>();
    private final ClientProtocol clientProtocol;
    public int renderDistance;
    public static int dataSize;
    public final Player player;
    public World world;
    public static Vector2 playerCoordinates;
    private boolean update;
    public boolean updateWorkerThread;
    public ChunkBuilderThread chunkBuilderThread;
    private static int processedChunks = 0;
    public static  int dirty = 0;
    public static int dirtyChunksProcess = 0;
    public byte[][][] NorthChunk;
    public byte[][][] SouthChunk;
    public byte[][][] EastChunk;
    public byte[][][] WestChunk;

    public ChunkMeshBuffer buffer;
    public ChunkDataGeneration dataGeneration;

    public ChunkManager(World world) {
        //Create a new instance of the collections.
        ChunkData = new ConcurrentHashMap<>();
        LoadedChunks = new HashMap<>();
        this.renderDistance = world.RenderDistance;
        this.world = world;
        this.player = MainRenderer.player;
        clientProtocol = new ClientProtocol(3838);
        dataGeneration = new ChunkDataGeneration(this);

        chunkBuilderThread = new ChunkBuilderThread(this);
        new Thread(chunkBuilderThread).start();
        buffer = new ChunkMeshBuffer(this);
    }
    public void updateBuffer() {
        //Gets the built chunks and adds it to the render list.
        try {
            if (isUpdate()){
                //Updates the chunks.
                buffer.updateBuffer();
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
    public void buildChunkAt(Vector2 coordinates){
        ChunkBuilder chunkBuilder = new ChunkBuilder(this);
        Chunk chunk = new Chunk(coordinates);
        chunkBuilder.build(chunk);
    }
    public void buildChunkAt(Vector2 coordinates,byte[][][] data){
        ChunkBuilder chunkBuilder = new ChunkBuilder(this);
        Chunk chunk = new Chunk(coordinates);
        chunk.setData(data);
        chunkBuilder.build(chunk);
    }
    public void updateChunkPosition(){
        try {
            //Gets the players chunk coordinates position.
            int playerChunkX = (int) ((int) player.getPlayerPosX() / Chunk.size.x);
            int playerChunkY = (int) ((int) player.getPlayerPosZ() / Chunk.size.z);
            CoordinatesToRemove.clear();
            ChunkManager.playerCoordinates = world.getChunkAtPlayer();
            //Adds the coordinates to remove the old chunks.
            CoordinatesToRemove.addAll(ActiveChunks);
            //Is used to create new chunks before removal.
            for (int x = playerChunkX - renderDistance; x <= playerChunkX + renderDistance; x++) {
                for (int y = playerChunkY - renderDistance; y <= playerChunkY +renderDistance; y++) {
                    //Check if it is a loaded chunk.
                    Vector2 coordinates = new Vector2(x, y);
                    if (!LoadedChunks.containsKey(coordinates) ){
                        if (!ActiveChunks.contains(coordinates)) {
                            ActiveChunks.add(coordinates);
                            updateChunkData(coordinates);
                        }
                    }
                    CoordinatesToRemove.remove(coordinates);
                }
            }
            removeChunks();
        }catch (Exception exception){
            exception.printStackTrace();
            libUtil.log("Worker Thread","Chunks overloading.");
        }
    }
//    public void updatePos(){
//        try {
//            //Gets the players chunk coordinates position.
//            int playerChunkX = (int) ((int) player.getPlayerPosX() / Chunk.size.x);
//            int playerChunkY = (int) ((int) player.getPlayerPosZ() / Chunk.size.z);
//            CoordinatesToRemove.clear();
//            ChunkManager.playerCoordinates = chunkRegion.getChunkAtPlayer();
//            CoordinatesToRemove.addAll(ActiveChunks);
//            for (int x = playerChunkX - renderDistance; x <= playerChunkX + renderDistance; x++) {
//                for (int y = playerChunkY - renderDistance; y <= playerChunkY +renderDistance; y++) {
//                    //Check if it is a loaded chunk.
//                     Vector2 coordinates = new Vector2(x, y);
//                    if (!checkLoadedChunks(coordinates)) {
//                        if (ChunkData.containsKey(coordinates)) {
//                            if (!ActiveChunks.contains(coordinates)) {
//                                createChunk(coordinates, ChunkData.get(coordinates));
//                                ActiveChunks.add(coordinates);
//                            }
//                        } else {
//                            /*
//                             * Send a request to the server.
//                             * Listen for a response and add
//                             * it to the hashtable.
//                             */
//                            listenForChunkUpdates(coordinates);
//                            return;
//                        }
//                        CoordinatesToRemove.remove(coordinates);
//                    }
//                }
//            }
//            removeChunks();
//        }catch (Exception exception){
//            exception.printStackTrace();
//            libUtil.log("Worker Thread","Chunks overloading.");
//        }
//    }
public void updatePos(){
    try {
        //Gets the players chunk coordinates position.
        ChunkManager.playerCoordinates = world.getChunkAtPlayer();
                //Check if it is a loaded chunk.
                if (!checkLoadedChunks(playerCoordinates)) {
                    if (ChunkData.containsKey(playerCoordinates)) {
                        if (!ActiveChunks.contains(playerCoordinates)) {
                            createChunk(playerCoordinates, ChunkData.get(playerCoordinates));
                            ActiveChunks.add(playerCoordinates);
                        }
                    } else {
                        /*
                         * Send a request to the server.
                         * Listen for a response and add
                         * it to the hashtable.
                         */
                        listenForChunkUpdates(playerCoordinates);
                        return;
                    }
                    CoordinatesToRemove.remove(playerCoordinates);
                }
        removeChunks();
    }catch (Exception exception){
        exception.printStackTrace();
        libUtil.log("Worker Thread","Chunks overloading.");
    }
}
    public void removeChunks(){
        for (Vector2 chunkCoordinates : CoordinatesToRemove) {
            ActiveChunks.remove(chunkCoordinates);
            LoadedChunks.remove(chunkCoordinates);
        }
    }
    public void updateChunkData(Vector2 coordinates){
        if (coordinates != null) {
            if (World.getWorld().isWorldSize((int) coordinates.x, (int) coordinates.y)) {
                byte[][][] data = dataGeneration.generateData(coordinates);
                if (data.length > 0) {
                    buildChunkAt(coordinates);
                }
            }
        }
    }
    public void listenForChunkUpdates(Vector2 coordinates){
        try {
            clientProtocol.serverConnection(coordinates,this);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
    public void createChunk(Vector2 coordinates ,byte[][][] data){
        if (coordinates != null) {
            if (World.getWorld().isWorldSize((int) coordinates.x, (int) coordinates.y)) {
                if (data.length > 0) {
                    buildChunkAt(coordinates,data);
                }
            }
        }
    }
    public static void setProcessedChunks(int processedChunks) {
        ChunkManager.processedChunks += processedChunks;
    }
    public static Vector2 getChunkCoordinatesPositionDebug(){
        return playerCoordinates;
    }
    public static int getDataSize() {
        return dataSize;
    }
    public boolean isUpdate() {
        return update;
    }
    public void update(){
        update = true;
    }
    public static int getDirty() {
        if (dirty >= 10){
            dirty = 0;
        }
        return dirty;
    }
    public static int getDirtyChunksProcess() {
        if (dirtyChunksProcess >= 10){
            dirtyChunksProcess = 0;
        }
        return dirtyChunksProcess;
    }
    public boolean hasNeighbours(Vector2 chunkCoordinates){
        NorthChunk = world.getChunkAt((int) chunkCoordinates.x, (int) (chunkCoordinates.y+1));
        SouthChunk = world.getChunkAt((int) chunkCoordinates.x, (int) (chunkCoordinates.y-1));
        EastChunk  = world.getChunkAt((int) (chunkCoordinates.x+1), (int)chunkCoordinates.y);
        WestChunk  = world.getChunkAt((int) (chunkCoordinates.x-1),(int) chunkCoordinates.y);
        return NorthChunk != null && SouthChunk != null && EastChunk != null && WestChunk != null;
    }
    public void updateDirtyChunks() {
        if (DirtyChunks.peek() != null) {
            ChunkBuilder chunkBuilder = new ChunkBuilder(this);
            chunkBuilder.update(DirtyChunks.poll());
            DirtyChunks.clear();
        }
    }
    public static int getProcessedChunks(){
        if (processedChunks >= 100){
            processedChunks = 0;
        }
        return processedChunks;
    }
    public boolean checkLoadedChunks(Vector2 coordinates){
        return LoadedChunks.containsKey(coordinates);
    }
}