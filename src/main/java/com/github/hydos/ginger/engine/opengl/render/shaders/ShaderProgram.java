package com.github.hydos.ginger.engine.opengl.render.shaders;

import java.io.*;

import org.joml.*;
import org.lwjgl.opengl.*;

public abstract class ShaderProgram
{
	private static int loadShader(String file, int type)
	{
		StringBuilder shaderSource = new StringBuilder();
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(ShaderProgram.class.getResourceAsStream("/shaders/" + file)));
			String line;
			while ((line = reader.readLine()) != null)
			{ shaderSource.append(line).append("\n"); }
			reader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
		int shaderID = GL20.glCreateShader(type);
		GL20.glShaderSource(shaderID, shaderSource);
		GL20.glCompileShader(shaderID);
		if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
		{
			System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
			System.err.println("Could not compile shader: " + file);
			System.exit(-1);
		}
		return shaderID;
	}

	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;

	public ShaderProgram(String vertexFile, String fragmentFile)
	{
		vertexShaderID = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
		programID = GL20.glCreateProgram();
		GL20.glAttachShader(programID, vertexShaderID);
		GL20.glAttachShader(programID, fragmentShaderID);
		bindAttributes();
		GL20.glLinkProgram(programID);
		GL20.glValidateProgram(programID);
		getAllUniformLocations();
	}

	protected void bindAttribute(int attribute, String variableName)
	{ GL20.glBindAttribLocation(programID, attribute, variableName); }

	protected abstract void bindAttributes();

	public void cleanUp()
	{
		stop();
		GL20.glDetachShader(programID, vertexShaderID);
		GL20.glDetachShader(programID, fragmentShaderID);
		GL20.glDeleteShader(vertexShaderID);
		GL20.glDeleteShader(fragmentShaderID);
		GL20.glDeleteProgram(programID);
	}

	protected abstract void getAllUniformLocations();

	protected int getUniformLocation(String uniformName)
	{ return GL20.glGetUniformLocation(programID, uniformName); }

	protected void load2DVector(int location, Vector2f vector)
	{ GL20.glUniform2f(location, vector.x, vector.y); }

	protected void loadBoolean(int location, boolean value)
	{
		float toLoad = 0;
		if (value)
		{ toLoad = 1; }
		GL20.glUniform1f(location, toLoad);
	}

	protected void loadFloat(int location, float value)
	{ GL20.glUniform1f(location, value); }

	protected void loadInt(int location, int value)
	{ GL20.glUniform1i(location, value); }

	protected void loadMatrix(int location, Matrix4f matrix)
	{
		float[] fm = new float[16];
		matrix.get(fm);
		GL20.glUniformMatrix4fv(location, false, fm);
	}
	protected void loadVector(int location, Vector3f vector)
	{ GL20.glUniform3f(location, vector.x, vector.y, vector.z); }

	protected void loadVector(int location, Vector4f vector)
	{ GL20.glUniform4f(location, vector.x, vector.y, vector.z, vector.w); }

	public void start()
	{ GL20.glUseProgram(programID); }

	public void stop()
	{ GL20.glUseProgram(0); }
}
