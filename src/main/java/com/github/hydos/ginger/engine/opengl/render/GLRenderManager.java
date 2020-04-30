package com.github.hydos.ginger.engine.opengl.render;

import com.github.hydos.ginger.engine.common.api.GingerRegister;
import com.github.hydos.ginger.engine.common.cameras.Camera;
import com.github.hydos.ginger.engine.common.elements.GLGuiTexture;
import com.github.hydos.ginger.engine.common.elements.objects.GLRenderObject;
import com.github.hydos.ginger.engine.common.elements.objects.Light;
import com.github.hydos.ginger.engine.common.io.Window;
import com.github.hydos.ginger.engine.opengl.render.models.GLTexturedModel;
import com.github.hydos.ginger.engine.opengl.render.renderers.GLGuiRenderer;
import com.github.hydos.ginger.engine.opengl.render.renderers.GLNormalMappingRenderer;
import com.github.hydos.ginger.engine.opengl.render.renderers.GLObjectRenderer;
import com.github.hydos.ginger.engine.opengl.render.shaders.GLStaticShader;
import com.github.hydos.ginger.engine.opengl.render.shaders.GuiShader;
import com.github.hydos.ginger.engine.opengl.shadow.ShadowMapMasterRenderer;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GLRenderManager {
    public static final float FOV = 80f;
    public static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000f;
    public GLObjectRenderer entityRenderer;
    private final GLStaticShader entityShader;
    private final GuiShader guiShader;
    private final GLGuiRenderer guiRenderer;
    //	private SkyboxRenderer skyboxRenderer;
    private final GLNormalMappingRenderer normalRenderer;
    private Matrix4f projectionMatrix;
    private final ShadowMapMasterRenderer shadowMapRenderer;
    private final Map<GLTexturedModel, List<GLRenderObject>> entities = new HashMap<GLTexturedModel, List<GLRenderObject>>();
    private final Map<GLTexturedModel, List<GLRenderObject>> normalMapEntities = new HashMap<GLTexturedModel, List<GLRenderObject>>();
    public GLRenderManager(Camera camera) {
        createProjectionMatrix();
        entityShader = new GLStaticShader();
        entityRenderer = new GLObjectRenderer(entityShader, projectionMatrix);
        guiShader = new GuiShader();
        guiRenderer = new GLGuiRenderer(guiShader);
        normalRenderer = new GLNormalMappingRenderer(projectionMatrix);
        shadowMapRenderer = new ShadowMapMasterRenderer(camera);
    }

    public static void disableCulling() {
        GL11.glDisable(GL11.GL_CULL_FACE);
    }

    public static void enableCulling() {
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
    }

    public static Matrix4f createProjectionMatrix() {
        Matrix4f projectionMatrix = new Matrix4f();
        float aspectRatio = (float) Window.getWidth() / (float) Window.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))));
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;
        projectionMatrix._m00(x_scale);
        projectionMatrix._m11(y_scale);
        projectionMatrix._m22(-((FAR_PLANE + NEAR_PLANE) / frustum_length));
        projectionMatrix._m23(-1);
        projectionMatrix._m32(-((2 * NEAR_PLANE * FAR_PLANE) / frustum_length));
        projectionMatrix._m33(0);
        return projectionMatrix;
    }

    public void cleanUp() {
        entityShader.cleanUp();
        guiRenderer.cleanUp();
        shadowMapRenderer.cleanUp();
        normalRenderer.cleanUp();
    }

    public Matrix4f getProjectionMatrix() {
        return this.projectionMatrix;
    }

    public int getShadowMapTexture() {
        return shadowMapRenderer.getShadowMap();
    }

    public void prepare() {
        GL13.glActiveTexture(GL13.GL_TEXTURE5);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, shadowMapRenderer.getShadowMap());
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
    }

    private void processEntity(GLRenderObject entity) {
        yes(entity, entities);
    }

    private void processEntityWithNormal(GLRenderObject entity) {
        yes(entity, normalMapEntities);
    }

    private void yes(GLRenderObject entity, Map<GLTexturedModel, List<GLRenderObject>> normalMapEntities) {
        GLTexturedModel entityModel = (GLTexturedModel) entity.getModel();
        List<GLRenderObject> batch = normalMapEntities.get(entityModel);
        if (batch != null) {
            batch.add(entity);
        } else {
            List<GLRenderObject> newBatch = new ArrayList<>();
            newBatch.add(entity);
            normalMapEntities.put(entityModel, newBatch);
        }
    }

    private void renderEntities(List<GLRenderObject> entities, Camera camera, List<Light> lights) {
        for (GLRenderObject entity : entities) {
            processEntity(entity);
        }
        entityRenderer.prepare();
        entityShader.start();
        entityShader.loadSkyColour(Window.getColour());
        entityShader.loadLights(lights);
        entityShader.loadViewMatrix(camera);
        entityRenderer.render(this.entities);
        entityShader.stop();
        this.entities.clear();
    }

    public void renderGui(GLGuiTexture guiTexture) {
        List<GLGuiTexture> texture = new ArrayList<GLGuiTexture>();
        texture.add(guiTexture);
        guiRenderer.render(texture);
    }

    public void renderGuis(List<GLGuiTexture> guis) {
        guiRenderer.render(guis);
    }

    private void renderNormalEntities(List<GLRenderObject> normalEntities, List<Light> lights, Camera camera, Vector4f clipPlane) {
        for (GLRenderObject entity : normalEntities) {
            processEntityWithNormal(entity);
        }
        normalRenderer.render(normalMapEntities, clipPlane, lights, camera);
    }

    public void renderScene(List<GLRenderObject> entities, List<GLRenderObject> normalEntities, List<Light> lights, Camera camera, Vector4f clipPlane) {
        prepare();
        renderEntities(entities, camera, lights);
        renderNormalEntities(normalEntities, lights, camera, clipPlane);
        GingerRegister.getInstance().game.renderScene();
//		skyboxRenderer.render(camera);
    }

    public void renderShadowMap(List<GLRenderObject> entityList, Light sun) {
        for (GLRenderObject entity : entityList) {
            processEntity(entity);
        }
        shadowMapRenderer.render(entities, sun);
        entities.clear();
    }
}
