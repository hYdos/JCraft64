package com.github.hydos.ginger.engine.opengl.render;

import com.github.hydos.ginger.engine.common.cameras.Camera;
import com.github.hydos.ginger.engine.common.elements.objects.GLRenderObject;
import com.github.hydos.ginger.engine.common.io.Window;
import com.github.hydos.ginger.engine.opengl.render.models.GLTexturedModel;
import com.github.hydos.ginger.engine.opengl.render.renderers.GLObjectRenderer;
import com.github.hydos.ginger.engine.opengl.render.shaders.GLObjectShader;
import com.github.hydos.ginger.engine.opengl.shadow.ShadowMapMasterRenderer;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GLRenderManager {
    public static final float FOV = 80f;
    public static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000f;
    public GLObjectRenderer entityRenderer;
    private final GLObjectShader entityShader;
    //	private SkyboxRenderer skyboxRenderer;
    private Matrix4f projectionMatrix;
    private final ShadowMapMasterRenderer shadowMapRenderer;
    private final Map<GLTexturedModel, List<GLRenderObject>> entities = new HashMap<GLTexturedModel, List<GLRenderObject>>();
    private final Map<GLTexturedModel, List<GLRenderObject>> normalMapEntities = new HashMap<GLTexturedModel, List<GLRenderObject>>();
    public GLRenderManager(Camera camera) {
        createProjectionMatrix();
        entityShader = new GLObjectShader();
//        entityRenderer = new GLObjectRenderer(entityShader, projectionMatrix);
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
    }
}
