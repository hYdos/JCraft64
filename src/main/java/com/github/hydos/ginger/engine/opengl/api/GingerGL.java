package com.github.hydos.ginger.engine.opengl.api;

import org.joml.Vector2f;

import com.github.hydos.ginger.engine.common.api.*;
import com.github.hydos.ginger.engine.common.api.game.Game;
import com.github.hydos.ginger.engine.common.elements.buttons.TextureButton;
import com.github.hydos.ginger.engine.common.elements.objects.GLRenderObject;
import com.github.hydos.ginger.engine.common.font.*;
import com.github.hydos.ginger.engine.common.io.Window;
import com.github.hydos.ginger.engine.common.screen.Screen;
import com.github.hydos.ginger.engine.common.tools.MousePicker;
import com.github.hydos.ginger.engine.common.util.Timer;
import com.github.hydos.ginger.engine.opengl.postprocessing.*;
import com.github.hydos.ginger.engine.opengl.render.GLRenderManager;
import com.github.hydos.ginger.engine.opengl.utils.GLLoader;

public class GingerGL extends GingerEngine
{
	public FontType globalFont;
	public FrameBufferObject contrastFbo;

	public void cleanup()
	{
		Window.stop();
		PostProcessing.cleanUp();
		GLLoader.cleanUp();
	}

	public void setup(Game game)
	{
		INSTANCE = this;
		registry = new GingerRegister();
		getRegistry().registerGame(game);
		contrastFbo = new FrameBufferObject(new ContrastChanger());
		PostProcessing.init();
	}
	
	@Override
	public void update()
	{
		getRegistry().game.update();
		super.update();
	}

	public GingerRegister getRegistry()
	{
		return registry;
	}
}