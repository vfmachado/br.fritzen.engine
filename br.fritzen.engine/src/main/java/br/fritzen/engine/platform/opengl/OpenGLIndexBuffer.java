package br.fritzen.engine.platform.opengl;

import java.nio.IntBuffer;

import org.lwjgl.opengl.GL15;

import br.fritzen.engine.core.EngineLog;
import br.fritzen.engine.renderer.Buffer;

public class OpenGLIndexBuffer implements Buffer.IndexBuffer {

	
	private int ibo;
	
	private int count;
	
	private int offset;
	
	
	public OpenGLIndexBuffer(IntBuffer indexBuffer, int count) {
		
		this.ibo = GL15.glGenBuffers();
		
		if (this.ibo == 0) {
			EngineLog.warning("Problem creating OpenGL IBO");
		}

		this.bind();
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL15.GL_STATIC_DRAW);
		this.count = count;
		
	}
	
	
	@Override
	public void bind() {
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.ibo);
	}
	
	
	@Override
	public void unbind() {
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	
	@Override
	public int getOffset() {
		return offset;
	}


	@Override
	public void setOffset(int offset) {
		this.offset = offset;
	}


	@Override
	public int getCount() {
		return this.count;
	}
		
	
}
