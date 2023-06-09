package com.dawnfall.engine.Client.features.CursorProperties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dawnfall.engine.Client.MainRenderer;
import java.util.LinkedList;

public class DefaultCursor {
    //List the properties of the cursor
    private final LinkedList<Texture> textureList = new LinkedList<>();
    private final int x = 530;
    private final int y = 350;
    private final int Size = 30;
   //Creates the cursor.
    public DefaultCursor(Texture texture){
        MainRenderer.spriteBatch = new SpriteBatch();
        textureList.add(texture);
    }
    public DefaultCursor(){
        MainRenderer.spriteBatch = new SpriteBatch();
    }
    //Just creates the default Cursor Color.
   public void getDefaultCursor(){
        String path = "Cursor/cursor.png";
        textureList.add(new Texture(Gdx.files.internal(path)));
       MainRenderer.spriteBatch.begin();
       MainRenderer.spriteBatch.draw(textureList.get(0), x, y, Size, Size);
       MainRenderer.spriteBatch.end();
    }
   //Creates the custom cursor.
   public void customCursor(){
       MainRenderer.spriteBatch.begin();
       MainRenderer.spriteBatch.draw(textureList.get(0),x,y,Size,Size);
       MainRenderer.spriteBatch.end();
   }
    //Creates the Red Cursor Color.
   public void getRedCursor(){
        textureList.add(new Texture(Gdx.files.internal("Cursor/red_cursor.png")));
       MainRenderer.spriteBatch.begin();
       MainRenderer.spriteBatch.draw(textureList.get(2),x,y,Size,Size);
       MainRenderer.spriteBatch.end();
   }
   public void dispose(){
        for (Texture texture:textureList){
            texture.dispose();
        }
   }
}
