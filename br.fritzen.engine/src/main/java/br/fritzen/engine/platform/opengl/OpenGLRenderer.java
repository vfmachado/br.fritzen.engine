package br.fritzen.engine.platform.opengl;

import org.lwjgl.opengl.GL11;

import br.fritzen.engine.renderer.Buffer.VertexArray;
import br.fritzen.engine.renderer.Renderer;
import br.fritzen.engine.renderer.shader.Shader;

public class OpenGLRenderer extends Renderer {
	
	
	@Override
	public void clear() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	@Override
	public void clearColor(float r, float g, float b, float a) {
		
		GL11.glClearColor(r, g, b, a);
		
	}

	@Override
	public void draw(VertexArray va, Shader shader) {
		
		shader.bind();
		va.bind();
				
		GL11.glDrawElements(GL11.GL_TRIANGLES, va.getIB().getCount(), GL11.GL_UNSIGNED_INT, va.getIB().getOffset());
		
		
	}

	
	
}