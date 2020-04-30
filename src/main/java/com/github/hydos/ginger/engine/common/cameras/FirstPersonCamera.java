package com.github.hydos.ginger.engine.common.cameras;

import org.joml.Vector3f;

import com.github.hydos.ginger.engine.common.elements.objects.GLRenderObject;
import com.github.hydos.ginger.engine.common.io.Window;

public class FirstPersonCamera extends Camera
{
	private Vector3f position = new Vector3f(0, 0, 0);
	private float pitch, yaw;
	private float roll;

	public FirstPersonCamera()
	{
	}

	public float getPitch()
	{ return pitch; }

	public Vector3f getPosition()
	{ return position; }

	public float getRoll()
	{ return roll; }

	public float getYaw()
	{ return yaw; }

	public void updateMovement()
	{
	}
}
