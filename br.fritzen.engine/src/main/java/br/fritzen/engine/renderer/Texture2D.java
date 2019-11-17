package br.fritzen.engine.renderer;

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
	
}
