package com.github.hydos.ginger.engine.opengl.render.shaders;

import com.github.hydos.ginger.engine.common.cameras.Camera;
import com.github.hydos.ginger.engine.common.elements.objects.Light;
import com.github.hydos.ginger.engine.common.math.Maths;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.List;

public class GLObjectShader extends ShaderProgram {

    public GLObjectShader() {
        super("entityVertexShader.glsl", "entityFragmentShader.glsl");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoords");
    }

    @Override
    protected void getAllUniformLocations() {
    }
}
