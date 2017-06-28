package org.gearvrf.particlesystem;

import org.gearvrf.GVRAndroidResource;
import org.gearvrf.GVRContext;
import org.gearvrf.GVRMaterial;
import org.gearvrf.GVRMaterialShaderManager;
import org.gearvrf.GVRMesh;
import org.gearvrf.GVRRenderData;
import org.gearvrf.GVRSceneObject;
import org.gearvrf.GVRShaderTemplate;
import org.gearvrf.GVRTexture;

import static android.opengl.GLES20.GL_ONE;
import static android.opengl.GLES20.GL_ONE_MINUS_SRC_ALPHA;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_SRC_ALPHA;

/**
 * Created by sushant.o on 6/23/2017.
 */

public class Particles extends GVRMesh {

    private  GVRContext mGVRContext;
    private GVRMaterial material;
    private GVRMesh mParticleMesh;

    public Particles(GVRContext gvrContext) {
        super(gvrContext);

        mGVRContext = gvrContext;
    }

    public GVRSceneObject makeParticleMesh(float[] vertices, float[] velocities, float[] particleTimeStamps)
    {
        mParticleMesh = new GVRMesh(mGVRContext);
        mParticleMesh.setVertices(vertices);
        mParticleMesh.setNormals(velocities);
        mParticleMesh.setTexCoords(particleTimeStamps);

        GVRTexture texture = mGVRContext.getAssetLoader().loadTexture(
                new GVRAndroidResource(mGVRContext, R.drawable.fireparticle));

        material = new GVRMaterial(mGVRContext,
                GVRMaterial.GVRShaderType.BeingGenerated.ID);
        material.setVec4("u_color", 1.0f,
                1.0f, 1.0f, 0.5f);
        material.setFloat("u_opacity", 1.0f);
        material.setFloat("u_velocity", 0.5f);
        GVRRenderData renderData = new GVRRenderData(mGVRContext);
        renderData.setMaterial(material);
        renderData.setMesh(mParticleMesh);
        material.setMainTexture(texture);
        GVRSceneObject meshObject = new GVRSceneObject(mGVRContext);
        meshObject.attachRenderData(renderData);
        //meshObject.getTransform().setScale(scale, scale, scale);
        meshObject.getRenderData().setMaterial(material);

        GVRMaterialShaderManager shaderManager = mGVRContext.getMaterialShaderManager();
        GVRShaderTemplate shaderTemplate = shaderManager.retrieveShaderTemplate(ColorShader.class);
        shaderTemplate.bindShader(mGVRContext, meshObject.getRenderData(), getGVRContext().getMainScene());

        meshObject.getRenderData().setDrawMode(GL_POINTS);
        meshObject.getRenderData().setDepthTest(true);
        meshObject.getRenderData().setDepthMask(false);
        meshObject.getTransform().setPosition(0,0,0);
        return meshObject;
    }
}
