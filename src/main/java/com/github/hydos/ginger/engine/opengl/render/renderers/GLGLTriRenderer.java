package com.github.hydos.ginger.engine.opengl.render.renderers;

import com.github.hydos.ginger.engine.common.io.Window;
import com.github.hydos.ginger.engine.common.math.Maths;
import com.github.hydos.ginger.engine.common.render.Renderer;
import com.github.hydos.ginger.engine.opengl.render.models.RawModel;
import com.github.hydos.ginger.engine.opengl.render.shaders.GLObjectShader;
import com.github.hydos.ginger.engine.opengl.render.texture.Image;
import gln64j.GLN64jPlugin;
import gln64j.OpenGlGdp;
import gln64j.gingerExt.N64Texture;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class GLGLTriRenderer extends Renderer {

    private final GLObjectShader shader;

    public N64Texture notexture;

    public GLGLTriRenderer(GLObjectShader shader, Matrix4f projectionMatrix) {
        this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
        notexture = new N64Texture(Image.createImage("/notexture.png"));
    }

    public void prepare() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    private void prepareInstance() {
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(new Vector3f(0, 0, 0), 0, 0, 0, new Vector3f(1, 1, 1));
        shader.loadTransformationMatrix(transformationMatrix);
    }

    private RawModel prepareVerts(OpenGlGdp.GLVertex[] vertices) {
        RawModel rawModel = RawModel.fromVerts(vertices);
        GL30.glBindVertexArray(rawModel.getVaoID());

        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, notexture.getTextureID());
        shader.loadFakeLightingVariable(true);
        shader.loadShine(1, 1);
        shader.loadColour(new Vector3f(0.6f, 0.4f, 0.4f));
        return rawModel;
    }

    private void unbind() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }

    public void render(OpenGlGdp.GLVertex[] vertices) {
        prepare();
        shader.start();
        shader.loadSkyColour(Window.getColour());
        shader.loadViewMatrix(GLN64jPlugin.camera);
        prepareInstance();
        RawModel model = prepareVerts(vertices);
        OpenGlGdp.cache.activateDummy(0);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertexCount());
        unbind();
        shader.stop();
    }

}
