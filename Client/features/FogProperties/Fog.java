package com.dawnfall.engine.Client.features.FogProperties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.shaders.DepthShader;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class Fog {
    //Creates the fog for the game.
   public ModelInstance fogInstance;
   private Model fog;
   private ModelBuilder buildFog;
   private Material material;
   private int attribute;
    private final int fogSize;
   public Fog(int fogSize){
       this.fogSize = fogSize;
       createFog();
   }
//Still config the fog a little.
   private void createFog(){
       Texture texture = new Texture(Gdx.files.internal("fog/default_fog.png"));
       material = new Material(TextureAttribute.createDiffuse(texture),
               new BlendingAttribute(GL20.GL_ONE_MINUS_SRC_ALPHA,GL20.GL_DEPTH_FUNC));
       attribute = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates;
       buildFog = new ModelBuilder();
       fog = buildFog.createBox(fogSize,fogSize,fogSize,material,attribute);
       fogInstance = new ModelInstance(fog);
       fogInstance.transform.setToTranslation(20,50,20);
   }
}
