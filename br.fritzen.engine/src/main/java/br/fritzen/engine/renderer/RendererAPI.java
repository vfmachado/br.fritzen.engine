package br.fritzen.engine.renderer;

import br.fritzen.engine.core.EngineLog;
import br.fritzen.engine.platform.opengl.OpenGLRendererAPI;
import br.fritzen.engine.renderer.Buffer.VertexArray;

public abstract class RendererAPI {

	public enum API {
		NONE, OPENGL, VULKAN;
	}
	
	//TODO - change in runtime
	public static API SELECTED_API = API.OPENGL; 

	private static RendererAPI instance;
	
	public static RendererAPI get() {
		
		if (instance == null) {
			
			switch (SELECTED_API) {
			
			case OPENGL:
				instance = new OpenGLRendererAPI();;
				break;
			
			default:
				EngineLog.severe("API NOT SUPPORTED");
				
			}
			
		}
		
		return instance;
	}
	
	public abstract void init();
	
	public abstract void setViewPort(int width, int height);
	
	public abstract void clear();

	public abstract void clearColor(float r, float g, float b, float a);
	
	public abstract void drawIndexed(VertexArray vertexArray);

	public abstract void drawInstanced(VertexArray vertexArray, int count);
		
	
}
