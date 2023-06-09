package com.dawnfall.engine.handle.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.dawnfall.engine.Client.MainRenderer;
import com.dawnfall.engine.Client.features.DebugProperties.Debug;
import com.dawnfall.engine.Client.features.OptionsProperties.Options;

import static com.dawnfall.engine.Client.MainRenderer.audio;

public class CreateWorldScene extends ScreenAdapter {
    public Table table;
    public TextButton textButton;
    public Stage stage;
    public CreateWorldScene(){
       //Creates the stage for ui.
       stage = new Stage(new ScreenViewport());
       Gdx.input.setInputProcessor(stage);
        //Creates the table and skin.
       table = new Table();
       Skin skin = new Skin(Gdx.files.internal("GUI/uiskin.json"));
       table.setFillParent(true);
       table.setColor(Color.RED);
       stage.addActor(table);
       table.setDebug(Debug.SHOW_UI_TABLES);
       //Adds a textButton to the scene.
       textButton = new TextButton("Launch Game",skin);
       textButton.setColor(MainRenderer.buttonColor.getColor());
       textButton.addListener(new InputListener(){
           @Override
           public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
               audio.playButtonClicked();
               //Tells the game it is ready to render.
               Options.NetworkResource = Options.NetworkSides.CLIENT;
               Options.RENDER_GAME = true;
               Options.MAIN_RENDERER.show();
               Gdx.input.setCursorCatched(true);
               Options.GAME.resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
               return super.touchDown(event, x, y, pointer, button);
           }
       });
       table.add(textButton).fill(5,2);
   }
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,true);
        if (Options.RENDER_GAME){
            Options.MAIN_RENDERER.resize(width,height);
        }
        super.resize(width, height);
    }
}

