package com.github.hydos.ginger.engine.opengl.render.shaders;

public class GLObjectShader extends ShaderProgram {

    public GLObjectShader() {
        super("entityVertexShader.glsl", "entityFragmentShader.glsl");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
        super.bindAttribute(2, "colour");
    }

    @Override
    protected void getAllUniformLocations() {
    }
}
