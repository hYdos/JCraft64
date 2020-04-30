package com.github.hydos.ginger.engine.opengl.render.renderers;

import java.util.*;

import org.lwjgl.opengl.*;

import com.github.hydos.ginger.engine.common.font.*;
import com.github.hydos.ginger.engine.common.render.Renderer;
import com.github.hydos.ginger.engine.opengl.render.shaders.FontShader;

public class GLFontRenderer extends Renderer
{
	private FontShader shader;

	public GLFontRenderer()
	{ shader = new FontShader(); }

	public void cleanUp()
	{ shader.cleanUp(); }

	private void endRendering()
	{
		shader.stop();
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_BLEND);
	}

	private void prepare()
	{
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		shader.start();
	}

	public void render(Map<FontType, List<GUIText>> texts)
	{
		prepare();
		for (FontType font : texts.keySet())
		{
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, font.getTextureAtlas());
			for (GUIText text : texts.get(font))
			{ renderText(text); }
		}
		endRendering();
	}

	private void renderText(GUIText text)
	{
		GL30.glBindVertexArray(text.getMesh());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		shader.loadColour(text.getColour());
		shader.loadTranslation(text.getPosition());
		shader.loadText(text);
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, text.getVertexCount());
		GL30.glBindVertexArray(0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
	}
}
