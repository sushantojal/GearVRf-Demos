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
import org.joml.Vector3f;
import org.joml.Vector4f;

import static android.opengl.GLES20.GL_ONE;
import static android.opengl.GLES20.GL_ONE_MINUS_SRC_ALPHA;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_SRC_ALPHA;


public class Particles extends GVRMesh {

    private  GVRContext mGVRContext;
    private GVRMaterial material;
    private GVRMesh mParticleMesh;
    private float mAge;
    private float mSize;
    private Vector3f mAcceleration;
    private float mParticleSizeRate;
    private float mFadeWithAge;
    private GVRTexture mTexture;
    private Vector4f mColorMultiplier;

    public Particles(GVRContext gvrContext, float age, float particleSize,
                     Vector3f acceleration, float particleSizeRate, boolean fadeWithAge,
                     GVRTexture tex, Vector4f color) {
        super(gvrContext);
        mGVRContext = gvrContext;
        mAge = age;
        mSize = particleSize;
        mAcceleration = acceleration;
        mParticleSizeRate = particleSizeRate;
        mColorMultiplier = color;
        if (fadeWithAge)
            mFadeWithAge = 1.0f;
        else
            mFadeWithAge = 0.0f;
        mTexture = tex;
    }

    public GVRSceneObject makeParticleMesh(float[] vertices, float[] velocities,
                                           float[] particleTimeStamps )
    {
        mParticleMesh = new GVRMesh(mGVRContext);
        mParticleMesh.setVertices(vertices);
        mParticleMesh.setNormals(velocities);
        mParticleMesh.setTexCoords(particleTimeStamps);

        material = new GVRMaterial(mGVRContext,
                GVRMaterial.GVRShaderType.BeingGenerated.ID);
        material.setVec4("u_color", mColorMultiplier.x, mColorMultiplier.y,
                mColorMultiplier.z, mColorMultiplier.w);
        material.setFloat("u_particle_age", mAge);
        material.setVec3("u_acceleration", mAcceleration.x, mAcceleration.y, mAcceleration.z);
        material.setFloat("u_particle_size", mSize);
        material.setFloat("u_size_change_rate", mParticleSizeRate);
        material.setFloat("u_fade", mFadeWithAge);

        GVRRenderData renderData = new GVRRenderData(mGVRContext);
        renderData.setMaterial(material);
        renderData.setMesh(mParticleMesh);
        material.setMainTexture(mTexture);

        GVRSceneObject meshObject = new GVRSceneObject(mGVRContext);
        meshObject.attachRenderData(renderData);
        meshObject.getRenderData().setMaterial(material);

        GVRMaterialShaderManager shaderManager = mGVRContext.getMaterialShaderManager();
        GVRShaderTemplate shaderTemplate = shaderManager.retrieveShaderTemplate(ColorShader.class);
        shaderTemplate.bindShader(mGVRContext, meshObject.getRenderData(), getGVRContext().getMainScene());

        meshObject.getRenderData().setDrawMode(GL_POINTS);
        meshObject.getRenderData().setDepthTest(true);
        meshObject.getRenderData().setDepthMask(false);
        meshObject.getRenderData().setRenderingOrder(GVRRenderData.GVRRenderingOrder.TRANSPARENT);

        return meshObject;
    }
}
