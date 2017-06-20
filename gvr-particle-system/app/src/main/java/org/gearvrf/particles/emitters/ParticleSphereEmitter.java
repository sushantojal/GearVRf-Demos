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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class ParticleSphereEmitter extends ParticleEmitter {
    private float mRadius;
    private Random mRandom = new Random();
    private float mLastEmitTime = 0;

    public ParticleSphereEmitter(GVRContext ctx, GVRScene scene, MakeParticle newParticle, float radius) {
        super(ctx, scene, newParticle);
        mRadius = radius;
    }

    public void onEnable() {
        super.onEnable();
        mLastEmitTime = 0;
    }

    public void onDrawFrame(float elapsed) {
        super.onDrawFrame(elapsed);

        float emitTime = 1 / EmissionRate;
        mLastEmitTime += elapsed;

        if (isEnabled() && !enableBurst) {
            if (mLastEmitTime >= emitTime) {
                emit();
                mLastEmitTime = 0;
            }
        }

        if ( enableBurst )
        {
            burst();
        }
    }

    protected void burst()
    {

        if ( mActiveParticles.size() == 0 ) {
            ArrayList<Particle> tempParticles = new ArrayList<Particle>();
            while (mNumParticles < MaxActiveParticles) {
                Particle particle = null;
                GVRSceneObject sceneObj = null;
                Vector3f pos = getNextPosition();
                Vector3f direction = getNextDirection(pos);
                float velocity = getNextVelocity();

                sceneObj = mMakeParticle.create(getGVRContext());
                sceneObj.setName(sceneObj.getName() + Integer.valueOf(mNumParticles).toString());
                ++mNumParticles;
                particle = new Particle(getGVRContext(), velocity, direction);
                sceneObj.attachComponent(particle);
                particle.setPosition(pos);
                tempParticles.add(particle);
                mBurstParticles.add(sceneObj);
            }

            for (int i = 0; i < mBurstParticles.size(); i++) {
                mActiveParticles.add(tempParticles.get(i));
                getOwnerObject().addChildObject(mBurstParticles.get(i));
                mBurstParticles.get(i).setEnable(true);
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
        Vector3f direction = new Vector3f(0, 0, 0);
        direction.x = pos.x - getOwnerObject().getTransform().getPositionX();
        direction.y = pos.y - getOwnerObject().getTransform().getPositionY();
        direction.z = pos.z - getOwnerObject().getTransform().getPositionZ();

        direction = direction.normalize();
        return direction;
    }

    private Vector3f getNextPosition() {
        float x = 0, y = 0, z = 0;
        do {
            x = mRandom.nextFloat() * 2 * mRadius - mRadius;
            y = mRandom.nextFloat() * 2 * mRadius - mRadius;
            z = mRandom.nextFloat() * 2 * mRadius - mRadius;
        }
        while (x * x + y * y + z * z > mRadius * mRadius);

        return new Vector3f(x, y, z);
    }

    private float getNextVelocity() {
        float velocity = Velocity.MinVal;
        if (Velocity.isRange()) {
            velocity += mRandom.nextFloat() * (Velocity.MaxVal - Velocity.MinVal);
        }
        return velocity;
    }
}