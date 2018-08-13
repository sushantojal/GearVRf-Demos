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

import android.os.Bundle;

import org.gearvrf.GVRActivity;
import org.gearvrf.GVRAndroidResource;
import org.gearvrf.GVRCameraRig;
import org.gearvrf.GVRContext;
import org.gearvrf.GVRImportSettings;
import org.gearvrf.GVRMain;
import org.gearvrf.GVRPointLight;
import org.gearvrf.GVRScene;
import org.gearvrf.GVRSceneObject;
import org.gearvrf.GVRSpotLight;
import org.gearvrf.GVRTexture;

import java.io.IOException;
import java.util.EnumSet;

public class SampleActivity extends GVRActivity {

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setMain(new SampleMain());
    }

    private static class SampleMain extends GVRMain {
        @Override
        public void onInit(GVRContext gvrContext) {
            GVRScene scene = gvrContext.getMainScene();
            //scene.setBackgroundColor(1, 1, 1, 1);

//            GVRTexture texture = gvrContext.getAssetLoader().loadTexture(new GVRAndroidResource(gvrContext, R.drawable.gearvr_logo));
//
//            // create a scene object (this constructor creates a rectangular scene
//            // object that uses the standard texture shader
//            GVRSceneObject sceneObject = new GVRSceneObject(gvrContext, 4.0f, 2.0f, texture);
//
//            // set the scene object position
//            sceneObject.getTransform().setPosition(0.0f, 0.0f, -3.0f);
//
//            // add the scene object to the scene graph
//            scene.addSceneObject(sceneObject);'

            GVRSceneObject root = new GVRSceneObject(gvrContext);


            //add model
            GVRSceneObject model = null;
            EnumSet<GVRImportSettings> settings = GVRImportSettings.getRecommendedSettingsWith(EnumSet.of(GVRImportSettings.START_ANIMATIONS));



//            String filepath = "busterDrone/busterDrone.gltf";
//            String filepath = "AnimatedCube/glTF/AnimatedCube.gltf";
//            String filepath = "BoxAnimated/glTF/BoxAnimated.gltf";

            String filepath = "sloth/sloth.gltf";

            try
            {
                model = gvrContext.getAssetLoader().loadModel(filepath, settings, false, scene);
            }
            catch (IOException ex) {
            }
            model.getTransform().setPosition(0, -8.5f, -6.5f);
            model.getTransform().setScale(0.05f,0.05f,0.05f);

            //root.addChildObject(model);


            //add light
            GVRSceneObject lightObj = new GVRSceneObject(gvrContext);
            GVRSpotLight spotLight = new GVRSpotLight(gvrContext);
            spotLight.setDiffuseIntensity(1, 1, 1, 1.0f);
            spotLight.setSpecularIntensity(0.6f, 0.6f, 0.6f, 1.0f);
            lightObj.attachComponent(spotLight);
            lightObj.getTransform().setPosition(0.0f, 0.5f, 0);
            root.addChildObject(lightObj);


            scene.addSceneObject(root);

        }
    }
}
