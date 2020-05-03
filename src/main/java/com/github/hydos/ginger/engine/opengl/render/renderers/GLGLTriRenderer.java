package com.github.hydos.ginger.engine.opengl.render.renderers;

import com.github.hydos.ginger.engine.common.io.Window;
import com.github.hydos.ginger.engine.common.math.Maths;
import com.github.hydos.ginger.engine.common.render.Renderer;
import com.github.hydos.ginger.engine.opengl.render.GLRenderManager;
import com.github.hydos.ginger.engine.opengl.render.models.RawModel;
import com.github.hydos.ginger.engine.opengl.render.shaders.GLObjectShader;
import com.github.hydos.ginger.engine.opengl.render.texture.ModelTexture;
import com.github.hydos.ginger.engine.opengl.utils.GLLoader;
import gln64j.GLN64jPlugin;
import gln64j.OpenGlGdp;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class GLGLTriRenderer extends Renderer {

    private final GLObjectShader shader;

    public GLGLTriRenderer(GLObjectShader shader) {
        this.shader = shader;
    }

    public void prepare() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    private void prepareInstance() {
    }

    private RawModel prepareVerts(OpenGlGdp.GLVertex[] vertices) {
        RawModel rawModel = RawModel.fromVerts(vertices);
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        return rawModel;
    }

    private void unbind() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    public void render(OpenGlGdp.GLVertex[] vertices) {
        prepare();
        shader.start();
        prepareInstance();
        RawModel model = prepareVerts(vertices);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertexCount());
        unbind();
        shader.stop();
    }

}
