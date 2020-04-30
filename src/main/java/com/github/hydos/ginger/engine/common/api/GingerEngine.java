package com.github.hydos.ginger.engine.common.api;

import com.github.hydos.ginger.engine.common.io.Window;
import com.github.hydos.ginger.engine.common.screen.Screen;
import com.github.hydos.ginger.engine.common.util.Timer;
import com.github.hydos.ginger.engine.common.util.Timer.TickListener;
import com.github.hydos.ginger.engine.opengl.utils.GLUtils;

public abstract class GingerEngine
{
	protected static GingerEngine INSTANCE;
	protected GingerRegister registry;
	
	public static GingerEngine getInstance()
	{
		return INSTANCE;
	}
	
	public void startGameLoop()
	{
		while (!Window.closed())
		{
			update(); // Run this regardless, (so as fast as possible)
			if (Window.shouldRender()) GingerRegister.getInstance().game.render(); // Run this only [framelimit] times per second
		}
		GingerRegister.getInstance().game.exit();
	}

	// Things that should be run as often as possible, without limits
	public void update()
	{
		GLUtils.update();
		Window.update();
	}

	public abstract void cleanup();
}
