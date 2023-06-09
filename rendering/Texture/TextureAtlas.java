package com.dawnfall.engine.rendering.Texture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Hashtable;

public class TextureAtlas {
    private final Texture texture;
    private SpriteBatch batch;
    private final Hashtable<Integer,TextureRegion> textureAtlasTable = new Hashtable<>();
    public TextureAtlas(String path){
        texture = new Texture(Gdx.files.internal(path));
        batch = new SpriteBatch();
    }
    public void addTexture(int index,int x,int y,int width,int height){
        textureAtlasTable.put(index,new TextureRegion(texture,x,y,width,height));
    }
    public void addTexture(int index,int textureSize,int x,int y){
        textureAtlasTable.put(index,new TextureRegion(texture,x,y,textureSize,textureSize));
    }
    public TextureRegion getTexture(int index){
      try{
        return textureAtlasTable.get(index);
      }catch (Exception exception){
          System.out.println("Error:Texture is not on atlas.");
          exception.printStackTrace();
      }
      return null;
    }
    public void renderTextureOnScreen(int index,int x,int y){
        batch.begin();
        batch.draw(textureAtlasTable.get(index),x,y);
        batch.end();
    }
    public void renderTextureOnScreen(int index,int x,int y,int width,int height){
        batch.begin();
        batch.draw(getTexture(index),x,y,width,height);
        batch.end();
    }
    public Texture getTexture() {
        return texture;
    }
    public SpriteBatch getBatch() {
        return batch;
    }
}
