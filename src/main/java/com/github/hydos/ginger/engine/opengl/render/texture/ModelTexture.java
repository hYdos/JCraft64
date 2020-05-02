package com.github.hydos.ginger.engine.opengl.render.texture;

import org.lwjgl.opengl.*;

public class ModelTexture
{
	private final int textureID = GL11.glGenTextures();
	private boolean transparency = false;
	private boolean useFakeLighting = false;
	private float shineDamper = 1;
	private float reflectivity = 0;
	private Image texture;

	public ModelTexture(String file)
	{
		// TODO: Add a missing texture placholder in case of null file.
		texture = Image.createImage(file);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textureID);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, 10241, 9729.0f);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, 10240, 9729.0f);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, texture.getWidth(), texture.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, texture.getImage());
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -0.4f);
	}

	public float getReflectivity()
	{ return reflectivity; }

	public float getShineDamper()
	{ return shineDamper; }

	public Image getTexture()
	{ return texture; }

	public int getTextureID()
	{ return this.textureID; }

	public boolean isTransparent()
	{ return transparency; }

	public boolean isUseFakeLighting()
	{ return useFakeLighting; }
}
