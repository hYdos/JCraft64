package com.github.hydos.ginger.engine.common.font;

import java.util.*;

import com.github.hydos.ginger.engine.common.info.RenderAPI;
import com.github.hydos.ginger.engine.common.io.Window;
import com.github.hydos.ginger.engine.opengl.api.GingerGL;
import com.github.hydos.ginger.engine.opengl.utils.GLLoader;

public class TextMaster
{
	private static Map<FontType, List<GUIText>> texts = new HashMap<FontType, List<GUIText>>();

	public static void cleanUp()
	{}

	public static void init()
	{}

	public static void loadText(GUIText text)
	{
		FontType font = text.getFont();
		TextMeshData data = font.loadText(text);
		int vao = GLLoader.loadToVAO(data.getVertexPositions(), data.getTextureCoords());
		text.setMeshInfo(vao, data.getVertexCount());
		List<GUIText> textBatch = texts.computeIfAbsent(font, k -> new ArrayList<GUIText>());
		textBatch.add(text);
	}

	public static void removeText(GUIText text)
	{
		List<GUIText> textBatch = texts.get(text.getFont());
		textBatch.remove(text);
		if (textBatch.isEmpty())
		{ texts.remove(text.getFont()); }
	}

	public static void render()
	{}

	public static void render(GUIText buildText)
	{
		// TODO: Add Vulkan font renderer
		if (Window.renderAPI == RenderAPI.OpenGL)
		{
			Map<FontType, List<GUIText>> oldTexts = texts;
			List<GUIText> oldFontText = texts.get(((GingerGL)GingerGL.getInstance()).globalFont);
			oldFontText.add(buildText);
			texts.clear();
			texts.put(((GingerGL)GingerGL.getInstance()).globalFont, oldFontText);
			texts = oldTexts;
		}
	}
}
