package com.github.hydos.ginger.engine.common.tools;

import static org.lwjgl.BufferUtils.createByteBuffer;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;

import org.lwjgl.BufferUtils;

public class IOUtil
{
	public static ByteBuffer ioResourceToByteBuffer(String resource, int bufferSize) throws IOException
	{
		ByteBuffer buffer;
		try (
			InputStream source = IOUtil.class.getResourceAsStream(resource);
			ReadableByteChannel rbc = Channels.newChannel(source))
		{
			buffer = createByteBuffer(bufferSize);
			while (true)
			{
				int bytes = rbc.read(buffer);
				if (bytes == -1)
				{ break; }
				if (buffer.remaining() == 0)
				{ buffer = resizeBuffer(buffer, buffer.capacity() * 3 / 2); }
			}
		}
		buffer.flip();
		return buffer;
	}

	private static ByteBuffer resizeBuffer(ByteBuffer buffer, int newCapacity)
	{
		ByteBuffer newBuffer = BufferUtils.createByteBuffer(newCapacity);
		buffer.flip();
		newBuffer.put(buffer);
		return newBuffer;
	}

	private IOUtil()
	{}
}