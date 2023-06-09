package com.dawnfall.engine.handle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.dawnfall.engine.util.Camera;

public class Camera3D {
    public final Vector3 pos = new Vector3();
    public Camera cam;

    public Camera3D(Camera cam) {
        this.cam = cam;
    }

    public void move() {
        final float delta = Gdx.graphics.getDeltaTime();
        final Input input = Gdx.input;

        final GridPoint2 mouseDelt = Inputs.getMouseDelta();
        float magX = mouseDelt.x;
        float magY = mouseDelt.y;

        cam.pitch -= magY * 0.15f; // 0.15f
        cam.yaw += magX * 0.15f;
        cam.pitch = MathUtils.clamp(cam.pitch, -90.0f, 90.0f);
        cam.yaw %= 360f;
        if (cam.yaw < 0f) cam.yaw += 360f;

        cam.updateRotation();

        final boolean left = input.isKeyPressed(Input.Keys.A);
        final boolean right = input.isKeyPressed(Input.Keys.D);
        final float speed = (input.isKeyPressed(Input.Keys.R) ? 120.0f : 12.0f)*delta;
        final float rad = cam.yaw / 180f * MathUtils.PI;
        float PI2 = MathUtils.PI/4.5f;

        final Vector3 pos = cam.position;

        if (input.isKeyPressed(Input.Keys.W)) {
            if (left) {
                pos.x += MathUtils.sin(rad+PI2) * speed;
                pos.z += MathUtils.cos(rad+PI2) * speed;
            } else if (right) {
                pos.x += MathUtils.sin(rad-PI2) * speed;
                pos.z += MathUtils.cos(rad-PI2) * speed;
            } else {
                pos.x += MathUtils.sin(rad) * speed;
                pos.z += MathUtils.cos(rad) * speed;
            }
        } else if (input.isKeyPressed(Input.Keys.S)) {
            if (left) {
                pos.x -= MathUtils.sin(rad-PI2) * speed;
                pos.z -= MathUtils.cos(rad-PI2) * speed;
            } else if (right) {
                pos.x -= MathUtils.sin(rad+PI2) * speed;
                pos.z -= MathUtils.cos(rad+PI2) * speed;
            } else {
                pos.x -= MathUtils.sin(rad) * speed;
                pos.z -= MathUtils.cos(rad) * speed;
            }
        } else {
            PI2 = MathUtils.PI*0.5f;
            if (left) {
                pos.x -= MathUtils.sin(rad-PI2) * speed;
                pos.z -= MathUtils.cos(rad-PI2) * speed;
            } else if (right) {
                pos.x -= MathUtils.sin(rad+PI2) * speed;
                pos.z -= MathUtils.cos(rad+PI2) * speed;
            }
        }

        if (input.isKeyPressed(Input.Keys.SPACE)) {
            pos.y += speed;
        } else if (input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            pos.y -= speed;
        }
        //pos.setZero();
        this.pos.set(pos);
    }

    public void resetPos() {
        cam.position.set(pos);
    }
}
