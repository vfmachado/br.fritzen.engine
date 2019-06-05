package br.fritzen.engine.renderer;

import br.fritzen.engine.platform.opengl.OpenGLRenderer;
import br.fritzen.engine.renderer.Buffer.VertexArray;
import br.fritzen.engine.renderer.shader.Shader;

public abstract class Renderer {

	public enum API {
		NONE, OPENGL, VULKAN;
	}
	
	//TODO - change in runtime
	public static API SELECTED_API = API.OPENGL; 

	
	private static Renderer instance;

		
	public static Renderer get() {
		
		if (instance == null) {
			instance = new OpenGLRenderer();
		}
		
		return instance; 
	}
	
	
	public abstract void clear();
	
	public abstract void clearColor(float r, float g, float b, float a);
	
	public abstract void draw(VertexArray va, Shader shader);
	
}
