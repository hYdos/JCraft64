package com.github.hydos.ginger.engine.opengl.render.renderers;

import com.github.hydos.ginger.engine.common.api.GingerRegister;
import com.github.hydos.ginger.engine.common.io.Window;
import com.github.hydos.ginger.engine.common.math.Maths;
import com.github.hydos.ginger.engine.common.render.Renderer;
import com.github.hydos.ginger.engine.opengl.render.GLRenderManager;
import com.github.hydos.ginger.engine.opengl.render.models.RawModel;
import com.github.hydos.ginger.engine.opengl.render.shaders.GLStaticShader;
import gln64j.GLN64jPlugin;
import gln64j.OpenGlGdp;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class GLGLVertexRenderer extends Renderer {

    private final GLStaticShader shader;

    public GLGLVertexRenderer(GLStaticShader shader, Matrix4f projectionMatrix) {
        this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public void prepare() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    private void prepareInstance() {
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(new Vector3f(0, 0, 0), 0, 0, 0, new Vector3f(1, 1, 1));
        shader.loadTransformationMatrix(transformationMatrix);
    }

    private RawModel prepareVerts(OpenGlGdp.GLVertex[] vertices) {
        RawModel rawModel = RawModel.createFromEmu(vertices);
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GLRenderManager.enableCulling();
        shader.loadFakeLightingVariable(true);
        shader.loadShine(1, 1);
        return rawModel;
    }

    private void unbind() {
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }

    public void render(OpenGlGdp.GLVertex[] vertices) {
        prepare();
        shader.start();
        shader.loadSkyColour(Window.getColour());
        shader.loadViewMatrix(GLN64jPlugin.camera);
        prepareInstance();
        RawModel model = prepareVerts(vertices);

        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertexCount());
        unbind();
        shader.stop();
    }

}
