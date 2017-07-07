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

import org.gearvrf.GVRContext;
import org.joml.Vector3f;

import java.util.Random;


public class GVRSphericalEmitter extends GVREmitter{

    private Random mRandom = new Random();

    private float mRadius = 1.0f;
    private float totalTime = 0;
    private float mElapsedTime = 0;


    public GVRSphericalEmitter(GVRContext gvrContext) {
        super(gvrContext);
    }


    private float[] generateParticlePositions()
    {
        float[] positions = new float[mEmitRate * 3];

        for ( int i = 0; i < mEmitRate * 3; i += 3 )
        {
            float x = 0, y = 0, z = 0;
            do {
                x = mRandom.nextFloat() * 2 * mRadius - mRadius;
                y = mRandom.nextFloat() * 2 * mRadius - mRadius;
                z = mRandom.nextFloat() * 2 * mRadius - mRadius;
            }
            while (x * x + y * y + z * z > mRadius * mRadius);

            positions[i] = x;
            positions[i+1] = y;
            positions[i+2] = z;
        }
        return positions;
    }


    private float[] generateParticleTimeStamps(float totalTime)
    {
        float timeStamps[] = new float[mEmitRate * 2];

        if ( burstMode ) {
            for (int i = 0; i < mEmitRate * 2; i += 2) {
                timeStamps[i] = totalTime;
                timeStamps[i + 1] = 0;
            }
        }
        else {
            for (int i = 0; i < mEmitRate * 2; i += 2) {
                timeStamps[i] = totalTime + mRandom.nextFloat();
                timeStamps[i + 1] = 0;
            }
        }
        return timeStamps;

    }


    private float[] generateParticleVelocities()
    {
        float [] particleVelocities = new float[mEmitRate * 3];
        Vector3f temp = new Vector3f(0,0,0);
        for ( int i = 0; i < mEmitRate * 3 ; i +=3 )
        {
            temp.x = mParticlePositions[i];
            temp.y = mParticlePositions[i+1];
            temp.z = mParticlePositions[i+2];

            float scaleFactor = mRandom.nextFloat() * (maxVelocity - minVelocity)
                                + minVelocity;

            temp = temp.normalize() ;
            temp.mul(scaleFactor, temp);

            particleVelocities[i] = temp.x;
            particleVelocities[i+1] = temp.y;
            particleVelocities[i+2] = temp.z;
        }

        return particleVelocities;

    }

    @Override
    public void onDrawFrame(float frameTime) {

        totalTime += frameTime;

        tickClock(totalTime);


        if (mEnableEmitter) {

            mElapsedTime += frameTime;
            if (mElapsedTime > 1.0f) {
                mElapsedTime = 0;

                mParticlePositions = generateParticlePositions();
                mParticleVelocities = generateParticleVelocities();
                mParticleGenTimes = generateParticleTimeStamps(totalTime);

                emitWithBurstCheck(mParticlePositions, mParticleVelocities, mParticleGenTimes);

                super.onDrawFrame(frameTime);
            }
        }

    }

    protected void tickClock(float time)
    {
        super.tickClock(time);
    }

    public void setRadius( float radius )
    {
        mRadius = radius;
    }

}
