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


public class GVRPlaneEmitter extends GVREmitter {


    private Random mRandom = new Random();
    private float mWidth = 1.0f;
    private float mHeight = 1.0f;
    private float mElapsedTime = 0;

    public GVRPlaneEmitter(GVRContext gvrContext) {
        super(gvrContext);
    }

    private Vector3f getNextPosition() {

        float x = mRandom.nextFloat() * mWidth - mWidth/2;
        float z = mRandom.nextFloat() * mHeight - mHeight/2;
        return new Vector3f(x, 0, z);
    }

    private float[] generateParticlePositions()
    {
        float[] positions = new float[mEmitRate * 3];
        for ( int i = 0; i < mEmitRate * 3; i += 3 ) {

            Vector3f nextPos = getNextPosition();
            positions[i] = nextPos.x;
            positions[i+1] = nextPos.y;
            positions[i+2] = nextPos.z;
        }

        return positions;
    }

    private float getNextVelocity() {
        float velocity = minVelocity;
        velocity += mRandom.nextFloat() * (maxVelocity - minVelocity);

        return velocity;
    }

    private float[] generateParticleVelocities()
    {
        float velocities[] = new float[mEmitRate * 3];
        for ( int i = 0; i < mEmitRate * 3; i +=3 )
        {
            velocities[i] = 0;
            velocities[i+1] = getNextVelocity();
            velocities[i+2] = 0;

        }
        return velocities;
    }

    private float[] generateParticleTimeStamps(float totalTime)
    {
        float timeStamps[] = new float[mEmitRate * 2];
        for ( int i = 0; i < mEmitRate * 2; i +=2 )
        {
            timeStamps[i] = totalTime + mRandom.nextFloat();
            timeStamps[i + 1] = 0;
        }
        return timeStamps;
    }

    private float totalTime = 0;

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

    public void setPlaneWidth (float width)
    {
        mWidth = width;
    }

    public void setPlaneHeight( float length )
    {
        mHeight = length;
    }

}
