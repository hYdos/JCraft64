package com.github.hydos.ginger.engine.common.api.game;

import com.github.hydos.ginger.engine.common.cameras.Camera;
import com.github.hydos.ginger.engine.common.elements.objects.GLRenderObject;

public abstract class Game
{
	public GameData data;
	public Camera camera;

	public Game()
	{}

	public abstract void exit();

	public abstract void render();

	public abstract void renderScene();

	public abstract void update();
}