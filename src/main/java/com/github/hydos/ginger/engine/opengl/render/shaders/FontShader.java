package com.github.hydos.ginger.engine.opengl.render.shaders;

import org.joml.*;

import com.github.hydos.ginger.engine.common.font.GUIText;

public class FontShader extends ShaderProgram
{
	private static final String VERTEX_FILE = "fontVertexShader.glsl";
	private static final String FRAGMENT_FILE = "fontFragmentShader.glsl";
	private int location_colour;
	private int location_translation;
	private int location_borderWidth;
	private int location_borderEdge;
	private int location_offset;
	private int location_outlineColour;

	public FontShader()
	{ super(VERTEX_FILE, FRAGMENT_FILE); }

	@Override
	protected void bindAttributes()
	{
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}

	@Override
	protected void getAllUniformLocations()
	{
		location_colour = super.getUniformLocation("colour");
		location_translation = super.getUniformLocation("translation");
		location_borderWidth = super.getUniformLocation("borderWidth");
		location_borderEdge = super.getUniformLocation("borderEdge");
		location_offset = super.getUniformLocation("offset");
		location_outlineColour = super.getUniformLocation("outlineColour");
	}

	public void loadColour(Vector3f colour)
	{ super.loadVector(location_colour, colour); }

	public void loadText(GUIText text)
	{
		super.load2DVector(location_offset, text.getOffset());
		super.loadFloat(location_borderEdge, text.getBorderEdge());
		super.loadFloat(location_borderWidth, text.getBorderWidth());
		super.loadVector(location_outlineColour, text.getOutlineColour());
	}

	public void loadTranslation(Vector2f translation)
	{ super.load2DVector(location_translation, translation); }
}
