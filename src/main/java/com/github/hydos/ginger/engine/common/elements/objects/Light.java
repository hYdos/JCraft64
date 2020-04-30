package com.github.hydos.ginger.engine.common.elements.objects;

import org.joml.Vector3f;

public class Light
{
	private Vector3f position, colour, attenuation;

	public Light(Vector3f position, Vector3f colour)
	{
		this.position = position;
		this.colour = colour;
	}

	public Light(Vector3f position, Vector3f colour, Vector3f attenuation)
	{
		this.position = position;
		this.colour = colour;
		this.attenuation = attenuation;
	}

	public Vector3f getAttenuation()
	{ return attenuation; }

	public Vector3f getColour()
	{ return colour; }

	public Vector3f getPosition()
	{ return position; }

	public void setAttenuation(Vector3f a)
	{ this.attenuation = a; }

	public void setColour(Vector3f colour)
	{ this.colour = colour; }

	public void setPosition(Vector3f position)
	{ this.position = position; }
}
