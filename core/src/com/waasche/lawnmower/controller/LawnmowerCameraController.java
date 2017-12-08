package com.waasche.lawnmower.controller;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;


public class LawnmowerCameraController extends InputAdapter {

    final Plane xzPlane = new Plane(new Vector3(0, 1, 0), 0);
    final Vector3 curr = new Vector3();
    final Vector3 last = new Vector3(-1, -1, -1);
    final Vector3 delta = new Vector3();
    final Camera camera;

    public LawnmowerCameraController (Camera camera) {
        this.camera = camera;
    }

    @Override
    public boolean touchDragged (int x, int y, int pointer) {
        Ray pickRay = camera.getPickRay(x, y);
        Intersector.intersectRayPlane(pickRay, xzPlane, curr);
        if (!(last.x == -1 && last.y == -1 && last.z == -1)) {
            pickRay = camera.getPickRay(last.x, last.y);
            Intersector.intersectRayPlane(pickRay, xzPlane, delta);
            delta.sub(curr);
            camera.position.add(delta.x, 0, delta.z);
        }
        last.set(x, y, 0);
        return false;
    }

    @Override
    public boolean touchUp (int x, int y, int pointer, int button) {
        last.set(-1, -1, -1);
        return false;
    }
}

