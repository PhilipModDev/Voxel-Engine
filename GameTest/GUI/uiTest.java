package com.dawnfall.engine.GameTest.GUI;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.w3c.dom.Node;


public class uiTest implements ApplicationListener {

    Stage stage;
    Table table;
    PerspectiveCamera camera;
    FirstPersonCameraController controller;

    Model model;
    ModelInstance instance;
    ModelBatch batch;
    ModelBuilder builder;

    Environment environment;

    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        table.setColor(Color.BLUE);
        table.debug();
        stage.addActor(table);

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 4.0f,4.0f,4,0f));
        environment.add(new DirectionalLight().set(8.0f,8.0f,-0.4f, -0.1f,0.1f,0.1f));
        batch = new ModelBatch();
        camera = new PerspectiveCamera(70, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera.position.set(0,0,0);
        camera.near = 1f;
        camera.far = 1000f;
        camera.update();


        Material material = new Material(new ColorAttribute(ColorAttribute.Diffuse, Color.BROWN));
        int attribute = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal;
        builder = new ModelBuilder();
        model = builder.createBox(5,5,5,material,attribute);
        instance = new ModelInstance(model);
        controller = new FirstPersonCameraController(camera);
        Gdx.input.setInputProcessor(controller);

    }

    @Override
    public void resize(int width, int height) {
          stage.getViewport().update(width,height,true);
    }

    @Override
    public void render() {
     Gdx.gl.glClearColor(0.1f,0,0,0);
     Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        controller.update();
     batch.begin(camera);
     stage.act();
     stage.draw();
     batch.render(instance,environment);
     batch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
