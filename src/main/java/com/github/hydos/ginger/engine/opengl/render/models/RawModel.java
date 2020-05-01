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
		return GLLoader.loadToVAO(getEmuVertsTriangle(vertices) ,4); //perspective is currently done in cpu so its 4
    }

	private static float[] getEmuVertsTriangle(OpenGlGdp.GLVertex[] vertices) {
		float[] verts = new float[vertices.length * 3];
		int i = 0;
		for(OpenGlGdp.GLVertex vertexData : vertices){
			verts[i] = vertexData.vtx.get(0);
			i++;
			verts[i] = vertexData.vtx.get(1);
			i++;
			verts[i] = vertexData.vtx.get(2);
			i++;
		}
		return verts;
	}

    public static RawModel quad(float[] verts) {
		return GLLoader.loadToVAO(verts, 4);
    }

    public int getVaoID()
	{ return vaoID; }

	public int getVertexCount()
	{ return vertexCount; }
}
