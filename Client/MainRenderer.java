package com.dawnfall.engine.Client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.dawnfall.engine.Client.features.BundleLoader;
import com.dawnfall.engine.entity.Player;
import com.dawnfall.engine.Client.features.AudioProperties.GameAudio;
import com.dawnfall.engine.Client.features.ButtonColor;
import com.dawnfall.engine.Client.features.DebugProperties.Debug;
import com.dawnfall.engine.Client.features.OptionsProperties.Options;
import com.dawnfall.engine.handle.GUI.CreateWorldScene;
import com.dawnfall.engine.handle.GUI.PauseMenu;
import com.dawnfall.engine.handle.events.InputEventRegister;

//Main Render Loop.
public class MainRenderer implements ApplicationListener  {
    public Table table;
    public Image image;
    public TextButton textButton;
    public TextButton settings;
    public Stage stage;
    public CreateWorldScene createWorldScene;
    public boolean renderWorld = false;
    public static Player player;
    public static SpriteBatch spriteBatch;
    public static FitViewport fitViewport;
    public static GameAudio audio;
    public static ButtonColor buttonColor;
    protected PauseMenu pauseMenu;
    //Create.
    @Override
    public void create() {
        BundleLoader.LoadResourceContainer();
        buttonColor = new ButtonColor(0.4f,0.5f,0.3f,1);
        spriteBatch = new SpriteBatch();
        player = new Player(70,Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),new Vector3(0,90,0));
        new InstallationGameScreens().installationGameScreens();
    }
    //Resize.
    @Override
    public void resize(int width, int height){
        if (stage == null){
            stage = new Stage(new ScreenViewport());
            image = new Image(new Texture(Gdx.files.internal("GUI/backgrounds/StarFall_menu0.png")));
            image.setWidth(stage.getWidth()/2);
            image.setHeight(stage.getHeight()/2);
            stage.addActor(image);
        }
        //Set the width and the height for resizing.
        stage.getViewport().update(width,height,true);
        image.setWidth(width);
        image.setHeight(height);
        if (!Options.RENDER_GAME){
            createWorldScene.resize(width, height);
        }
        Options.MAIN_RENDERER.resize(width,height);
    }
    //Render.
    @Override
    public void render() {
        //Sets up the rendering scene.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        RenderControl();
    }
    private void RenderControl() {
        //This is the button render control.
        if (!(Options.RENDER_GAME)) {
            update();
            resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        } else {
            Options.MAIN_RENDERER.render(Gdx.graphics.getDeltaTime());
            resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
      if (Gdx.input.isKeyPressed(Input.Keys.F11)){
          Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
      } else if (Gdx.input.isKeyPressed(Input.Keys.F12)) {
          Gdx.graphics.setWindowedMode(1080,720);
      }
    }
    private void update(){
        stage.act();
        stage.draw();
        if (renderWorld) {
            createWorldScene.stage.getViewport().apply();
            createWorldScene.stage.draw();
        } else {
            textButton.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    audio.playButtonClicked();
                    renderWorld = true;
                    settings.setVisible(false);
                    textButton.setVisible(false);
                    Gdx.input.setInputProcessor(createWorldScene.stage);
                    return super.touchDown(event, x, y, pointer, button);
                }
            });
        }
    }
    @Override
    public void pause() {
        Options.MAIN_RENDERER.pause();
        createWorldScene.pause();
    }
    @Override
    public void resume() {
        Options.MAIN_RENDERER.resume();
        createWorldScene.resume();
    }
    @Override
    public void dispose() {
        Options.MAIN_RENDERER.dispose();
        stage.dispose();
        createWorldScene.dispose();
        createWorldScene.stage.dispose();
    }
 protected class InstallationGameScreens {
        private void installationGameScreens(){
            pauseMenu = new PauseMenu();
            fitViewport = new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
            //Creates the second scene variable
            createWorldScene = new CreateWorldScene();
            //Creates the Main menu scene.
            stage = new Stage(new ScreenViewport());
            Gdx.input.setInputProcessor(stage);
            //Creates the background image.
            image = new Image(new Texture(Gdx.files.internal("GUI/backgrounds/StarFall_menu0.png")));
            image.setWidth(stage.getWidth()/2);
            image.setHeight(stage.getHeight()/2);
            stage.addActor(image);
            //Creates the tables for the scene.
            table = new Table();
            Skin skin = new Skin(Gdx.files.internal("GUI/uiskin.json"));
            table.setFillParent(true);
            table.setColor(Color.RED);
            stage.addActor(table);
            //Set if debug mode is on.
            Debug.setTableDebug(false,table);
            //Creates and adds the textButton to the stage.
            textButton = new TextButton("Play Game",skin);
            textButton.setColor(MainRenderer.buttonColor.getColor());
            table.add(textButton).width(700).height(50).fill(10,2).space(10);
            settings = new TextButton("Game Settings",skin);
            settings.setColor(MainRenderer.buttonColor.getColor());
            table.row();
            table.add(settings).width(700).height(50).fill(10,2).space(10);
            //plays the game audio.
            audio = new GameAudio();
            InputEventRegister register = new InputEventRegister();
            settings.addListener(register);
        }
    }
}
