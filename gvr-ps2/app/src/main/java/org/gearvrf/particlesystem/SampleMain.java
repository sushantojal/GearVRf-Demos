package org.gearvrf.particlesystem;

import org.gearvrf.GVRAndroidResource;
import org.gearvrf.GVRBillboard;
import org.gearvrf.GVRContext;
import org.gearvrf.GVRDrawFrameListener;
import org.gearvrf.GVRMain;
import org.gearvrf.GVRMaterial;
import org.gearvrf.GVRMaterialShaderManager;
import org.gearvrf.GVRMesh;
import org.gearvrf.GVRRenderData;
import org.gearvrf.GVRScene;
import org.gearvrf.GVRSceneObject;
import org.gearvrf.GVRShaderTemplate;
import org.gearvrf.GVRTexture;
import org.gearvrf.particlesystem.R;
import org.gearvrf.particlesystem.emitters.GVREmitter;
import org.gearvrf.particlesystem.emitters.GVRPlaneEmitter;
import org.joml.Vector3f;

import java.io.IOException;
import java.util.Random;

import static android.opengl.GLES20.GL_POINTS;

/**
 * Created by sushant.o on 6/16/2017.
 */

public class SampleMain extends GVRMain {


    private GVRContext mGVRContext;

    @Override
    public void onInit(GVRContext gvrContext) throws IOException {

        mGVRContext = gvrContext;

        GVRScene scene = gvrContext.getMainScene();

        GVRSceneObject testEmitter = new GVRPlaneEmitter(mGVRContext);
        testEmitter.getTransform().setPosition(0,-2,0);
       // testEmitter.getRenderData().setDepthTest(true);
//        GVRTexture texture = gvrContext.getAssetLoader().loadTexture(new GVRAndroidResource(gvrContext, R.drawable.gearvr_logo));
//
//        // create a scene object (this constructor creates a rectangular scene
//        // object that uses the standard texture shader
//        GVRSceneObject sceneObject = new GVRSceneObject(gvrContext, 4.0f, 2.0f, texture);
//
//        // set the scene object position
//        sceneObject.getTransform().setPosition(0, 0.0f, -0.5f);

       scene.addSceneObject(testEmitter);



    }

}
