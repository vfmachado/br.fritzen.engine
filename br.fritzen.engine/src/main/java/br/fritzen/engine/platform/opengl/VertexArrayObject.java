package br.fritzen.engine.platform.opengl;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class VertexArrayObject {

	private int vao;
	
	private int drawMode;
	
	
	public VertexArrayObject() {
		this.vao = GL30.glGenVertexArrays();		
		this.drawMode = GL11.GL_TRIANGLES;
	}
	
	
	public void bind() {
		GL30.glBindVertexArray(this.vao);
	}
	
	
	public void unbind() {
		GL30.glBindVertexArray(0);
	}
	
	
	public void addInterleavedVBO(VertexBufferObject vbo, List<VertexBufferLayout> layouts) {
		
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
	
	
	public void addVBO(VertexBufferObject vbo, int attribArray, int size ) {
		
		this.bind();
		vbo.bind();
		GL20.glEnableVertexAttribArray(attribArray);
		GL20.glVertexAttribPointer(attribArray, size, GL11.GL_FLOAT, false, 0, 0);
		
	}
	
	
	public void addVBO(VertexBufferObject vbo, int attribArray, VertexBufferLayout layout ) {
		
		this.bind();
		vbo.bind();
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


	public int getDrawMode() {
		return drawMode;
	}


	public void setDrawMode(int drawMode) {
		this.drawMode = drawMode;
	}
	

}
