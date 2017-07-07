package org.gearvrf.particlesystem;

import android.provider.Settings;

import org.gearvrf.FutureWrapper;
import org.gearvrf.GVRAndroidResource;
import org.gearvrf.GVRBillboard;
import org.gearvrf.GVRContext;
import org.gearvrf.GVRDrawFrameListener;
import org.gearvrf.GVRMain;
import org.gearvrf.GVRMaterial;
import org.gearvrf.GVRMaterialShaderManager;
import org.gearvrf.GVRMesh;
import org.gearvrf.GVRRenderData;
import org.gearvrf.GVRScene;
import org.gearvrf.GVRSceneObject;
import org.gearvrf.GVRShaderTemplate;
import org.gearvrf.GVRTexture;
import org.gearvrf.particlesystem.R;
import org.gearvrf.particlesystem.emitters.GVREmitter;
import org.gearvrf.particlesystem.emitters.GVRPlaneEmitter;
import org.gearvrf.particlesystem.emitters.GVRSphericalEmitter;
import org.gearvrf.utility.Log;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Future;

import static android.opengl.GLES20.GL_LINE_STRIP;
import static android.opengl.GLES20.GL_POINTS;

public class SampleMain extends GVRMain {

    private GVRContext mGVRContext;

    GVREmitter fire, smoke, stars;
    ArrayList<GVREmitter> fwEmitters;
    GVRSceneObject fw;

    private boolean enableFireworks = false;
    private long mElapsedTime = 0;

    private static final float CUBE_WIDTH = 200.0f;


    @Override
    public void onInit(GVRContext gvrContext) throws IOException {

        mGVRContext = gvrContext;

        GVRScene scene = gvrContext.getMainScene();

        scene.getMainCameraRig().getTransform().setPosition(0,0,0);

        stars = createstars();
        stars.getTransform().setPosition(0,0,-50);
        stars.getTransform().setRotationByAxis(90, 1,0,0);
        scene.addSceneObject(stars);

        mElapsedTime = System.currentTimeMillis();

    }

    private int counter = 0;
    private int NUMBER_OF_SYSTEMS = 3;

    public void onTap()
    {
        counter = (counter + 1) % NUMBER_OF_SYSTEMS;
        switchSystem();
    }

    private void clearSystems()
    {
        if ( stars != null )
            stars.clearSystem();

        if( fire != null )
            fire.clearSystem();

        if(smoke != null )
            smoke.clearSystem();

        if (fwEmitters != null ) {
            for (GVREmitter em : fwEmitters)
                if (em != null)
                    em.clearSystem();
            fwEmitters = null;
        }
    }

    private void switchSystem()
    {
        clearSystems();
        mGVRContext.getMainScene().removeAllSceneObjects();


        if ( counter == 0 )
        {
            fire = createFire();
            smoke = createSmoke();
            fire.getTransform().setPosition(0,-5.0f,-10);
            smoke.getTransform().setPosition(0,-3.0f,-10);

            mGVRContext.getMainScene().addSceneObject(fire);
            mGVRContext.getMainScene().addSceneObject(smoke);
            enableFireworks = false;
        }
        else if (counter == 2 )
        {
            stars = createstars();
            stars.getTransform().setPosition(0,0,-50);
            stars.getTransform().setRotationByAxis(90, 1,0,0);

            mGVRContext.getMainScene().addSceneObject(stars);
            enableFireworks = false;
        }
        else if ( counter == 1)
        {
            fw = createFireworks();
            fw.getTransform().setPosition(0, 10, -20.0f);
            mGVRContext.getMainScene().addSceneObject(fw);
            enableFireworks = true;
        }
    }

    @Override
    public void onStep()
    {
        if (enableFireworks)
        {
            long currTime = System.currentTimeMillis();

            if ( currTime - mElapsedTime > 6000 ) {
                mGVRContext.getMainScene().removeAllSceneObjects();
                clearSystems();
                fw = null;
                fw = createFireworks();
                fw.getTransform().setPosition(0,10, -20);
                mGVRContext.getMainScene().addSceneObject(fw);
                mElapsedTime = currTime;
            }
        }
    }

    private GVREmitter createstars()
    {
        GVRTexture starsTexture = mGVRContext.getAssetLoader().loadTexture(
                new GVRAndroidResource(mGVRContext, R.drawable.stars));

        GVRPlaneEmitter starsEmitter = new GVRPlaneEmitter(mGVRContext);

        starsEmitter.setPlaneWidth(100);
        starsEmitter.setPlaneHeight(100);
        starsEmitter.setParticleSize(5.0f);
        starsEmitter.setVelocityRange(2.0f, 15.5f);
        starsEmitter.setEmitRate(300);
        starsEmitter.setFadeWithAge(false);
        starsEmitter.setEnvironmentAcceleration(new Vector3f(0,0,0));
        starsEmitter.setParticleVolume(50.0f, 100.0f, 50.0f);
        starsEmitter.setParticleAge(10);
        starsEmitter.setParticleTexture(starsTexture);

        return starsEmitter;
    }

    private  GVREmitter createSmoke()
    {
        GVRTexture smokeTexture = mGVRContext.getAssetLoader().loadTexture(
                new GVRAndroidResource(mGVRContext, R.drawable.smoke));

        GVRPlaneEmitter smokeEmitter = new GVRPlaneEmitter(mGVRContext);

        smokeEmitter.setPlaneWidth(0.7f);
        smokeEmitter.setPlaneHeight(0.7f);
        smokeEmitter.setParticleSize(80.0f);
        smokeEmitter.setVelocityRange(1.0f, 2.0f);
        smokeEmitter.setEmitRate(50);
        smokeEmitter.setFadeWithAge(true);
        smokeEmitter.setEnvironmentAcceleration(new Vector3f(0,0,0));
        smokeEmitter.setParticleVolume(10.0f, 20.0f, 10.0f);
        smokeEmitter.setParticleAge(2.5f);
        smokeEmitter.setParticleTexture(smokeTexture);
        smokeEmitter.setParticleSizeChangeRate(5.0f);
        smokeEmitter.setColorMultiplier(new Vector4f(1.0f, 1.0f, 1.0f, 0.06f));
//        smokeEmitter.getTransform().setPosition(0,-3.0f,-10);

        return smokeEmitter;
    }

    private GVREmitter createFire()
    {
        GVRPlaneEmitter fireEmitter = new GVRPlaneEmitter(mGVRContext);
        GVRTexture texture = mGVRContext.getAssetLoader().loadTexture(
                new GVRAndroidResource(mGVRContext, R.drawable.fireparticle));

        fireEmitter.setEmitRate(30);
        fireEmitter.setBurstMode(false);
        fireEmitter.setParticleAge(4.0f);
        fireEmitter.setVelocityRange(0.5f, 1.5f);
        fireEmitter.setEnvironmentAcceleration(new Vector3f(0,0.0f,0));
        fireEmitter.setParticleVolume(50.0f, 100.0f, 50.0f);
        fireEmitter.setParticleSizeChangeRate(-6.0f);
        fireEmitter.setFadeWithAge(true);
        fireEmitter.setParticleSize(50.0f);
        fireEmitter.setParticleTexture(texture);
//
//        fireEmitter.getTransform().setPosition(0,-5.0f,-10);

        return fireEmitter;
    }


    private GVRSceneObject createFireworks()
    {
        ArrayList<GVREmitter> fwEmits = createFireworkEmitters();
        GVRSceneObject fw = new GVRSceneObject(mGVRContext);
        for ( GVREmitter emitter : fwEmits )
            fw.addChildObject(emitter);

        return fw;
    }


    private ArrayList<GVREmitter> createFireworkEmitters()
    {

        GVRTexture fireworksTexture1 = mGVRContext.getAssetLoader().loadTexture(
                new GVRAndroidResource(mGVRContext, R.drawable.stars));

        GVRTexture fireworksTexture2 = mGVRContext.getAssetLoader().loadTexture(
                new GVRAndroidResource(mGVRContext, R.drawable.fireparticle));

        GVRTexture fireworksTexture3 = mGVRContext.getAssetLoader().loadTexture(
                new GVRAndroidResource(mGVRContext, R.drawable.fworks1));

        GVRTexture fireworksTexture4 = mGVRContext.getAssetLoader().loadTexture(
                new GVRAndroidResource(mGVRContext, R.drawable.fworks2));


        GVRSphericalEmitter fworksEmitter1 = new GVRSphericalEmitter(mGVRContext);
        GVRSphericalEmitter fworksEmitter2 = new GVRSphericalEmitter(mGVRContext);
        GVRSphericalEmitter fworksEmitter3 = new GVRSphericalEmitter(mGVRContext);
        GVRSphericalEmitter fworksEmitter4 = new GVRSphericalEmitter(mGVRContext);

        fworksEmitter1.setRadius(0.1f);
        fworksEmitter1.setFadeWithAge(true);
        fworksEmitter1.setParticleSize(10.0f);
        fworksEmitter1.setEmitRate(100);
        fworksEmitter1.setParticleAge(6.0f);
        fworksEmitter1.setBurstMode(true);
        fworksEmitter1.setEnvironmentAcceleration(new Vector3f(0,-2.0f,0));
        fworksEmitter1.setParticleTexture(fireworksTexture1);

        fworksEmitter2.setRadius(0.1f);
        fworksEmitter2.setFadeWithAge(true);
        fworksEmitter2.setParticleSize(15.0f);
        fworksEmitter2.setEmitRate(150);
        fworksEmitter2.setParticleAge(6f);
        fworksEmitter2.setBurstMode(true);
        fworksEmitter2.setEnvironmentAcceleration(new Vector3f(0,-2.0f,0));
        fworksEmitter2.setParticleTexture(fireworksTexture2);

        fworksEmitter3.setRadius(0.1f);
        fworksEmitter3.setFadeWithAge(true);
        fworksEmitter3.setParticleSize(12.0f);
        fworksEmitter3.setEmitRate(100);
        fworksEmitter3.setParticleAge(6.0f);
        fworksEmitter3.setBurstMode(true);
        fworksEmitter3.setEnvironmentAcceleration(new Vector3f(0,-2.0f,0));
        fworksEmitter3.setParticleTexture(fireworksTexture3);

        fworksEmitter4.setRadius(0.1f);
        fworksEmitter4.setFadeWithAge(true);
        fworksEmitter4.setParticleSize(11.0f);
        fworksEmitter4.setEmitRate(100);
        fworksEmitter4.setParticleAge(6);
        fworksEmitter4.setBurstMode(true);
        fworksEmitter4.setEnvironmentAcceleration(new Vector3f(0,-2.0f,0));
        fworksEmitter4.setParticleTexture(fireworksTexture4);


        fworksEmitter1.setParticleVolume(100, 100, 100);
        fworksEmitter2.setParticleVolume(100, 100, 100);
        fworksEmitter3.setParticleVolume(100, 100, 100);
        fworksEmitter4.setParticleVolume(100, 100, 100);

        fwEmitters = new ArrayList<GVREmitter>();

        fwEmitters.add(fworksEmitter1);
        fwEmitters.add(fworksEmitter2);
        fwEmitters.add(fworksEmitter3);
        fwEmitters.add(fworksEmitter4);

        return fwEmitters;
    }

    private void addSurroundings(GVRContext gvrContext, GVRScene scene) {
        FutureWrapper<GVRMesh> futureQuadMesh = new FutureWrapper<GVRMesh>(
                gvrContext.createQuad(CUBE_WIDTH, CUBE_WIDTH));
        Future<GVRTexture> futureCubemapTexture = gvrContext.getAssetLoader()
                .loadFutureCubemapTexture(
                        new GVRAndroidResource(gvrContext, R.raw.earth));

        GVRMaterial cubemapMaterial = new GVRMaterial(gvrContext,
                GVRMaterial.GVRShaderType.Cubemap.ID);
        cubemapMaterial.setMainTexture(futureCubemapTexture);

        GVRSceneObject frontFace = new GVRSceneObject(gvrContext,
                futureQuadMesh, futureCubemapTexture);
        frontFace.getRenderData().setMaterial(cubemapMaterial);
        frontFace.setName("front");
        frontFace.getRenderData().setRenderingOrder(GVRRenderData.GVRRenderingOrder.BACKGROUND);
        scene.addSceneObject(frontFace);
        frontFace.getTransform().setPosition(0.0f, 0.0f, -CUBE_WIDTH * 0.5f);

        GVRSceneObject backFace = new GVRSceneObject(gvrContext, futureQuadMesh,
                futureCubemapTexture);
        backFace.getRenderData().setMaterial(cubemapMaterial);
        backFace.getRenderData().setRenderingOrder(GVRRenderData.GVRRenderingOrder.BACKGROUND);
        backFace.setName("back");
        scene.addSceneObject(backFace);
        backFace.getTransform().setPosition(0.0f, 0.0f, CUBE_WIDTH * 0.5f);
        backFace.getTransform().rotateByAxis(180.0f, 0.0f, 1.0f, 0.0f);

        GVRSceneObject leftFace = new GVRSceneObject(gvrContext, futureQuadMesh,
                futureCubemapTexture);
        leftFace.getRenderData().setMaterial(cubemapMaterial);
        leftFace.getRenderData().setRenderingOrder(GVRRenderData.GVRRenderingOrder.BACKGROUND);
        leftFace.setName("left");
        scene.addSceneObject(leftFace);
        leftFace.getTransform().setPosition(-CUBE_WIDTH * 0.5f, 0.0f, 0.0f);
        leftFace.getTransform().rotateByAxis(90.0f, 0.0f, 1.0f, 0.0f);

        GVRSceneObject rightFace = new GVRSceneObject(gvrContext,
                futureQuadMesh, futureCubemapTexture);
        rightFace.getRenderData().setMaterial(cubemapMaterial);
        rightFace.getRenderData().setRenderingOrder(GVRRenderData.GVRRenderingOrder.BACKGROUND);
        rightFace.setName("right");
        scene.addSceneObject(rightFace);
        rightFace.getTransform().setPosition(CUBE_WIDTH * 0.5f, 0.0f, 0.0f);
        rightFace.getTransform().rotateByAxis(-90.0f, 0.0f, 1.0f, 0.0f);

        GVRSceneObject topFace = new GVRSceneObject(gvrContext, futureQuadMesh,
                futureCubemapTexture);
        topFace.getRenderData().setMaterial(cubemapMaterial);
        topFace.getRenderData().setRenderingOrder(GVRRenderData.GVRRenderingOrder.BACKGROUND);
        topFace.setName("top");
        scene.addSceneObject(topFace);
        topFace.getTransform().setPosition(0.0f, CUBE_WIDTH * 0.5f, 0.0f);
        topFace.getTransform().rotateByAxis(90.0f, 1.0f, 0.0f, 0.0f);

        GVRSceneObject bottomFace = new GVRSceneObject(gvrContext,
                futureQuadMesh, futureCubemapTexture);
        bottomFace.getRenderData().setMaterial(cubemapMaterial);
        bottomFace.getRenderData().setRenderingOrder(GVRRenderData.GVRRenderingOrder.BACKGROUND);
        bottomFace.setName("bottom");
        scene.addSceneObject(bottomFace);
        bottomFace.getTransform().setPosition(0.0f, -CUBE_WIDTH * 0.5f, 0.0f);
        bottomFace.getTransform().rotateByAxis(-90.0f, 1.0f, 0.0f, 0.0f);
    }

}
