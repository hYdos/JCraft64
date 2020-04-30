package com.github.hydos.ginger.engine.common.elements.objects;

import org.joml.Vector3f;

import com.github.hydos.ginger.engine.common.Constants;
import com.github.hydos.ginger.engine.common.api.GingerRegister;
import com.github.hydos.ginger.engine.common.io.Window;
import com.github.hydos.ginger.engine.opengl.render.models.GLTexturedModel;

public class Player extends GLRenderObject
{
	private boolean isInAir = false;
	private double upwardsSpeed;
	private boolean noWeight = true; // because the force of gravity on an object's mass is called WEIGHT, not gravity

	public Player(GLTexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, Vector3f scale)
	{
		super(model, position, rotX, rotY, rotZ, scale);
	}

	private static final float RIGHT_ANGLE = (float) (Math.PI / 2f);

	private void jump()
	{
		if (!isInAir)
		{
			isInAir = true;
			this.upwardsSpeed = Constants.jumpPower;
		}
	}
	public void updateMovement()
	{
		super.increasePosition(0, (float) (upwardsSpeed * (Window.getTime())), 0);
		upwardsSpeed += Constants.gravity.y() * Window.getTime(); // TODO: Implement 3D gravity
		isInAir = false;
		upwardsSpeed = 0;
	}
}
