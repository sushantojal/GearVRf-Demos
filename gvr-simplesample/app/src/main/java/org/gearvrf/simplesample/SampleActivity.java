/* Copyright 2015 Samsung Electronics Co., LTD
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gearvrf.simplesample;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import org.gearvrf.GVRActivity;
import org.gearvrf.GVRAndroidResource;
import org.gearvrf.GVRBitmapImage;
import org.gearvrf.GVRCameraRig;
import org.gearvrf.GVRContext;
import org.gearvrf.GVRCubemapImage;
import org.gearvrf.GVRMain;
import org.gearvrf.GVRMaterial;
import org.gearvrf.GVRPointLight;
import org.gearvrf.GVRRenderPass;
import org.gearvrf.GVRScene;
import org.gearvrf.GVRSceneObject;
import org.gearvrf.GVRShader;
import org.gearvrf.GVRSpotLight;
import org.gearvrf.GVRTexture;
import org.gearvrf.GVRTransform;
import org.gearvrf.scene_objects.GVRCubeSceneObject;
import org.gearvrf.utility.FileNameUtils;
import org.gearvrf.utility.Log;
import org.joml.Quaternionf;

import java.io.IOException;
import java.io.InputStream;

public class SampleActivity extends GVRActivity {

    private GVRContext mContext;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setMain(new SampleMain(), "gvr.xml");
    }

    private class SampleMain extends GVRMain {

//        @Override
//        public void onInit(GVRContext gvrContext) {
//            GVRScene scene = gvrContext.getMainScene();
//            scene.setBackgroundColor(0, 0, 0, 1);
//
//            GVRTexture texture = gvrContext.getAssetLoader().loadTexture(new GVRAndroidResource(gvrContext, R.drawable.cube_normal));
//
//            // create a scene object (this constructor creates a rectangular scene
//            // object that uses the standard texture shader
//            GVRSceneObject sceneObject = new GVRSceneObject(gvrContext, 4.0f, 2.0f, texture);
//
//            // set the scene object position
//            sceneObject.getTransform().setPosition(0.0f, 0.0f, -3.0f);
//
//            // add the scene object to the scene graph
//            scene.addSceneObject(sceneObject);
//        }

        @Override
        public void onInit(GVRContext gvrContext) {
            GVRScene scene = gvrContext.getMainScene();
            scene.setBackgroundColor(0, 0, 0, 1);
            mContext = gvrContext;
            GVRSceneObject mBackground;
            GVRSceneObject mRoot;

            GVRContext ctx  = gvrContext;
            mBackground = new GVRCubeSceneObject(ctx, false, new GVRMaterial(ctx, GVRMaterial.GVRShaderType.Phong.ID));
            mBackground.getTransform().setScale(10, 10, 10);
            mBackground.setName("background");
            mRoot = scene.getRoot();

            GVRSceneObject model = null;
//            String modelfile = "/avocado/specgloss/Avocado.gltf";
//            String modelfile = "/avocado/specgloss/Avocado.gltf";
            String modelfile = "/damaged_helmet/DamagedHelmet.gltf";

            try
            {
                model = ctx.getAssetLoader().loadModel(modelfile);
            }
            catch (IOException ex)
            {
                Log.e("sushant", "failed to load");
            }
            //centerModel(model, scene.getMainCameraRig().getTransform());
            model.getTransform().setPosition(0, 0,-2);


            GVRTexture specularCubetex = mContext.getAssetLoader().loadCubemapTexture(new GVRAndroidResource(mContext, R.raw.specularcube));
            GVRTexture diffuseCubetex = mContext.getAssetLoader().loadCubemapTexture(new GVRAndroidResource(mContext, R.raw.diffusecube));

            model.getChildByIndex(0).getRenderData().getMaterial().setTexture("diffuseEnvTex",diffuseCubetex);
            model.getChildByIndex(0).getRenderData().getMaterial().setTexture("specularEnvTexture",specularCubetex);


            GVRSceneObject light1 = createLight(gvrContext, 1, 1, 1, 2.8f);
            //GVRSceneObject light2 = createLight(gvrContext, 1, 1, 1, -0.8f);

            scene.addSceneObject(model);
            scene.addSceneObject(light1);
            //scene.addSceneObject(light2);

        }
    }


    public void centerModel(GVRSceneObject model, GVRTransform camTrans)
    {
        GVRSceneObject.BoundingVolume bv = model.getBoundingVolume();
        float x = camTrans.getPositionX();
        float y = camTrans.getPositionY();
        float z = camTrans.getPositionZ();
        float sf = 1 / bv.radius;
        model.getTransform().setScale(sf, sf, sf);
        bv = model.getBoundingVolume();
        model.getTransform().setPosition(x - bv.center.x, y - bv.center.y, z - bv.center.z - 1.5f * bv.radius);
    }


    private GVRSceneObject createLight(GVRContext context, float r, float g, float b, float y)
    {
        GVRSceneObject lightNode = new GVRSceneObject(context);
        GVRPointLight light = new GVRPointLight(context);
        Quaternionf q = new Quaternionf();

        lightNode.attachLight(light);
        lightNode.getTransform().setPosition(0, 0.5f, 0);
        light.setAmbientIntensity(0.4f * r, 0.4f * g, 0.4f * b, 1);
        light.setDiffuseIntensity(r, g, b, 1);
        light.setSpecularIntensity(r, g, b, 1);
//        light.setInnerConeAngle(10);
//        light.setOuterConeAngle(20);
        return lightNode;
    }
}
