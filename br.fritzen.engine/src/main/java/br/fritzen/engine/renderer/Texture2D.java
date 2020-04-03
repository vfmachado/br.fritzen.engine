package br.fritzen.engine.renderer;

import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

import br.fritzen.engine.core.EngineLog;
import br.fritzen.engine.platform.opengl.OpenGLTexture2D;

public abstract class Texture2D extends Texture {

	public static Texture2D create(String filename) {
		
		switch (RendererAPI.SELECTED_API) {
		
		case NONE:
			EngineLog.severe("None API selected");
			break;
		
		case OPENGL:
			return new OpenGLTexture2D(filename);
			
		case VULKAN:
			EngineLog.severe("Not supported yet");
			break;
		}
		
		EngineLog.severe("Invalid Renderer API.");
		System.exit(1);
		return null;
	}
	
	
	public static Texture2D create(int width, int height) {
		
		switch (RendererAPI.SELECTED_API) {
		
		case NONE:
			EngineLog.severe("None API selected");
			break;
		
		case OPENGL:
			return new OpenGLTexture2D(width, height);
			
		case VULKAN:
			EngineLog.severe("Not supported yet");
			break;
		}
		
		EngineLog.severe("Invalid Renderer API.");
		System.exit(1);
		return null;
		
	}
	
	
	public static Texture2D createBlank() {
		
		ByteBuffer textureData = BufferUtils.createByteBuffer(16);
		
		textureData.put((byte) 255);
		textureData.put((byte) 255);
		textureData.put((byte) 255);
		textureData.put((byte) 255);
		
		textureData.flip();
		
		Texture2D blank = create(1, 1);
		blank.setData(textureData, 1);
		return blank;
	}
	
	
	/**
	 * Creates a flat color texture
	 * @return
	 */
	public static Texture2D createFlat(int r, int g, int b) {
		
		ByteBuffer textureData = BufferUtils.createByteBuffer(16);
		
		textureData.put((byte) r);
		textureData.put((byte) g);
		textureData.put((byte) b);
		textureData.put((byte) 255);
		
		textureData.flip();
		
		Texture2D blank = create(1, 1);
		blank.setData(textureData, 1);
		return blank;
	}
	
}
