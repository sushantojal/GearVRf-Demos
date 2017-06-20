package org.gearvrf.simplesample;

import org.gearvrf.GVRAndroidResource;
import org.gearvrf.GVRContext;
import org.gearvrf.GVRMain;
import org.gearvrf.GVRMaterial;
import org.gearvrf.GVRMesh;
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
        //scene.setBackgroundColor(1, 1, 1, 1);

        GVRTexture texture = gvrContext.getAssetLoader().loadTexture(new GVRAndroidResource(gvrContext, R.drawable.gearvr_logo));
        GVRSceneObject sceneObject = new GVRSceneObject(gvrContext, 4.0f, 2.0f, texture);

        GVRMesh partMesh = new GVRMesh(gvrContext);
        partMesh.setVertices(new float[]{-2.0f, 1.0f, -1.0f, 0,1.0f, -1.0f, 0,0,0, -2.0f, 0.0f, -1.0f});

        partMesh.setNormals(new float[]{0,0,1, 0,0,1, 0,0,1, 0,0,1});

        sceneObject.getRenderData().setMesh(partMesh);

        sceneObject.getRenderData().setShaderTemplate(ColorShader.class);

        sceneObject.getTransform().setPosition(0.0f, 0.0f, -3.0f);



        scene.addSceneObject(sceneObject);

    }

}
