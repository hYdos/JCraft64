package com.github.hydos.ginger.engine.opengl.render.models;

import com.github.hydos.ginger.engine.opengl.utils.GLLoader;
import gln64j.OpenGlGdp;

public class RawModel
{
	private int vaoID;
	private int vertexCount;

	public RawModel(int vaoID, int vertexCount)
	{
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}

    public static RawModel fromVerts(OpenGlGdp.GLVertex[] vertices) {
		float[][] data = processEmuVerts(vertices);
		return GLLoader.loadEmuVertsToVAO(data[0], data[1], data[2]);
    }

	private static float[][] processEmuVerts(OpenGlGdp.GLVertex[] vertices) {
		float[][] meshData = new float[3][];
		// 0 = position
		// 1 = tex coords
		// 2 = colour
		// 3 = tex coords 2?

		float[] verts = new float[vertices.length * 4];
		int i = 0;
		for(OpenGlGdp.GLVertex vertexData : vertices){
			verts[i] = vertexData.vtx.get(0);
			i++;
			verts[i] = vertexData.vtx.get(1);
			i++;
			verts[i] = vertexData.vtx.get(2);
			i++;
			verts[i] = vertexData.vtx.get(3);
			i++;
		}

		float[] texCoords = new float[vertices.length * 2];
		i = 0;
		for(OpenGlGdp.GLVertex vertexData : vertices){
			texCoords[i] = vertexData.tex0.get(0);
			i++;
			texCoords[i] = vertexData.tex0.get(1);
			i++;
		}

		float[] colours = new float[vertices.length * 4];
		i = 0;
		for(OpenGlGdp.GLVertex vertexData : vertices){
			colours[i] = vertexData.color.get(0);
			i++;
			colours[i] = vertexData.color.get(1);
			i++;
			colours[i] = vertexData.color.get(2);
			i++;
			colours[i] = vertexData.color.get(3);
			i++;
		}

		meshData[0] = verts;
		meshData[1] = texCoords;
		meshData[2] = colours;
		return meshData;
	}

    public static RawModel quad(float[] verts) {
		return GLLoader.loadToVAO(verts, 4);
    }

    public int getVaoID()
	{ return vaoID; }

	public int getVertexCount()
	{ return vertexCount; }
}
