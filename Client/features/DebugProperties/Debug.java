package com.dawnfall.engine.Client.features.DebugProperties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.dawnfall.engine.Client.MainGame;
import com.dawnfall.engine.Client.MainRenderer;
import com.dawnfall.engine.gen.ChunkManager;
import com.dawnfall.engine.gen.World.World;

public class Debug {
    //The Game Debug Class.
    public static boolean SHOW_UI_TABLES = false;
    private static boolean isDebugOn = false;
    private static Model gridLine;
    private static Model xyzLine;
    public static GLProfiler profiler;

    public static void setRenderDebugMode(boolean isDebugMode, PerspectiveCamera camera, MainGame client){

        if (isDebugMode){
            Runtime runtime = Runtime.getRuntime();
            isDebugOn = true;
            int x = Math.round(camera.position.x);
            int y = Math.round(camera.position.y);
            int z = Math.round(camera.position.z);
            MainRenderer.spriteBatch.begin();
            client.fpsFont.draw(MainRenderer.spriteBatch,"Coordinates:"+x+","+y+","+z+"\nChunks tick:"+
                    World.getTickUpdate()+ "\nMemory Heap:"+ (short)Gdx.app.getNativeHeap()+
                    "\nWorld Data Size:"+ ChunkManager.getDataSize()+"\nRender Calls:"+
                    profiler.getDrawCalls()+" FPS:"+Gdx.graphics.getFramesPerSecond()+"\nChunk Position:"
                    +ChunkManager.getChunkCoordinatesPositionDebug()+"\nDirty Chunks:"+ChunkManager.getDirty()
                    +"\nDirty Chunks Processed:"+ChunkManager.getDirtyChunksProcess()+"\nBuilt Chunks:"+ChunkManager.getProcessedChunks(),0,680);
            MainRenderer.spriteBatch.end();
            profiler.reset();
        }
    }
    public static void enableRenderDebug(){
        profiler = new GLProfiler(Gdx.graphics);
        profiler.enable();
        profiler.reset();
    }
    public static boolean isDebugOn(){
        return isDebugOn;
    }
    //Shows the UI table debug.
    public static void setTableDebug(boolean tableDebug, Table table){
       if (tableDebug){
           SHOW_UI_TABLES = true;
           table.setDebug(true);
       }
    }
    //Shows the debug grid.
    public static ModelInstance showGrid(){
        Material material = new Material( ColorAttribute.createDiffuse(0.2f,0.2f,0.2f,1f));
        int attributes = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.ColorPacked;
        ModelBuilder builder = new ModelBuilder();
        gridLine = builder.createLineGrid(10000,1000,16,16,material,attributes);
        ModelInstance grid;
        return grid = new ModelInstance(gridLine);
    }
    public static ModelInstance showXYZLine(){
        Material material = new Material(new ColorAttribute(ColorAttribute.Diffuse, Color.WHITE));
        int attributes = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.ColorPacked;
        ModelBuilder builder = new ModelBuilder();
        xyzLine = builder.createXYZCoordinates(16,material,attributes);
        ModelInstance line = new ModelInstance(xyzLine);
       return line;
    }
    public static void dispose(){
        if (gridLine != null){
            gridLine.dispose();
        }
        xyzLine.dispose();
    }
}
