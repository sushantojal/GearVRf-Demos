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

package org.gearvrf.particlesystem.emitters;

import android.os.Debug;

import org.gearvrf.GVRContext;
import org.gearvrf.GVRDrawFrameListener;
import org.gearvrf.GVRMaterial;
import org.gearvrf.GVRMesh;
import org.gearvrf.GVRRenderData;
import org.gearvrf.GVRScene;
import org.gearvrf.GVRSceneObject;
import org.gearvrf.GVRTexture;
import org.gearvrf.particlesystem.Particles;
import org.gearvrf.utility.Log;
import org.gearvrf.x3d.InteractiveObject;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class GVREmitter extends GVRSceneObject implements GVRDrawFrameListener {

    private int MAX_EMIT_RATE = 600;

    protected int mEmitRate = 600;
    protected boolean mEnableEmitter = true;
    private GVRContext mGVRContext = null;

    protected ArrayList<GVRSceneObject> mActiveParticleMeshes = null;
    private  ArrayList<GVRSceneObject> mParticleMeshPool = null;
    protected ArrayList<Float> mParticleMeshSpawnTimes;

    protected float mMaxAge = 1.5f;

    protected float mParticleSize = 50.0f;


    protected float minVelocity = 1.5f;
    protected float maxVelocity = 4.5f;

    protected Vector3f mEnvironmentAcceleration ;
    private Vector4f mColor;

    private float currTime = 0;

    protected float[] mParticlePositions;
    protected float[] mParticleVelocities;
    protected float [] mParticleGenTimes;

    protected boolean burstMode = false;
    protected float burstLoopTime  = 2.0f;

    private float mParticleSizeRate = 0.0f;
    private boolean mFadeWithAge = false;

    private GVRTexture mParticleTexture;

    public GVREmitter(GVRContext gvrContext)
    {
        super(gvrContext);
        mGVRContext = gvrContext;
        mGVRContext.registerDrawFrameListener(this);
        mActiveParticleMeshes = new ArrayList<GVRSceneObject>();
        mParticleMeshSpawnTimes = new ArrayList<Float>();
        mParticleMeshPool = new ArrayList<GVRSceneObject>();
        mEnvironmentAcceleration = new Vector3f(0.0f,0.0f,0.0f);
        mColor = new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
        setParticleVolume(100,100,100);
    }

    public void setEmitter(boolean state)
    {
        mEnableEmitter = state;
        if (state)
            mGVRContext.registerDrawFrameListener(this);
        else
            mGVRContext.unregisterDrawFrameListener(this);
    }

    /**
     * Particle meshes are generated for upto one second in the future.
     * Therefore the maximum time particle mesh should be allowed to remain
     * in the scene is (maxAge of the particles + one second). Allowing half
     * a second as buffer time.
     * @param frameTime Seconds since the previous frame
     */


    @Override
    public void onDrawFrame(float frameTime)
    {
        int numActiveMeshes = mActiveParticleMeshes.size();
        for ( int i = 0; i < numActiveMeshes; i ++ )
        {
            float currMeshSpawnTime = mParticleMeshSpawnTimes.get(i);

            if ( currTime - currMeshSpawnTime > mMaxAge + 1 )
            {
                GVRSceneObject toDelete = mActiveParticleMeshes.get(i);
                this.removeChildObject(toDelete);
                mParticleMeshSpawnTimes.remove(i);
                mActiveParticleMeshes.remove(i);
            }
        }
    }

    private boolean executeOnce = true;


    protected  void emitWithBurstCheck(float[] particlePositions, float[] particleVelocities,
                         float[] particleTimeStamps)
    {

        if ( burstMode )
        {
            if ( executeOnce )
            {
                emit(particlePositions, particleVelocities, particleTimeStamps);
                executeOnce = false;
            }
        }
        else
        {
            emit(particlePositions, particleVelocities, particleTimeStamps);
        }

    }

    private void emit(float[] particlePositions, float[] particleVelocities,
                      float[] particleTimeStamps)
    {
        float[] allParticlePositions = new float[particlePositions.length + particleBoundingVolume.length];
        System.arraycopy(particlePositions, 0, allParticlePositions, 0, particlePositions.length);
        System.arraycopy(particleBoundingVolume, 0, allParticlePositions,
                        particlePositions.length, particleBoundingVolume.length);


        float[] allSpawnTimes = new float[particleTimeStamps.length + BVSpawnTimes.length];
        System.arraycopy(particleTimeStamps, 0, allSpawnTimes, 0, particleTimeStamps.length);
        System.arraycopy(BVSpawnTimes, 0, allSpawnTimes, particleTimeStamps.length, BVSpawnTimes.length);

        float[] allParticleVelocities = new float[particleVelocities.length + BVVelocities.length];
        System.arraycopy(particleVelocities, 0, allParticleVelocities, 0, particleVelocities.length);
        System.arraycopy(BVVelocities, 0, allParticleVelocities, particleVelocities.length, BVVelocities.length);


        Particles particleMesh = new Particles(mGVRContext, mMaxAge,
                mParticleSize, mEnvironmentAcceleration, mParticleSizeRate, mFadeWithAge,
                mParticleTexture, mColor);


        GVRSceneObject particleObject = particleMesh.makeParticleMesh(allParticlePositions,
                allParticleVelocities, allSpawnTimes);

        this.addChildObject(particleObject);
        mActiveParticleMeshes.add(particleObject);
        mParticleMeshSpawnTimes.add(currTime);
    }

    private float[] particleBoundingVolume;
    private float[] BVSpawnTimes;
    private float[] BVVelocities;

    public void setParticleVolume(float width, float height, float depth)
    {
        Vector3f center = new Vector3f(this.getTransform().getPositionX(),
                this.getTransform().getPositionY(), this.getTransform().getPositionZ());

        particleBoundingVolume = new float[]{center.x - width/2, center.y - height/2, center.z - depth/2,
                                       center.x - width/2, center.y - height/2, center.z + depth/2,
                                       center.x + width/2, center.y - height/2, center.z + depth/2,
                                       center.x + width/2, center.y - height/2, center.z - depth/2,

                                       center.x - width/2, center.y + height/2, center.z - depth/2,
                                       center.x - width/2, center.y + height/2, center.z + depth/2,
                                       center.x + width/2, center.y + height/2, center.z + depth/2,
                                       center.x - width/2, center.y + height/2, center.z - depth/2};

        BVSpawnTimes = new float[]{100, 0, 100, 0, 100, 0, 100, 0, 100, 0, 100, 0, 100, 0, 100, 0};

        BVVelocities = new float[24];
        for ( int i = 0; i < 24; i ++ )
            BVVelocities[i] = 0;
    }

    protected void tickClock(float time)
    {
        currTime = time;

        for (GVRSceneObject partObj: mActiveParticleMeshes)
        {
            partObj.getRenderData().getMaterial().setFloat("u_time", time);
        }
    }

    public void setEmitRate(int emitRate)
    {
        if ( emitRate > MAX_EMIT_RATE)
            mEmitRate = MAX_EMIT_RATE;
        else
            mEmitRate = emitRate;
    }

    public int getEmitRate()
    {
        return mEmitRate;
    }

    public void setParticleAge ( float age )
    {
        mMaxAge = age;
    }

    public void setParticleSize ( float size )
    {
        mParticleSize = size;
    }

    public void setVelocityRange( float minV, float maxV )
    {
        minVelocity = minV;
        maxVelocity = maxV;
    }

    public void setEnvironmentAcceleration( Vector3f acceleration )
    {
        mEnvironmentAcceleration = acceleration;
    }


    public void setParticleSizeChangeRate( float rate )
    {
        mParticleSizeRate = rate;
    }

    public void setFadeWithAge ( boolean fade )
    {
        mFadeWithAge = fade;
    }

    public void setBurstMode(boolean mode)
    {
        burstMode = mode;
    }

    public void setParticleTexture(GVRTexture tex)
    {
        mParticleTexture = tex;
    }

    public void  setColorMultiplier( Vector4f color )
    {
        mColor = color;
    }

    public void clearSystem()
    {
        mGVRContext.unregisterDrawFrameListener(this);
        int nchildren = this.getChildrenCount();
        for( int i = 0; i < nchildren; i ++ )
        {
            this.removeChildObject(this.getChildByIndex(0));
        }
    }
}
