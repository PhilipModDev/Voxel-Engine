package com.dawnfall.engine.Client.features;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class ButtonColor {
    private Color color;
    public ButtonColor(float r, float g, float b,float a){
        color = new Color(r,g,b,a);
    }
    public Color getColor() {
        return color;
    }
    public Color getClickColor(){
        return new Color(0.6f,0.5f,0.3f,1);
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public void changeColorButton(TextButton textButton){
        textButton.setColor(getClickColor());
    }
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
