package com.example.org.gvrfapplication;

import org.gearvrf.GVRAndroidResource;
import org.gearvrf.GVRContext;
import org.gearvrf.GVRMain;
import org.gearvrf.GVRMesh;
import org.gearvrf.GVRSceneObject;
import org.gearvrf.GVRTexture;
import org.gearvrf.animation.GVRAnimator;
import org.gearvrf.animation.GVRRepeatMode;
import org.gearvrf.scene_objects.GVRModelSceneObject;

import java.util.concurrent.Future;

/**
 * The Main Scene of the App
 */
public class MainScene extends GVRMain {

    @Override
    public void onInit(GVRContext gvrContext) throws Throwable {

        //Load animated model
        GVRSceneObject character = gvrContext.getAssetLoader().loadModel("astro_boy.dae");
        character.getTransform().setRotationByAxis(45.0f, 0.0f, 1.0f, 0.0f);
        character.getTransform().setScale(6, 6, 6);
        character.getTransform().setPosition(0.0f, -0.5f, -1f);
        gvrContext.getMainScene().addSceneObject(character);

        GVRAnimator animator = (GVRAnimator)character.getComponent(GVRAnimator.getComponentType());
        animator.setRepeatCount(-1);
        animator.setRepeatMode(GVRRepeatMode.REPEATED);
        animator.start();
    }

    @Override
    public SplashMode getSplashMode() {
        return SplashMode.NONE;
    }

    @Override
    public void onStep() {
        //Add update logic here
    }
}
