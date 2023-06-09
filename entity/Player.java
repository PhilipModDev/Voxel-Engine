package com.dawnfall.engine.entity;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.dawnfall.engine.gen.World.World;
import com.dawnfall.engine.handle.Camera3D;
import com.dawnfall.engine.util.Camera;

public final class Player {
    private Camera camera;
    public World world;
    private final Camera3D camera3D;
    public Player(int fieldOfView,int width,int height){
        createCamera(fieldOfView,width,height);
        camera3D = new Camera3D(camera);
    }
    public Player(int fieldOfView, int width, int height, Vector3 position){
        createCamera(fieldOfView,width,height,position);
        camera3D = new Camera3D(camera);
    }
    private void createCamera(int fieldOfView,int width,int height){
        camera = new Camera(fieldOfView,width,height);
        camera.position.set(0,0,0);
        camera.near = 1f;
        camera.far = 1000f;
        camera.update();
    }
    private void createCamera(int fieldOfView, int width, int height, Vector3 position){
        camera =  new Camera(fieldOfView,width,height);
        camera.position.set(position);
        camera.near = 0.1f;
        camera.far = 2000f;
        camera.update();
    }
    public PerspectiveCamera getPlayer() {
        return camera;
    }
    public float getPlayerPosX(){
        return camera.position.x;
    }
    public float getPlayerPosY(){
        return camera.position.y;
    }
    public float getPlayerPosZ(){
        return camera.position.z;
    }
    public Vector3 getPlayersPosition(){
        return camera.position;
    }
    public Vector2 getPlayersVector2Position(){
        return new Vector2(camera.position.x,camera.position.z);
    }

    public Camera getCamera() {
        return camera;
    }
    public Camera3D getCamera3D() {
        return camera3D;
    }
    public void updatePlayer(){
        if (camera3D != null){
           camera3D.move();
        }
    }
    public void setWorld(World world) {
        this.world = world;
    }
}
