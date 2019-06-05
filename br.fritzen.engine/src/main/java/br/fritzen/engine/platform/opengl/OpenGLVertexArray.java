package br.fritzen.engine.platform.opengl;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import br.fritzen.engine.renderer.Buffer;
import br.fritzen.engine.renderer.Buffer.IndexBuffer;
import br.fritzen.engine.renderer.Buffer.VertexBuffer;


public class OpenGLVertexArray implements Buffer.VertexArray {

	private int va;
		
	private IndexBuffer ib;
	
	public OpenGLVertexArray() {
		this.va = GL30.glGenVertexArrays();		
	}
	
	
	public void bind() {
		GL30.glBindVertexArray(this.va);
	}
	
	
	public void unbind() {
		GL30.glBindVertexArray(0);
	}
	
	
	public void addInterleavedVBO(OpenGLVertexBuffer vbo, List<VertexBufferLayout> layouts) {
		
		this.bind();
		vbo.bind();
		
		int stride = computeStride(layouts);
		
		int pointer = 0;
		for (int i = 0; i < layouts.size(); i++) {
			
			VertexBufferLayout currentLayout = layouts.get(i);
			GL20.glEnableVertexAttribArray(i);
			GL20.glVertexAttribPointer(i, currentLayout.getSize(), currentLayout.getType(), 
					currentLayout.isNormalized(), stride, pointer);
			
			pointer += (currentLayout.getSize() * getSizeOf(currentLayout.getType()));
			
		}
	}
	
	
	@Override
	public void addVB(VertexBuffer vb, int attribArray, int size ) {
		
		this.bind();
		vb.bind();
		GL20.glEnableVertexAttribArray(attribArray);
		GL20.glVertexAttribPointer(attribArray, size, GL11.GL_FLOAT, false, 0, 0);
		
	}
	

	@Override
	public void addVB(VertexBuffer vb, int attribArray, VertexBufferLayout layout ) {
		
		this.bind();
		vb.bind();
		GL20.glEnableVertexAttribArray(attribArray);
		GL20.glVertexAttribPointer(attribArray, layout.getSize(), layout.getType(), layout.isNormalized(), 0, 0);
		
	}
	
	
	private int computeStride(List<VertexBufferLayout> layouts) {
		
		int sum = 0;
		
		for (VertexBufferLayout layout : layouts) {
			sum += (layout.getSize() * getSizeOf(layout.getType()));
		}
		
		return sum;
	}
	
	
	private int getSizeOf(int type) {
		
		if (type == GL11.GL_FLOAT) {
			return 4;
		}
		
		if (type == GL11.GL_BYTE) {
			return 1;
		}
		
		return 4;
	}

	
	public void setIB(IndexBuffer ib) {
		
		this.ib = ib;
		
		this.bind();
		this.ib.bind();
		
	}
	
	
	public IndexBuffer getIB() {
		return this.ib;
	}
}
