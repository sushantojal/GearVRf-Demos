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
package org.gearvrf.particles.emitters;

import org.gearvrf.GVRContext;
import org.gearvrf.GVRScene;
import org.gearvrf.GVRSceneObject;
import org.gearvrf.particles.Particle;
import org.joml.Vector3f;

import java.util.Random;

public class ParticlePlaneEmitter extends ParticleEmitter {
    private float mWidth;
    private float mHeight;
    private Random mRandom = new Random();
    private float mLastEmitTime = 0;

    public ParticlePlaneEmitter(GVRContext ctx, GVRScene scene, MakeParticle newParticle,
                                float width, float height) {
        super(ctx, scene, newParticle);
        mWidth = width;
        mHeight = height;
    }

    public void onEnable() {
        super.onEnable();
        mLastEmitTime = 0;
    }

    public void onDrawFrame(float elapsed) {
        super.onDrawFrame(elapsed);

        float emitTime = 1 / EmissionRate;
        mLastEmitTime += elapsed;

        if (isEnabled()) {
            if (mLastEmitTime >= emitTime) {
//                if (burst)
//                {
//                    createBurstParticles();
//                }
                emit();
                mLastEmitTime = 0;
            }
        }
    }

    protected void emit() {
        Particle particle = null;
        GVRSceneObject sceneObj = null;
        Vector3f pos = getNextPosition();
        Vector3f direction = getNextDirection(pos);
        float velocity = getNextVelocity();

        if (mFreeParticles.size() > 0) {
            int last = mFreeParticles.size() - 1;
            particle = mFreeParticles.get(last);
            mFreeParticles.remove(last);
            sceneObj = particle.getOwnerObject();
            particle.Velocity = velocity;
            particle.Direction = direction;
        } else {
            if (mNumParticles >= TotalParticles) {
                return; // cannot create any more
            }
            if (mActiveParticles.size() >= MaxActiveParticles) {
                return; // cannot emit any more
            }
            sceneObj = mMakeParticle.create(getGVRContext());
            sceneObj.setName(sceneObj.getName() + Integer.valueOf(mNumParticles).toString());
            ++mNumParticles;
            particle = new Particle(getGVRContext(), velocity, direction);
            sceneObj.attachComponent(particle);
            getOwnerObject().addChildObject(sceneObj);
            //sceneObj.getRenderData().bindShader(mScene);
        }
        particle.setPosition(pos);
        mActiveParticles.add(particle);
        sceneObj.setEnable(true);
    }

    private Vector3f getNextDirection(Vector3f pos) {
        return new Vector3f(0,1,0);
    }

    private Vector3f getNextPosition() {

        float x = mRandom.nextFloat() * mWidth - mWidth/2;
        float z = mRandom.nextFloat() * mHeight - mHeight/2;
        return new Vector3f(x, 0, z);
    }

    private float getNextVelocity() {
        float velocity = Velocity.MinVal;
        if (Velocity.isRange()) {
            velocity += mRandom.nextFloat() * (Velocity.MaxVal - Velocity.MinVal);
        }
        return velocity;
    }
}