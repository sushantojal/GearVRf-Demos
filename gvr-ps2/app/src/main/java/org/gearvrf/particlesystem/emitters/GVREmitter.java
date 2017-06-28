package org.gearvrf.particlesystem.emitters;

import android.os.Debug;

import org.gearvrf.GVRContext;
import org.gearvrf.GVRDrawFrameListener;
import org.gearvrf.GVRScene;
import org.gearvrf.GVRSceneObject;
import org.gearvrf.particlesystem.Particles;
import org.gearvrf.utility.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by sushant.o on 6/23/2017.
 */

public class GVREmitter extends GVRSceneObject implements GVRDrawFrameListener {

    protected int mEmitRate = 300;
    protected boolean mEnableEmitter = true;
    private GVRContext mGVRContext = null;
    protected int maxEmitRate = 1000;

    protected ArrayList<GVRSceneObject> mActiveParticleMeshes = null;
    private  ArrayList<GVRSceneObject> mParticleMeshPool = null;
    protected ArrayList<Float> mParticleTimeStamps;

    protected float maxDistance = 20.0f;


    protected float minVelocity = 0.5f;
    protected float maxVelocity = 5.0f;

    protected boolean fadeWithDistance = true;


    public GVREmitter(GVRContext gvrContext)
    {
        super(gvrContext);
        mGVRContext = gvrContext;
        mGVRContext.registerDrawFrameListener(this);
        mActiveParticleMeshes = new ArrayList<GVRSceneObject>();
        mParticleMeshPool = new ArrayList<GVRSceneObject>();
    }

    public void setEmitRate(int emitRate)
    {
        mEmitRate = emitRate;
    }

    public int getEmitRate()
    {
        return mEmitRate;
    }

    public void setEmitter(boolean state)
    {
        mEnableEmitter = state;
        if (state)
            mGVRContext.registerDrawFrameListener(this);
        else
            mGVRContext.unregisterDrawFrameListener(this);
    }

    @Override
    public void onDrawFrame(float frameTime) {



    }

    public void setVelocityRange( float minV, float maxV )
    {
        minVelocity = minV;
        maxVelocity = maxV;
    }


    private float totalTime;
    protected  void emit(float[] particlePositions, float[] particleVelocities,
                         float[] particleTimeStamps)
    {

        Log.e("sushant", "emitting");
        Particles particleMesh = new Particles(mGVRContext);
        GVRSceneObject particleObject = particleMesh.makeParticleMesh(particlePositions,
                particleVelocities, particleTimeStamps);

        //particleObject.getRenderData().getMaterial().setFloat("u_time", totalTime);

        this.addChildObject(particleObject);
        mActiveParticleMeshes.add(particleObject);

        //todo: manage particle mesh pool
    }

    protected void tickClock(float time)
    {
        for (GVRSceneObject partObj: mActiveParticleMeshes)
        {
            partObj.getRenderData().getMaterial().setFloat("u_time", time);
        }
    }
}
