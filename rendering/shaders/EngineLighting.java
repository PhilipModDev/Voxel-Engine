package com.dawnfall.engine.rendering.shaders;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.*;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
public class EngineLighting {

    /*
     * This is for lighting engine.
     * Coming soon.
     */
    public Environment environment, fogEnvironment;
    private DirectionalLight light;
    public EngineLighting(){
        light = new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1, -0.8f, 0.2f);
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.Specular,0.3f, 0.2f, 0.2f, 0.4f),
                      new ColorAttribute(ColorAttribute.Reflection,0.5f, 0.3f, 0.2f, 0.4f),
                new BlendingAttribute(GL20.GL_ONE_MINUS_SRC_ALPHA,GL20.GL_SRC_ALPHA,0));
        environment.add(light);
    }


    public void lightingFog(){
        light = new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1, -0.8f, 0.2f);
        fogEnvironment = new Environment();
        fogEnvironment.set(new ColorAttribute(ColorAttribute.Fog,0.4f, 0.4f, 0.4f, 0.4f));
        fogEnvironment.add(light);
    }
}
