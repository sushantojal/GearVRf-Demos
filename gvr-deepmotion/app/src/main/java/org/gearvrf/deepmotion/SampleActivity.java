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

package org.gearvrf.deepmotion;

import android.os.Bundle;

import org.gearvrf.GVRActivity;
import org.gearvrf.GVRAssetLoader;
import org.gearvrf.GVRContext;
import org.gearvrf.GVRImportSettings;
import org.gearvrf.GVRMain;
import org.gearvrf.GVRScene;
import org.gearvrf.GVRSceneObject;

import java.io.IOException;

public class SampleActivity extends GVRActivity {

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setMain(new SampleMain());
    }

    private class SampleMain extends GVRMain {
        @Override
        public void onInit(GVRContext gvrContext) {
            GVRScene scene = gvrContext.getMainScene();

            String filepath = "testavt.json";
//            String modelpath = "FBX/Sam_2.fbx";
            String modelpath = "rabbit_gltf/re_rabbit_V03.gltf";

            AVTDeserializer.DeserializeFromJsonStr(gvrContext, filepath);
            GVRSceneObject modelRoot = null;
            GVRAssetLoader loader = getGVRContext().getAssetLoader();
            try {
                modelRoot = loader.loadModel(modelpath);
                modelRoot.getTransform().setScale(0.005f, 0.005f, 0.005f);
                modelRoot.getTransform().setPosition(0,0, -2);
            } catch (IOException e) {
                e.printStackTrace();
            }

            scene.addSceneObject(modelRoot);


        }
    }

}
