package br.fritzen.engine.renderer;

import br.fritzen.engine.renderer.Buffer.VertexArray;

public class RenderCommand {

	private static RendererAPI API; 
	
	
	
	public static void init() {
		
		API = RendererAPI.get();
		
	}
	
	
	public static void clear() {
	
		API.clear();
		
	}

	
	public static void clearColor(float r, float g, float b, float a) {
		
		API.clearColor(r, g, b, a);
		
	}
	
	
	public static void drawIndexed(VertexArray vertexArray) {
		
		API.drawIndexed(vertexArray);
		
	}
	
	
	public static void setViewPort(int width, int height) {
		
		API.setViewPort(width, height);
		
	}
	
}
