package com.dawnfall.engine.handle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.dawnfall.engine.Client.MainGame;
import com.dawnfall.engine.handle.GUI.PauseMenu;
import java.nio.ByteBuffer;
import java.util.Random;
import java.util.zip.Deflater;

  public class InputUpdater{
      private final MainGame client;
      public static boolean Pause = false;
      public InputUpdater( MainGame client){
          this.client = client;

      }
      public static void takeScreenshot(){
              Pixmap pixmap = Pixmap.createFromFrameBuffer(0, 0, Gdx.graphics.getBackBufferWidth(), Gdx.graphics.getBackBufferHeight());
              ByteBuffer pixels = pixmap.getPixels();
              int size = Gdx.graphics.getBackBufferWidth() * Gdx.graphics.getBackBufferHeight() * 4;
              for (int i = 3; i < size; i += 4) {
                  pixels.put(i, (byte) 255);
              }
              Random random = new Random();
              PixmapIO.writePNG(Gdx.files.external("OneDrive/Desktop/screenshot"+random.nextInt(100)+".png"),
                      pixmap, Deflater.DEFAULT_COMPRESSION, true);
              pixmap.dispose();
      }
      public void setPauseMenu(PauseMenu pauseMenu){
          if (Pause){
              client.pause();
              pauseMenu.show();
              pauseMenu.resize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
              Gdx.input.setCursorCatched(false);
          }
      }
  }






