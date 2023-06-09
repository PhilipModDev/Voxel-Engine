package com.dawnfall.engine.Client.features.AudioProperties;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class GameAudio {
    //This holds all the game audio sounds and music.
    private final Music buttonClick;
    public GameAudio() {
        buttonClick = Gdx.audio.newMusic(Gdx.files.internal("audio/sounds/button_clicked.mp3"));
    }
    public void playButtonClicked(){
        buttonClick.play();
    }
}
