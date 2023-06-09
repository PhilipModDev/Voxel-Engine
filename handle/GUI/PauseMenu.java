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
import com.dawnfall.engine.handle.InputUpdater;

public class PauseMenu extends ScreenAdapter {
    //This is for the pause menu screen.
    private Stage stage;
    public TextButton button;
    public boolean showColorButton;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        Skin skin = new Skin(Gdx.files.internal("GUI/uiskin.json"));
        table.setFillParent(true);
        table.setColor(Color.RED);
        stage.addActor(table);
        table.setDebug(Debug.SHOW_UI_TABLES);

        button = new TextButton("Save and Quit", skin);
        if (showColorButton){
            button.setColor(MainRenderer.buttonColor.getClickColor());
        }else {
            button.setColor(MainRenderer.buttonColor.getColor());
        }
        button.addListener(new InputListener(){



            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button1) {
                MainRenderer.audio.playButtonClicked();
                showColorButton = true;
                Gdx.app.exit();
                System.exit(0);
                dispose();
                return super.touchDown(event, x, y, pointer, button1);
            }



        });
        table.add(button).width(700).height(50).fill(10,2).space(10);
        final TextButton returnToGameButton = new TextButton("Return to game",skin);
        returnToGameButton.setColor(MainRenderer.buttonColor.getColor());



        returnToGameButton.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                MainRenderer.audio.playButtonClicked();
                showColorButton = false;
                stage.dispose();
                InputUpdater.Pause = false;
                return super.touchDown(event, x, y, pointer, button);
            }


        });
        table.add(returnToGameButton).width(700).height(50).fill(10,21).space(10);

        TextButton portButton = new TextButton("Port to Server?",skin);
        portButton.setColor(MainRenderer.buttonColor.getClickColor());
        portButton.addListener(new InputListener(){


            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (!Options.NetworkResource.equals(Options.NetworkSides.SERVER)){
                    MainRenderer.audio.playButtonClicked();
                    Options.NetworkResource = Options.NetworkSides.SERVER;
                }
                return super.touchDown(event, x, y, pointer, button);
            }


        });
        table.row();
        table.add(portButton).width(700).height(50).fill(10,2).space(10);
        stage.getViewport().apply();
        stage.draw();
        super.show();
    }
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height);
        super.resize(width, height);
    }
    @Override
    public void render(float delta) {
        super.render(delta);
    }
    @Override
    public void dispose() {
        stage.dispose();
        super.dispose();
    }
}

