package org.gearvrf.simplesample;

import org.gearvrf.GVRAndroidResource;
import org.gearvrf.GVRContext;
import org.gearvrf.GVRMain;
import org.gearvrf.GVRScene;
import org.gearvrf.GVRSceneObject;
import org.gearvrf.GVRTexture;

/**
 * Created by sushant.o on 6/16/2017.
 */

public class SampleMain extends GVRMain{

    @Override
    public void onInit(GVRContext gvrContext) {
        GVRScene scene = gvrContext.getMainScene();
        scene.setBackgroundColor(1, 1, 1, 1);

        GVRTexture texture = gvrContext.getAssetLoader().loadTexture(new GVRAndroidResource(gvrContext, R.drawable.gearvr_logo));

        // create a scene object (this constructor creates a rectangular scene
        // object that uses the standard texture shader
        GVRSceneObject sceneObject = new GVRSceneObject(gvrContext, 4.0f, 2.0f, texture);

        // set the scene object position
        sceneObject.getTransform().setPosition(0.0f, 0.0f, -3.0f);

        // add the scene object to the scene graph
        scene.addSceneObject(sceneObject);
    }

}
