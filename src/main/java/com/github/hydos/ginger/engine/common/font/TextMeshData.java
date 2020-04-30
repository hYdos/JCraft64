package com.github.hydos.ginger.engine.common.font;

/** Stores the vertex data for all the quads on which a text will be rendered. */
public class TextMeshData
{
	private float[] vertexPositions;
	private float[] textureCoords;

	protected TextMeshData(float[] vertexPositions, float[] textureCoords)
	{
		this.vertexPositions = vertexPositions;
		this.textureCoords = textureCoords;
	}

	public float[] getTextureCoords()
	{ return textureCoords; }

	public int getVertexCount()
	{ return vertexPositions.length / 2; }

	public float[] getVertexPositions()
	{ return vertexPositions; }
}
