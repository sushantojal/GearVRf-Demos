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

package org.gearvrf.particles;

import org.gearvrf.GVRAndroidResource;
import org.gearvrf.GVRContext;
import org.gearvrf.GVRMaterial;
import org.gearvrf.GVRMesh;
import org.gearvrf.GVRScene;
import org.gearvrf.GVRSceneObject;
import org.gearvrf.GVRMain;
import org.gearvrf.GVRRenderData.GVRRenderingOrder;
import org.gearvrf.GVRTexture;
import org.gearvrf.balloons.R;
import org.gearvrf.particles.emitters.ParticleEmitter;
import org.gearvrf.particles.emitters.ParticlePlaneEmitter;
import org.gearvrf.particles.emitters.ParticleSphereEmitter;
import org.gearvrf.scene_objects.GVRSphereSceneObject;
;
import android.graphics.Color;
import android.view.MotionEvent;
import org.gearvrf.GVRPicker;
import org.gearvrf.IPickEvents;
import org.gearvrf.GVRPicker.GVRPickedObject;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.Random;

public class ParticleMain extends GVRMain {

    public class PickHandler implements IPickEvents
    {
        public GVRSceneObject   PickedObject = null;

        public void onEnter(GVRSceneObject sceneObj, GVRPicker.GVRPickedObject pickInfo) { }
        public void onExit(GVRSceneObject sceneObj) { }
        public void onInside(GVRSceneObject sceneObj, GVRPicker.GVRPickedObject pickInfo) { }
        public void onNoPick(GVRPicker picker)
        {
            PickedObject = null;
        }
        public void onPick(GVRPicker picker)
        {
            GVRPickedObject picked = picker.getPicked()[0];
            PickedObject = picked.hitObject;
        }
    }

    private GVRScene mScene = null;
    private PickHandler mPickHandler;
    private ParticleEmitter mParticleSystem;
    private ParticleEmitter mParticleSystem2;

    private ArrayList<GVRMaterial> mMaterials;
    private GVRMesh     mSphereMesh;
    private Random      mRandom = new Random();
    private GVRPicker   mPicker;

    @Override
    public void onInit(GVRContext context)
    {
        /*
         * Set the background color
         */
        mScene = context.getMainScene();
        mScene.getMainCameraRig().getLeftCamera().setBackgroundColor(Color.BLACK);
        mScene.getMainCameraRig().getRightCamera().setBackgroundColor(Color.BLACK);

        /*
         * Set up a head-tracking pointer
         */
        GVRSceneObject headTracker = new GVRSceneObject(context,
                context.createQuad(0.1f, 0.1f),
                context.getAssetLoader().loadTexture(new GVRAndroidResource(context, R.drawable.headtrackingpointer)));
        headTracker.getTransform().setPosition(0.0f, 0.0f, -1.0f);
        headTracker.getRenderData().setDepthTest(false);
        headTracker.getRenderData().setRenderingOrder(100000);
        mScene.getMainCameraRig().addChildObject(headTracker);
        /*
         * Add the environment
         */
//        GVRSceneObject environment = makeEnvironment(context);
//        mScene.addSceneObject(environment);
        /*
         * Make balloon prototype sphere mesh
         */
        mSphereMesh = new GVRSphereSceneObject(context, true).getRenderData().getMesh();

        /*
         * Start the particle emitter making balloons
         */
        GVRSceneObject particleRoot = new GVRSceneObject(context);
        particleRoot.setName("ParticleSystem");
        ParticleEmitter.MakeParticle particleCreator = new ParticleEmitter.MakeParticle()
        {
//            public GVRSceneObject create(GVRContext context) { return makeBalloon(context); }

            public GVRSceneObject create(GVRContext context) { return makeParticleQuad(context); }

        };
        mParticleSystem = new ParticleSphereEmitter(context, mScene, particleCreator,1);

        mParticleSystem.setShapeParams(1);

        mParticleSystem.MaxDistance = 6.0f;
        mParticleSystem.TotalParticles = 1000;
        mParticleSystem.EmissionRate = 100;
        mParticleSystem.Velocity = new ParticleEmitter.Range<Float>(2.0f, 3.0f);
        mParticleSystem.EmitterArea = new ParticleEmitter.Range<Vector2f>(new Vector2f(-1.0f, 1.0f), new Vector2f(1.0f, 1.0f));
        //particleRoot.getTransform().setRotationByAxis(-90.0f, 1, 0, 0);
        mParticleSystem.fadeWithDistance = true;
        particleRoot.getTransform().setPosition(2, -6, -5.0f);
        particleRoot.attachComponent(mParticleSystem);
        //particleRoot.addChildObject(mParticleSystem.getOwnerObject());
        //mScene.addSceneObject(particleRoot);




        GVRSceneObject particleRoot2 = new GVRSceneObject(context);
        particleRoot.setName("ParticleSystem");
        ParticleEmitter.MakeParticle particleCreator2 = new ParticleEmitter.MakeParticle()
        {
//            public GVRSceneObject create(GVRContext context) { return makeBalloon(context); }

            public GVRSceneObject create(GVRContext context) { return makeParticleQuad(context); }

        };
        mParticleSystem2 = new ParticlePlaneEmitter(context, mScene, particleCreator2,1,1);

        mParticleSystem2.setShapeParams(1);

        mParticleSystem2.MaxDistance = 6.0f;
        mParticleSystem2.TotalParticles = 1000;
        mParticleSystem2.EmissionRate = 100;
        mParticleSystem2.Velocity = new ParticleEmitter.Range<Float>(2.0f, 3.0f);
        mParticleSystem2.EmitterArea = new ParticleEmitter.Range<Vector2f>(new Vector2f(-1.0f, 1.0f), new Vector2f(1.0f, 1.0f));
        //particleRoot.getTransform().setRotationByAxis(-90.0f, 1, 0, 0);
        mParticleSystem2.fadeWithDistance = true;
        particleRoot2.getTransform().setPosition(-2, -1, -5.0f);
        particleRoot2.attachComponent(mParticleSystem2);
        //particleRoot.addChildObject(mParticleSystem.getOwnerObject());
        particleRoot2.getTransform().setRotationByAxis(45, 0,0,1);
        mScene.addSceneObject(particleRoot2);








        /*
         * Respond to picking events
         */
        mPicker = new GVRPicker(context, mScene);
        mPickHandler = new PickHandler();
        mScene.getEventReceiver().addListener(mPickHandler);
    }

    GVRSceneObject makeParticleQuad(GVRContext context)
    {
        GVRTexture tex = context.getAssetLoader().loadTexture (new GVRAndroidResource(context, R.drawable.fireparticle));
        GVRSceneObject particleQuad = new GVRSceneObject(context, 0.7f,0.7f, tex);
        particleQuad.getRenderData().setRenderingOrder(GVRRenderingOrder.TRANSPARENT);
        //particleQuad.getRenderData().setAlphaToCoverage(true);
        return particleQuad;
    }


    @Override
    public void onStep()
    {
        FPSCounter.tick();
    }

    public void onTouchEvent(MotionEvent event)
    {
        switch (event.getAction() & MotionEvent.ACTION_MASK)
        {
            case MotionEvent.ACTION_DOWN:
                if (mPickHandler.PickedObject != null)
                {
                    onHit(mPickHandler.PickedObject);
                }
                break;

            default:
                break;
        }
    }

    private void onHit(GVRSceneObject sceneObj)
    {
        Particle particle = (Particle) sceneObj.getComponent(Particle.getComponentType());
        if (particle != null)
        {
            mParticleSystem.stop(particle);
        }
    }
}
