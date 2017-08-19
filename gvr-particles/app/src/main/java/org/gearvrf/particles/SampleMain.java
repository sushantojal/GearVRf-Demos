package org.gearvrf.particles;

import android.graphics.Color;

import org.gearvrf.FutureWrapper;
import org.gearvrf.GVRAndroidResource;
import org.gearvrf.GVRAssetLoader;
import org.gearvrf.GVRBitmapTexture;
import org.gearvrf.GVRContext;
import org.gearvrf.GVRMain;
import org.gearvrf.GVRMaterial;
import org.gearvrf.GVRMesh;
import org.gearvrf.GVRRenderData;
import org.gearvrf.GVRRenderPass;
import org.gearvrf.GVRScene;
import org.gearvrf.GVRSceneObject;
import org.gearvrf.GVRSpotLight;
import org.gearvrf.GVRTexture;
import org.gearvrf.particlesystem.GVRPlaneEmitter;
import org.gearvrf.particlesystem.GVRSphericalEmitter;
import org.gearvrf.scene_objects.GVRModelSceneObject;
import org.gearvrf.scene_objects.GVRSphereSceneObject;
import org.gearvrf.utility.Colors;
import org.gearvrf.utility.Log;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;


public class SampleMain extends GVRMain {

    private GVRContext mGVRContext;

    //particle emitters
    GVRPlaneEmitter fire, smoke, stars, snow;

    //for fireworks, have more than one emitter with different textures
    ArrayList<GVRSphericalEmitter> fwEmitters;
    GVRSceneObject fw1; GVRSceneObject fw2; GVRSceneObject fw3;

    private boolean enableFireworks = false;
    private long mElapsedTime = 0;

    private static final float CUBE_WIDTH = 200.0f;

    private int counter = 0;
    private int NUMBER_OF_SYSTEMS = 4;

    @Override
    public void onInit(GVRContext gvrContext) throws IOException {

        mGVRContext = gvrContext;

        GVRScene scene = gvrContext.getMainScene();

        scene.getMainCameraRig().getTransform().setPosition(0,0,0);


        GVRSphericalEmitter test = new GVRSphericalEmitter(mGVRContext);

        GVRTexture fireworksTexture = mGVRContext.getAssetLoader().loadTexture(
                new GVRAndroidResource(mGVRContext, R.raw.fworks5));

        test.setRadius(0.1f);
        test.setFadeWithAge(true);
        test.setParticleSize(10.0f);
        test.setEmitRate(50);
        test.setParticleAge(5.5f);
        test.setBurstMode(false);
        test.setEnvironmentAcceleration(new Vector3f(0,-2.0f,0));
        test.setParticleTexture(fireworksTexture);
        test.getTransform().setPosition(1,3,-5);
        test.setParticleVolume(100,100,100);
        mGVRContext.getMainScene().addSceneObject(test);




        //attach the stars emitter initially
//        stars = createstars();
//        stars.getTransform().setPosition(0,0,-50);
//        stars.getTransform().setRotationByAxis(90, 1,0,0);
//        scene.addSceneObject(stars);

//        snow = createSnow();
//        snow.getTransform().setPosition(0,10,0);
//        //stars.getTransform().setRotationByAxis(90, 1,0,0);
//        scene.addSceneObject(snow);


//        fire = createFire();
//        smoke = createSmoke();
//        fire.getTransform().setPosition(5.2f,-5.0f,-10.5f);
//        smoke.getTransform().setPosition(5.2f,-5.0f,-10.5f);
//
//        mGVRContext.getMainScene().addSceneObject(fire);
//        mGVRContext.getMainScene().addSceneObject(smoke);
        enableFireworks = false;

        mElapsedTime = System.currentTimeMillis();
    }


    //tap to toggle between systems
    public void onTap() throws IOException {
        counter = (counter + 1) % NUMBER_OF_SYSTEMS;
        switchSystem();
    }

    //clear all systems
    private void clearSystems()
    {
        if ( stars != null )
            stars.clearSystem();

        if( fire != null )
            fire.clearSystem();

        if(smoke != null )
            smoke.clearSystem();

        if ( snow!= null )
            snow.clearSystem();

        if (fwEmitters != null ) {
            for (GVRSphericalEmitter em : fwEmitters)
                if (em != null)
                    em.clearSystem();
            fwEmitters = null;
        }
    }

    private void switchSystem() throws IOException {
        clearSystems();
        mGVRContext.getMainScene().removeAllSceneObjects();

        if ( counter == 1 )
        {
            fire = createFire();
            smoke = createSmoke();
            fire.getTransform().setPosition(5.2f,-5.0f,-10.5f);
            smoke.getTransform().setPosition(5.2f,-5.0f,-10.5f);

            mGVRContext.getMainScene().addSceneObject(fire);
            mGVRContext.getMainScene().addSceneObject(smoke);
            enableFireworks = false;
        }

        else if ( counter == 3)
        {
            fwEmitters = new ArrayList<GVRSphericalEmitter>();
            loadSky();
            fw1 = createFireworks();
            fw2 = createFireworks();
            fw3 = createFireworks();


            fw1.getTransform().setPosition(0, 10, -20.0f);
            mGVRContext.getMainScene().addSceneObject(fw1);

            fw2.getTransform().setPosition(3, 15, -20.0f);
            mGVRContext.getMainScene().addSceneObject(fw2);

            fw3.getTransform().setPosition(-5, 20, -20.0f);
            mGVRContext.getMainScene().addSceneObject(fw3);

            enableFireworks = true;
        }

        else if (counter == 0 )
        {
            stars = createstars();
            stars.getTransform().setPosition(0,0,-50);
            stars.getTransform().setRotationByAxis(90, 1,0,0);

            mGVRContext.getMainScene().addSceneObject(stars);
            enableFireworks = false;
        }

        else if (counter == 2 )
        {

            snow = createSnow();
            snow.getTransform().setPosition(0,10,0);
            //stars.getTransform().setRotationByAxis(90, 1,0,0);
            mGVRContext.getMainScene().addSceneObject(snow);
            enableFireworks = false;
        }
    }

    @Override
    public void onStep()
    {
        //loop over if the fireworks are enabled
//        if (enableFireworks)
//        {
//            long currTime = System.currentTimeMillis();
//
//            if ( currTime - mElapsedTime > 4000 ) {
//                mGVRContext.getMainScene().removeAllSceneObjects();
//                clearSystems();
//                fw1 = null; fw2 = null; fw3 = null;
//                fwEmitters = new ArrayList<GVRSphericalEmitter>();
//                loadSky();
//                fw1 = createFireworks(); fw2 = createFireworks(); fw3 = createFireworks();
//                float randomNum = ThreadLocalRandom.current().nextInt(10, 50 + 1);
//                fw1.getTransform().setPosition(randomNum,randomNum, -20);
//                randomNum = ThreadLocalRandom.current().nextInt(10, 50 + 1);
//                fw2.getTransform().setPosition(randomNum,randomNum, -20);
//                randomNum = ThreadLocalRandom.current().nextInt(10, 50 + 1);
//                fw3.getTransform().setPosition(randomNum,randomNum, -20);
//                mGVRContext.getMainScene().addSceneObject(fw1);
//                mGVRContext.getMainScene().addSceneObject(fw2);
//                mGVRContext.getMainScene().addSceneObject(fw3);
//                mElapsedTime = currTime;
//            }
//        }
    }

    //---------------------------------------STARS-----------------------------------
    private GVRPlaneEmitter createstars()
    {
        GVRTexture starsTexture = mGVRContext.getAssetLoader().loadTexture(
                new GVRAndroidResource(mGVRContext, R.drawable.stars));

        GVRPlaneEmitter starsEmitter = new GVRPlaneEmitter(mGVRContext);

        starsEmitter.setPlaneWidth(100);
        starsEmitter.setPlaneHeight(100);
        starsEmitter.setParticleSize(5.0f);
        starsEmitter.setVelocityRange(new Vector3f(0,2.0f,0), new Vector3f(0,15.5f,0));
        starsEmitter.setEmitRate(300);
        starsEmitter.setFadeWithAge(false);
        starsEmitter.setEnvironmentAcceleration(new Vector3f(0,0,0));
        starsEmitter.setParticleVolume(50.0f, 100.0f, 50.0f);
        starsEmitter.setParticleAge(10);
        starsEmitter.setParticleTexture(starsTexture);

        return starsEmitter;
    }


    //-----------------------------------------SMOKE-----------------------------------
    private  GVRPlaneEmitter createSmoke()
    {
        GVRTexture smokeTexture = mGVRContext.getAssetLoader().loadTexture(
                new GVRAndroidResource(mGVRContext, R.drawable.smoke));

        GVRPlaneEmitter smokeEmitter = new GVRPlaneEmitter(mGVRContext);

        smokeEmitter.setPlaneWidth(1.5f);
        smokeEmitter.setPlaneHeight(1.5f);
        smokeEmitter.setParticleSize(80.0f);
        smokeEmitter.setVelocityRange(new Vector3f(0,2.0f,0), new Vector3f(0,5.0f,0));
        smokeEmitter.setEmitRate(100);
        smokeEmitter.setFadeWithAge(true);
        smokeEmitter.setEnvironmentAcceleration(new Vector3f(0,0,0));
        smokeEmitter.setParticleVolume(10.0f, 20.0f, 10.0f);
        smokeEmitter.setParticleAge(1.5f);
        smokeEmitter.setParticleTexture(smokeTexture);
        smokeEmitter.setParticleSizeChangeRate(6.0f);
        smokeEmitter.setColorMultiplier(new Vector4f(1.0f, 1.0f, 1.0f, 0.06f));

        return smokeEmitter;
    }


    //----------------------------------------------FIRE-----------------------------------
    private GVRPlaneEmitter createFire() throws IOException {

        loadCampModel();

        GVRPlaneEmitter fireEmitter = new GVRPlaneEmitter(mGVRContext);
        GVRTexture texture = mGVRContext.getAssetLoader().loadTexture(
                new GVRAndroidResource(mGVRContext, R.drawable.fire));

        fireEmitter.setEmitRate(250);
        fireEmitter.setBurstMode(false);
        fireEmitter.setPlaneWidth(1.0f);
        fireEmitter.setPlaneHeight(1.0f);
        fireEmitter.setParticleAge(0.7f);
        fireEmitter.setVelocityRange(new Vector3f(0,1.0f,0), new Vector3f(0,4.0f,0));
        fireEmitter.setEnvironmentAcceleration(new Vector3f(0,0.0f,0));
        fireEmitter.setParticleVolume(50.0f, 100.0f, 50.0f);
        fireEmitter.setParticleSizeChangeRate(-6.0f);
        fireEmitter.setFadeWithAge(true);
        fireEmitter.setParticleSize(60.0f);
        fireEmitter.setParticleTexture(texture);
        fireEmitter.setNoiseFactor(0.07f);

        return fireEmitter;
    }


    private GVRPlaneEmitter createSnow()
    {
        loadSnowHills();
        GVRPlaneEmitter snowEmitter = new GVRPlaneEmitter(mGVRContext);
        GVRTexture texture = mGVRContext.getAssetLoader().loadTexture(
                new GVRAndroidResource(mGVRContext, R.raw.snow));

        snowEmitter.setPlaneWidth(60);
        snowEmitter.setPlaneHeight(60);
        snowEmitter.setParticleSize(2.0f);
        snowEmitter.setVelocityRange(new Vector3f(-1.0f,-3.0f, -1.0f),
                new Vector3f(1.8f, -4.0f, 1.8f));
        snowEmitter.setEmitRate(600);
        snowEmitter.setFadeWithAge(false);
        snowEmitter.setEnvironmentAcceleration(new Vector3f(0,0,0));
        snowEmitter.setParticleVolume(50.0f, 100.0f, 50.0f);
        snowEmitter.setParticleAge(20);
        snowEmitter.setParticleTexture(texture);
        //snowEmitter.setNoiseFactor(0.1f);
        //snowEmitter.setParticleSizeChangeRate(1.01f);
        return snowEmitter;


    }


    //---------------------------------------------FIREWORKS----------------------------------
    private GVRSceneObject createFireworks()
    {

        ArrayList<GVRSphericalEmitter> fwEmits = createFireworkEmitters();
        GVRSceneObject fw = new GVRSceneObject(mGVRContext);
        for ( GVRSphericalEmitter emitter : fwEmits )
            fw.addChildObject(emitter);

        return fw;
    }




    private ArrayList<GVRSphericalEmitter> createFireworkEmitters()
    {
        GVRTexture fireworksTexture1 = mGVRContext.getAssetLoader().loadTexture(
                new GVRAndroidResource(mGVRContext, R.drawable.stars));

        GVRTexture fireworksTexture2 = mGVRContext.getAssetLoader().loadTexture(
                new GVRAndroidResource(mGVRContext, R.raw.fworks4));

        GVRTexture fireworksTexture3 = mGVRContext.getAssetLoader().loadTexture(
                new GVRAndroidResource(mGVRContext, R.drawable.fworks));

        GVRTexture fireworksTexture4 = mGVRContext.getAssetLoader().loadTexture(
                new GVRAndroidResource(mGVRContext, R.raw.fworks5));


        GVRSphericalEmitter fworksEmitter1 = new GVRSphericalEmitter(mGVRContext);
        GVRSphericalEmitter fworksEmitter2 = new GVRSphericalEmitter(mGVRContext);
        GVRSphericalEmitter fworksEmitter3 = new GVRSphericalEmitter(mGVRContext);
        GVRSphericalEmitter fworksEmitter4 = new GVRSphericalEmitter(mGVRContext);

        fworksEmitter1.setRadius(0.1f);
        fworksEmitter1.setFadeWithAge(true);
        fworksEmitter1.setParticleSize(10.0f);
        fworksEmitter1.setEmitRate(50);
        fworksEmitter1.setParticleAge(1.5f);
        fworksEmitter1.setBurstMode(true);
        fworksEmitter1.setEnvironmentAcceleration(new Vector3f(0,-2.0f,0));
        fworksEmitter1.setParticleTexture(fireworksTexture1);

        fworksEmitter2.setRadius(0.1f);
        fworksEmitter2.setFadeWithAge(true);
        fworksEmitter2.setParticleSize(10.0f);
        fworksEmitter2.setEmitRate(50);
        fworksEmitter2.setParticleAge(1.5f);
        fworksEmitter2.setBurstMode(true);
        fworksEmitter2.setEnvironmentAcceleration(new Vector3f(0,-2.0f,0));
        fworksEmitter2.setParticleTexture(fireworksTexture2);

        fworksEmitter3.setRadius(0.1f);
        fworksEmitter3.setFadeWithAge(true);
        fworksEmitter3.setParticleSize(12.0f);
        fworksEmitter3.setEmitRate(50);
        fworksEmitter3.setParticleAge(1.5f);
        fworksEmitter3.setBurstMode(true);
        fworksEmitter3.setEnvironmentAcceleration(new Vector3f(0,-2.0f,0));
        fworksEmitter3.setParticleTexture(fireworksTexture3);

        fworksEmitter4.setRadius(0.1f);
        fworksEmitter4.setFadeWithAge(true);
        fworksEmitter4.setParticleSize(11.0f);
        fworksEmitter4.setEmitRate(50);
        fworksEmitter4.setParticleAge(1.5f);
        fworksEmitter4.setBurstMode(true);
        fworksEmitter4.setEnvironmentAcceleration(new Vector3f(0,-2.0f,0));
        fworksEmitter4.setParticleTexture(fireworksTexture4);


        fworksEmitter1.setParticleVolume(100, 100, 100);
        fworksEmitter2.setParticleVolume(100, 100, 100);
        fworksEmitter3.setParticleVolume(100, 100, 100);
        fworksEmitter4.setParticleVolume(100, 100, 100);

        ArrayList<GVRSphericalEmitter> fwEmittersRet = new ArrayList<GVRSphericalEmitter>();

        fwEmittersRet.add(fworksEmitter1);
        fwEmittersRet.add(fworksEmitter2);
        fwEmittersRet.add(fworksEmitter3);
        fwEmittersRet.add(fworksEmitter4);

        fwEmitters.add(fworksEmitter1);
        fwEmitters.add(fworksEmitter2);
        fwEmitters.add(fworksEmitter3);
        fwEmitters.add(fworksEmitter4);

        return fwEmittersRet;
    }

    public void loadSky()
    {
        GVRAssetLoader loader = mGVRContext.getAssetLoader();
        Future<GVRTexture> sphtex = loader
                .loadFutureTexture(
                        new GVRAndroidResource(mGVRContext, R.raw.sf));
        GVRSphereSceneObject sph = new GVRSphereSceneObject(mGVRContext,false,sphtex);
        sph.getTransform().setScale(100,100,100);
        mGVRContext.getMainScene().addSceneObject(sph);
    }

    public void loadSnowHills()
    {
        GVRAssetLoader loader = mGVRContext.getAssetLoader();

        Future<GVRMesh> gmesh =
                loader.loadFutureMesh(new GVRAndroidResource(mGVRContext, R.raw.snowground));
        Future<GVRTexture> gtex = loader
                .loadFutureTexture(
                        new GVRAndroidResource(mGVRContext, R.raw.sground));

        GVRRenderData rdata = new GVRRenderData(mGVRContext);
        GVRMaterial mat = new GVRMaterial(mGVRContext);
        rdata.setMesh(gmesh);
        mat.setMainTexture(gtex);
        rdata.setMaterial(mat);
        GVRSceneObject snowground = new GVRSceneObject(mGVRContext);
        snowground.attachRenderData(rdata);
        snowground.getTransform().setPosition(0,-4,0);
        mGVRContext.getMainScene().addSceneObject(snowground);


        Future<GVRMesh> skymesh =
                loader.loadFutureMesh(new GVRAndroidResource(mGVRContext, R.raw.snowsky));
        Future<GVRTexture> sky = loader
                .loadFutureTexture(
                        new GVRAndroidResource(mGVRContext, R.raw.ssky));

        GVRRenderData rdata2 = new GVRRenderData(mGVRContext);
        GVRMaterial mat2 = new GVRMaterial(mGVRContext);
        rdata2.setMesh(skymesh);
        mat2.setMainTexture(sky);
        rdata2.setMaterial(mat2);
        GVRSceneObject snowsky = new GVRSceneObject(mGVRContext);
        snowsky.attachRenderData(rdata2);
        snowsky.getRenderData().setCullFace(GVRRenderPass.GVRCullFaceEnum.None);
        //mGVRContext.getMainScene().addSceneObject(snowsky);
    }

    public void loadCampModel() throws IOException {

        GVRSceneObject env = new GVRSceneObject(mGVRContext);

        GVRAssetLoader loader = mGVRContext.getAssetLoader();

        Future<GVRMesh> tentmesh =
                loader.loadFutureMesh(new GVRAndroidResource(mGVRContext, R.raw.tent));
        Future<GVRTexture> tentex = loader
                .loadFutureTexture(
                        new GVRAndroidResource(mGVRContext, R.raw.camp_diffuse));

        GVRRenderData rdata = new GVRRenderData(mGVRContext);
        GVRMaterial mat = new GVRMaterial(mGVRContext);
        rdata.setMesh(tentmesh);
        mat.setMainTexture(tentex);
        rdata.setMaterial(mat);
        GVRSceneObject tent = new GVRSceneObject(mGVRContext);
        tent.attachRenderData(rdata);
        //tent.getTransform().setPosition(-3,-3.5f,-9);
        //mGVRContext.getMainScene().addSceneObject(tent);
        env.addChildObject(tent);

        Future<GVRMesh> rockmesh =
                loader.loadFutureMesh(new GVRAndroidResource(mGVRContext, R.raw.rocks));
        Future<GVRTexture> rocktex = loader
                .loadFutureTexture(
                        new GVRAndroidResource(mGVRContext, R.raw.rocks_diffuse));

        GVRRenderData rdata2 = new GVRRenderData(mGVRContext);
        GVRMaterial mat2 = new GVRMaterial(mGVRContext);
        rdata2.setMesh(rockmesh);
        mat2.setMainTexture(rocktex);
        rdata2.setMaterial(mat2);
        GVRSceneObject rocks = new GVRSceneObject(mGVRContext);
        rocks.attachRenderData(rdata2);
        //rocks.getTransform().setPosition(0,-3.5f,-9);
        //mGVRContext.getMainScene().addSceneObject(rocks);
        env.addChildObject(rocks);

        Future<GVRMesh> stickmesh =
                loader.loadFutureMesh(new GVRAndroidResource(mGVRContext, R.raw.sticks));
        Future<GVRTexture> sticktex = loader
                .loadFutureTexture(
                        new GVRAndroidResource(mGVRContext, R.raw.woodsground_diffuse));

        GVRRenderData rdata3 = new GVRRenderData(mGVRContext);
        GVRMaterial mat3 = new GVRMaterial(mGVRContext);
        rdata3.setMesh(stickmesh);
        mat3.setMainTexture(sticktex);
        rdata3.setMaterial(mat3);
        GVRSceneObject sticks = new GVRSceneObject(mGVRContext);
        sticks.attachRenderData(rdata3);
        //sticks.getTransform().setPosition(0,-3.5f,-9);
        //mGVRContext.getMainScene().addSceneObject(sticks);
        env.addChildObject(sticks);

        Future<GVRMesh> potmesh =
                loader.loadFutureMesh(new GVRAndroidResource(mGVRContext, R.raw.pot));
        Future<GVRTexture> pottex = loader
                .loadFutureTexture(
                        new GVRAndroidResource(mGVRContext, R.raw.utensilrope_diffuse));

        GVRRenderData rdata4 = new GVRRenderData(mGVRContext);
        GVRMaterial mat4 = new GVRMaterial(mGVRContext);
        rdata4.setMesh(potmesh);
        mat4.setMainTexture(pottex);
        rdata4.setMaterial(mat4);
        GVRSceneObject pot = new GVRSceneObject(mGVRContext);
        pot.attachRenderData(rdata4);
        //pot.getTransform().setPosition(0,-3.5f,-9);
        //mGVRContext.getMainScene().addSceneObject(pot);
        //env.addChildObject(pot);

        env.getTransform().setPosition(0,-5.0f,-11);
        env.getTransform().setScale(0.4f, 0.4f, 0.4f);
        mGVRContext.getMainScene().addSceneObject(env);


        GVRSceneObject ground = new GVRSceneObject(mGVRContext, 200, 200, loader.loadTexture(
                (new GVRAndroidResource(mGVRContext, R.raw.grass))));

        ground.getTransform().setPosition(0,-5.0f,0);
        ground.getTransform().setRotationByAxis(-90, 1,0,0);
        mGVRContext.getMainScene().addSceneObject(ground);


//        Future<GVRTexture> sphtex = loader
//                .loadFutureTexture(
//                        new GVRAndroidResource(mGVRContext, R.raw.night2));
//        GVRSphereSceneObject sph = new GVRSphereSceneObject(mGVRContext,false,sphtex);
//        sph.getTransform().setScale(100,100,100);
//        mGVRContext.getMainScene().addSceneObject(sph);

//        GVRSceneObject sphereObj = loader.loadModel("sphere_sun.obj", mGVRContext.getMainScene());
//        sphereObj.getTransform().setScale(100.0f, 100.0f, 100.0f);
//        mGVRContext.getMainScene().addSceneObject(sphereObj);
    }
}
