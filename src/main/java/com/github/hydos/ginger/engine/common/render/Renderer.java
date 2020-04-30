package com.github.hydos.ginger.engine.common.render;

import org.lwjgl.system.MemoryStack;

public abstract class Renderer
{
	public int priority = 2;//default is 2. 1 is the highest and 10 is the lowest
	
	public void VKBindShaders(MemoryStack stack)
	{}
	
	public void VKUnbindShaders(MemoryStack stack)
	{}
}