package gln64j.gingerExt;

import com.github.hydos.ginger.engine.opengl.render.texture.Image;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;

public class N64Texture
{
	private int textureID = GL11.glGenTextures();
	private boolean transparency = false;
	private Image texture;

	public N64Texture(Image texture)
	{
		this.texture = texture;
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.textureID);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, 10241, 9729.0f);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, 10240, 9729.0f);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, 6408, texture.getWidth(), texture.getHeight(), 0, 6408, 5121, texture.getImage());
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}

	public Image getTexture()
	{ return texture; }

	public int getTextureID()
	{ return this.textureID; }

	public boolean isTransparent()
	{ return transparency; }

	public void remove()
	{ GL11.glDeleteTextures(this.textureID); }

	public void setTransparency(boolean b)
	{ this.transparency = b; }
}
