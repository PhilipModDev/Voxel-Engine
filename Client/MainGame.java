package com.dawnfall.engine.Client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g3d.*;
import com.dawnfall.engine.Blocknet.Client.ClientProtocol;
import com.dawnfall.engine.Client.features.BundleLoader;
import com.dawnfall.engine.Client.features.CursorProperties.DefaultCursor;
import com.dawnfall.engine.Client.features.DebugProperties.Debug;
import com.dawnfall.engine.Client.features.OptionsProperties.Options;
import com.dawnfall.engine.handle.GUI.PauseMenu;
import com.dawnfall.engine.gen.World.WorldRenderer;
import com.dawnfall.engine.handle.InputUpdater;
import com.dawnfall.engine.handle.events.EventAdapter;
import com.dawnfall.engine.handle.events.EventRegister;
import com.dawnfall.engine.handle.events.ServerPortEvent;
import com.dawnfall.engine.handle.events.TickEvent;
import com.dawnfall.engine.rendering.BoxRenderer;
import com.dawnfall.engine.rendering.shaders.ShaderResource;
import com.dawnfall.engine.rendering.shaders.EngineLighting;

//Main Game loop.
public class MainGame implements Screen {
    public ModelBatch batch;
    public static EngineLighting light;
    private DefaultCursor defaultCursor;
    public BitmapFont fpsFont;
    public WorldRenderer worldRenderer;
    public static ShaderResource shader;
    private InputUpdater updater;
    private PauseMenu pauseMenu;
    public static EventRegister<?> eventRegister;
    public static BoxRenderer boxRenderer;
    @Override
    public void show() {

        shader = new ShaderResource(MainRenderer.player.getPlayer());
        shader.setVertexFromFile("shaders/vertex.glsl");
        shader.setFragmentFromFile("shaders/fragment.glsl");
        shader.compileShader();
        if (shader.getShaderProgram().isCompiled()){
           Gdx.app.log("Shader","Compiled Complete");
        } else if (!shader.getShaderProgram().isCompiled()) {
            Gdx.app.error("Shader","Can't Compile Shader!");
        }
        ClientProtocol.setClientId();
        BundleLoader.LoadEvents();
        fpsFont = new BitmapFont();
        defaultCursor = new DefaultCursor();
        //Creates the lighting for the game.
        light = new EngineLighting();
        light.lightingFog();
        //Create the model batch.
        batch = new ModelBatch();

        worldRenderer = new WorldRenderer(true);
        Debug.enableRenderDebug();
        updater = new InputUpdater(this);
        pauseMenu = new PauseMenu();

        EventAdapter tick = new TickEvent();
        EventAdapter serverPort = new ServerPortEvent();
        eventRegister = new EventRegister<>();
        eventRegister.registerTick(tick);
        eventRegister.registerServerPort(serverPort);

        boxRenderer = new BoxRenderer(MainRenderer.player.getCamera());
    }
    @Override
    public void render(float delta) {
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glViewport(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl20.glClearColor(0.1f,0.4f,0.6f,1);
        renderTerrain();
        //Calls the cursor for drawing.
        defaultCursor.getDefaultCursor();
        //Game controls
        updater.setPauseMenu(pauseMenu);
        BundleLoader.LoadEvents();
    }
    public void renderTerrain(){
        worldRenderer.initializeWorld();
        Debug.setRenderDebugMode(true, MainRenderer.player.getPlayer(),this);
    }
    @Override
    public void resize(int width, int height) {
       if (Options.RENDER_GAME) {
           BundleLoader.ResizeUpdate(width,height);
           MainRenderer.player.getPlayer().viewportWidth = width;
           MainRenderer.player.getPlayer().viewportHeight = height;
           MainRenderer.player.getPlayer().update();
           Gdx.gl.glViewport(0, 0, width, height);
           MainRenderer.spriteBatch.setProjectionMatrix(MainRenderer.fitViewport.getCamera().combined);
           MainRenderer.fitViewport.update(width, height, true);
       }
    }
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
    @Override
    public void dispose() {
        batch.dispose();
        defaultCursor.dispose();
        Debug.dispose();
        boxRenderer.dispose();
    }
}
