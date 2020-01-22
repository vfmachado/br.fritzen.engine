package br.fritzen.engine.platform.opengl;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL33;

import br.fritzen.engine.renderer.Buffer.VertexArray;
import br.fritzen.engine.renderer.RendererAPI;

public class OpenGLRendererAPI extends RendererAPI {

	@Override
	public void init() {

		GL11.glEnable(GL11.GL_STENCIL_TEST);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_DEPTH_TEST);

		GL11.glEnable(GL30.GL_MULTISAMPLE);
		
		// active debug with glDebugMessageCallback
	}


	@Override
	public void setViewPort(int width, int height) {

		GL11.glViewport(0, 0, width, height);

	}


	@Override
	public void clear() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}


	@Override
	public void clearColor(float r, float g, float b, float a) {

		GL11.glClearColor(r, g, b, a);

	}


	@Override
	public void drawIndexed(VertexArray vertexArray) {

		vertexArray.bind();
		GL11.glDrawElements(GL11.GL_TRIANGLES, vertexArray.getIB().getCount(), GL11.GL_UNSIGNED_INT,
				vertexArray.getIB().getOffset());

	}

	
	@Override
	public void drawInstanced(VertexArray vertexArray, int count) {
		vertexArray.bind();
		//GL31.glDrawArraysInstanced(GL11.GL_TRIANGLES, 0, vertexArray.getIB().getCount(), count);
		GL33.glDrawElementsInstanced(GL11.GL_TRIANGLES, vertexArray.getIB().getCount() * 4, GL11.GL_UNSIGNED_INT, 0, count);
	}
}
